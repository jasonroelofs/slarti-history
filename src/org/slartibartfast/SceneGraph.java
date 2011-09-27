package org.slartibartfast;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;
import org.slartibartfast.behaviors.CameraBehavior;
import org.slartibartfast.behaviors.TransformBehavior;

/**
 * A SceneGraph handles a localized set of Actors in a given
 * area for display or other uses. Takes a node which will be
 * the root node of this scene graph.
 */
public class SceneGraph {

  private Node rootNode;

  private long nextActorId = 0;

  private List<Actor> actors;

  private BehaviorController behaviorController;

  /**
   * Construct a new SceneGraph rooted on the passed in node.
   */
  public SceneGraph(Node node) {
    rootNode = node;

    actors = new ArrayList<Actor>();
  }

  /**
   * Build and return a new Actor, ready for use.
   *
   * @return Actor
   */
  public Actor createActor() {
    Actor a = new Actor(behaviorController);
    a.setId(getNextId());

    Node actorNode = new Node();
    rootNode.attachChild(actorNode);
    a.setNode(actorNode);

    actors.add(a);

    a.useBehavior(new TransformBehavior());
    return a;
  }

  /**
   * Build an actor and give it a starting location
   */
  public Actor createActor(Vector3f startingLocation) {
    Actor a = createActor();
    TransformBehavior b = a.getBehavior(TransformBehavior.class);
    b.setLocation(startingLocation);

    return a;
  }

  /**
   * Run a collision test against this graph and return
   * the first node found.
   *
   * Code found on:
   *  http://jmonkeyengine.org/wiki/doku.php/jme3:advanced:mouse_picking
   *
   * TODO: Figure out the test for this. Not sure how Mockito
   * handles out-parameters
   *
   * @param camera The camera the user is currently using
   * @param mousePos The current mouse position from input
   * @return First node found, or null if nothing found
   */
  public Geometry getClosestNode(Actor camera, Vector2f mousePos) {
    CollisionResults results = new CollisionResults();
    Camera cam = camera.getBehavior(CameraBehavior.class).getCamera();

    Vector3f click3d = cam.getWorldCoordinates(mousePos, 0f).clone();
    Vector3f dir = cam.getWorldCoordinates(mousePos, 1f).
            subtractLocal(click3d);
    // Aim the ray from the clicked spot forwards.
    Ray ray = new Ray(click3d, dir);

    int count = rootNode.collideWith(ray, results);
    if(count == 0) {
      return null;
    } else {
      return results.getClosestCollision().getGeometry();
    }
  }

  public Node getRootNode() {
    return rootNode;
  }

  public void setRootNode(Node node) {
    rootNode = node;
  }

  /**
   * Per-frame update
   *
   * @param delta Time since last frame
   */
  public void update(float delta) {
    behaviorController.update(delta);
  }

  private long getNextId() {
    return nextActorId++;
  }

  public void setBehaviorController(BehaviorController behaviorController) {
    this.behaviorController = behaviorController;
  }
}
