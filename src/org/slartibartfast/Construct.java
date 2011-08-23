package org.slartibartfast;

import com.jme3.scene.Node;

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
}
