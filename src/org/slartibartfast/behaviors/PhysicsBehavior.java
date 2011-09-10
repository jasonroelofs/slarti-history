package org.slartibartfast.behaviors;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Geometry;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import org.slartibartfast.Behavior;

/**
 * This behavior registers the Actor as a collidable entity
 * in the physics engine.
 *
 * NOTE: Once this behavior is put onto an Actor, any behavior
 * that sets location or rotation on the actor WILL NOT WORK.
 * Physics takes COMPLETE control over the location and rotation
 * of the actor. To manually transform the actor you'll need to disable
 * physics for this Actor, move the Actor, then re-enable physics.
 */
public class PhysicsBehavior extends Behavior {

  /**
   * Mass of this Actor in kilograms
   */
  private float mass;

  public PhysicsBehavior(float mass) {
    this.mass = mass;
  }

  public float getMass() {
    return mass;
  }

  class PhysicsApplier implements SceneGraphVisitor {
    private PhysicsSpace physicsSpace;

    public PhysicsApplier(PhysicsSpace space) {
      super();
      physicsSpace = space;
    }

    @Override
    public void visit(Spatial spatial) {
      if(spatial instanceof Geometry) {
        addRigidControlTo(spatial);
      }
    }

    private void addRigidControlTo(Spatial node) {
      RigidBodyControl rigidControl;

      rigidControl = new RigidBodyControl(mass);

      node.addControl(rigidControl);
      physicsSpace.add(rigidControl);
    }
  }

  public void initialize(PhysicsSpace physicsSpace) {
    // Ensure starting location and rotation get set
    // properly before physics takes over
    actor.getBehavior(TransformBehavior.class).perform(1.0f);

    // We want to build a collision hull for all Geometry nodes, as
    // Bullet recommends more smaller hulls over a few large hulls
    actor.getNode().breadthFirstTraversal(new PhysicsApplier(physicsSpace));

    initialize();
  }

}
