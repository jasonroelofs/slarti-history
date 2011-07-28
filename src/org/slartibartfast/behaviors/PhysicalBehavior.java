package org.slartibartfast.behaviors;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.slartibartfast.Actor;
import org.slartibartfast.Behavior;

/**
 * This Behavior keeps track of the actual location, orientation, and
 * scale of the Actor it's added to.
 *
 * @author roelofs
 */
public class PhysicalBehavior extends Behavior {

  private Vector3f location;

  public PhysicalBehavior() {
    initialized = true;
  }

  public void setLocation(Vector3f location) {
    this.location = location;
  }

  public Vector3f getLocation() {
    return this.location;
  }

  @Override
  public void perform(Actor actor, float delta) {
    Node node = actor.get(Node.class, "node");
    node.move(this.location.add(node.getWorldTranslation().negate()));
  }

}
