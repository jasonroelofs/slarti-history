package org.slartibartfast.behaviors;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
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

  public PhysicsBehavior(float mass) {
    this.mass = mass;
  }

  public float getMass() {
    return mass;
  }

  @Override
  public void perform(float delta) {
    // Probably need to update TransformBehavior with new
    // location / rotation from Physics. Will deal with this when
    // I need that information
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

    actor.getNode().breadthFirstTraversal(new PhysicsApplier(physicsSpace));

    initialize();
  }

}
