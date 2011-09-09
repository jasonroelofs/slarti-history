package org.slartibartfast.behaviors;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import org.slartibartfast.Behavior;

/**
 * This behavior registers the Actor as a collidable entity
 * in the physics engine.
 */
public class PhysicsBehavior extends Behavior {

  /**
   * Mass of this Actor in kilograms
   */
  private float mass;

  /**
   * The control this Actor is using to hook into
   * the physics system
   */
  private RigidBodyControl rigidControl;

  public PhysicsBehavior(float mass) {
    this.mass = mass;
  }

  public float getMass() {
    return mass;
  }

  @Override
  public void perform(float delta) {

  }

  public void initialize(PhysicsSpace physicsSpace) {
    rigidControl = new RigidBodyControl(mass);
    actor.getNode().addControl(rigidControl);
    physicsSpace.add(rigidControl);
    
    initialize();
  }
}
