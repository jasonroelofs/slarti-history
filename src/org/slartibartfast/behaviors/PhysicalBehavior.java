package org.slartibartfast.behaviors;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.slartibartfast.Behavior;

/**
 * This Behavior keeps track of the actual location, orientation, and
 * scale of the Actor it's added to.
 *
 * @author roelofs
 */
public class PhysicalBehavior implements Behavior {

  private Vector3f location;
  private Node node;

  public void setLocation(Vector3f location) {
    this.location = location;
  }

  public Vector3f getLocation() {
    return this.location;
  }

  public void setNode(Node node) {
    this.node = node;
  }

  public Node getNode() {
    return node;
  }

  @Override
  public void perform(float delta) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
