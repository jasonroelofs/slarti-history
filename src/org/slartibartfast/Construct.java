package org.slartibartfast;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Construct is the base name for anything built in the galaxy by the player.
 * A construct (will) know where it is in the galaxy, and knows what it looks
 * like.
 *
 * Constructs are to be built through ConstructFactory where the definition
 * will be read out of the database as needed.
 *
 * This class itself is used by the ConstructBehavior to turn an Actor
 * into a Construct.
 *
 * A note about Construct's visual details:
 *
 *  All constructs are built on a grid, centered on the Construct's local origin.
 *  This grid is sized to be 1/4 a unit cubed, so a 1x1x1 JME unit cube construct
 *  will actually be 4x4x4 in grid size.
 */
public class Construct {

  private String name;

  // Root node of the geometry graph that defines this construct
  private Node root;

  public Construct(String name, Node root) {
    this.name = name;
    this.root = root;
  }

  /**
   * Tell this construct to attach it's internal node graph to the
   * passed in Node.
   *
   * @see ConstructBehavior.initialize
   *
   * @param node The parent node for this Construct
   */
  public void attachTo(Node node) {
    node.attachChild(root);
  }


  public static Vector3f gridToLocal(Vector3f startPoint) {
    return startPoint.mult(0.25f);
  }

}
