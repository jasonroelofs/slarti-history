package org.slartibartfast;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.slartibartfast.behaviors.PhysicalBehavior;

/**
 * Factory that produces Actors.
 *
 * @author roelofs
 */
public class ActorManager implements InputReceiver {

  private Node rootNode;

  /**
   * Build and return a new Actor, ready for use.
   *
   * @return Actor
   */
  public Actor create() {
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
  public Actor create(Vector3f startingLocation) {
    Actor a = create();
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
