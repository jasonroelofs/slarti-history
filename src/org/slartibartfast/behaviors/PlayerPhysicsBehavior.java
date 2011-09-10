package org.slartibartfast.behaviors;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 * This behavior replaces this Actor's TransformBehavior with this class
 * which works directly with the Physics sub-system. With this behavior
 * actors will be affected by gravity and will collide with things.
 *
 * As it's a replacement for TransformBehavior, you can get access to
 * this behavior two ways:
 *
 *    getBehavior(TransformBehavior.class)
 *    getBehavior(PlayerPhysicsBehavior.class)
 *
 * Obviously the first will get you only access to methods on Transform.
 *
 * Not the happiest with this name, may rename if I come up with something
 * better.
 */
public class PlayerPhysicsBehavior extends TransformBehavior {

  private static float PHYSICS_STEP = 1f / 60f;

  private CharacterControl playerControl;

  /**
   * Initial vertical velocity in units / s
   */
  private float jumpSpeed;

  /**
   * Terminal fall velocity in units / s
   */
  private float fallSpeed;

  /**
   * Local affecting gravity in units / s^2
   */
  private float gravity;

  public PlayerPhysicsBehavior() {
  }

  public float getJumpSpeed() {
    return jumpSpeed;
  }

  public void setJumpSpeed(float jumpSpeed) {
    this.jumpSpeed = jumpSpeed;
  }

  public float getFallSpeed() {
    return fallSpeed;
  }

  public void setFallSpeed(float fallSpeed) {
    this.fallSpeed = fallSpeed;
  }

  public float getGravity() {
    return gravity;
  }

  public void setGravity(float gravity) {
    this.gravity = gravity;
  }

  @Override
  public void perform(float delta) {
    // Update any settings from the previous frame
    playerControl.setJumpSpeed(jumpSpeed);
    playerControl.setFallSpeed(fallSpeed);
    playerControl.setGravity(gravity);

    // Rotations are untouched by physics here, just do
    // things normally
    performRotationUpdate(delta);

    Vector3f newWalkDir = Vector3f.ZERO.clone();

    // Cap our updates for 60fps here, but still
    // allow higher values to be sent in
    if(delta < PHYSICS_STEP) {
      delta = PHYSICS_STEP;
    }

    if(movesRelativeToRotation()) {
      // TODO
      // Copied from TransformBehavior
      // Find a way to clean up this duplication

      Vector3f up, left, dir;
      Quaternion currentRotation = getRotation();

      up = currentRotation.mult(Vector3f.UNIT_Y);
      left = currentRotation.mult(Vector3f.UNIT_X);
      dir = currentRotation.mult(Vector3f.UNIT_Z);

      left.multLocal(-moveDelta.x * delta);
      dir.multLocal(-moveDelta.z * delta);
      up.multLocal(moveDelta.y * delta);

      newWalkDir.addLocal(left);
      newWalkDir.addLocal(dir);
      newWalkDir.addLocal(up);
    } else {
      newWalkDir = moveDelta.mult(delta);
    }

    playerControl.setWalkDirection(newWalkDir);

    // Reset deltas
    moveDelta = Vector3f.ZERO.clone();
  }

  /**
   * Initialization of this behavior requires a few things:
   *
   *  - Setting up the CharacterControl with a collision shape
   *  - Setting defaults to CharacterControl
   *  - Adding control to node
   *  - Adding control to physics space
   *  - Replacing TransformBehavior on the actor
   *
   * @param space
   */
  public void initialize(PhysicsSpace space) {
    CapsuleCollisionShape capsule = new CapsuleCollisionShape(0.5f, 2f, 1);
    playerControl = new CharacterControl(capsule, 0.5f);
    playerControl.setJumpSpeed(3);
    playerControl.setFallSpeed(5);

    // Handle rotations ourselves
    playerControl.setUseViewDirection(false);

    TransformBehavior transB =
            getActor().getBehavior(TransformBehavior.class);
    setSpeed(transB.getSpeed());
    setTurnSpeed(transB.getTurnSpeed());

    getActor().getNode().addControl(playerControl);
    space.add(playerControl);

    getActor().replaceBehavior(TransformBehavior.class, this);

    initialize();
  }

}
