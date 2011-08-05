package org.slartibartfast;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;
import org.slartibartfast.behaviors.PhysicalBehavior;

/**
 * A SceneGraph handles a localized set of Actors in a given
 * area for display or other uses. Takes a node which will be
 * the root node of this scene graph.
 *
 * @author roelofs
 */
public class SceneGraph {

  private Node rootNode;
  private AssetManager assetManager;

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
    a.set("node", actorNode);

    actors.add(a);

    a.useBehavior(new PhysicalBehavior());
    return a;
  }

  /**
   * Build an actor and give it a starting location
   */
  public Actor createActor(Vector3f startingLocation) {
    Actor a = createActor();
    PhysicalBehavior b = a.getBehavior(PhysicalBehavior.class);
    b.setLocation(startingLocation);

    return a;
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

  public void setAssetManager(AssetManager assetManager) {
    this.assetManager = assetManager;
  }

  public void setBehaviorController(BehaviorController behaviorController) {
    this.behaviorController = behaviorController;
  }
}
