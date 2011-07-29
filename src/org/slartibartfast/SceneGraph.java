package org.slartibartfast;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;
import org.slartibartfast.behaviors.DirectionalLightBehavior;
import org.slartibartfast.behaviors.PhysicalBehavior;
import org.slartibartfast.behaviors.VisualBehavior;

/**
 * A SceneGraph handles a localized set of Actors in a given
 * area for display or other uses. Takes a node which will be
 * the root node of this scene graph.
 *
 * @author roelofs
 */
public class SceneGraph implements InputReceiver {

  private Node rootNode;
  private AssetManager assetManager;

  private long nextActorId = 0;

  private List<Actor> actors;

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
    Actor a = new Actor();
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

  @Override
  public void receiveInput(InputEvent[] events) {
    //throw new UnsupportedOperationException("Not supported yet.");
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
   * TODO: Refactoring this logic out to separate behavior management
   * steps.
   * The scene graph shouldn't care about what type of behavior, nor
   * behavior initialization.
   *
   * @param delta
   */
  public void update(float delta) {
    for(Actor a : actors) {
      for(Behavior b: a.getBehaviors()) {
        if(!b.isInitialized()) {
          if(b instanceof VisualBehavior) {
            b.initialize(a, assetManager);
          } else if (b instanceof DirectionalLightBehavior) {
            b.initialize(a);
          }
        }


        b.perform(a, delta);
      }
    }

    // Current thinking:
    // - Pull all behaviors into lists of like behavior types
    // - Send each list to a handler which knows how to update that
    //   type of behavior
    // - Do these lists get build every frame or do we cache them?
    //   ( build every frame and change if we need better performance )
  }

  private long getNextId() {
    return nextActorId++;
  }

  public void setAssetManager(AssetManager assetManager) {
    this.assetManager = assetManager;
  }
}
