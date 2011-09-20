package org.slartibartfast;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;

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

  /**
   * List of all parts of which this construct is built
   */
  private List<Part> parts;

  public Construct(String name) {
    this(name, null);
  }

  public Construct(String name, Node root) {
    this.name = name;
    this.root = root;

    parts = new ArrayList<Part>();
  }

  public String getName() {
    return name;
  }

  /**
   * Get the list of parts for this construct
   * @return
   */
  public final List<Part> getParts() {
    return parts;
  }

  /**
   * Add a new part to this Construct
   * @param part
   */
  public void addPart(Part part) {
    parts.add(part);
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
