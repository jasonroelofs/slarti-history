package org.slartibartfast;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.slartibartfast.behaviors.PhysicalBehavior;

/**
 * A SceneGraph handles a localized set of Actors in a given
 * area for display or other uses. Takes a node which will be
 * the root node of this scene graph.
 *
 * @author roelofs
 */
public class SceneGraph implements InputReceiver {

  private Node rootNode;

  /**
   * Construct a new SceneGraph rooted on the passed in node.
   */
  public SceneGraph(Node node) {
    rootNode = node;
  }

  /**
   * Build and return a new Actor, ready for use.
   *
   * @return Actor
   */
  public Actor createActor() {
    Actor a = new Actor();
    PhysicalBehavior b = new PhysicalBehavior();
    Node actorNode = new Node();
    rootNode.attachChild(actorNode);

    b.setNode(actorNode);
    a.useBehavior(b);
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
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Node getRootNode() {
    return rootNode;
  }

  public void setRootNode(Node node) {
    rootNode = node;
  }
}
