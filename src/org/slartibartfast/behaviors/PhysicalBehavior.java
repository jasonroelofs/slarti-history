package org.slartibartfast.behaviors;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.slartibartfast.Behavior;

/**
 * This Behavior keeps track of the actual location, rotation, and
 * scale of the Actor it's added to.
 *
 * @author roelofs
 */
public class PhysicalBehavior extends Behavior {

  /**
   * World location of the current Actor
   */
  private Vector3f location;

  /**
   * Orientation of the current Actor
   */
  private Quaternion rotation;

  /**
   * This keeps a running tally of all move requests
   * made to this Actor for the given frame
   */
  private Vector3f moveDelta;

  /**
   * Like moveDelta, keep track of the rotation requests
   * received so far. Kept in a vector to make it much
   * easier to use than a Quaternion:
   *
   *  x = yaw
   *  y = roll
   *  z = pitch
   *
   * While technically correct, I have to say that in general use
   * this is horribly wrong. X is pitch, Y is yaw, and Z is roll.
   * This is what the system will use, and will convert as needed
   * for JME to understand.
   */
  private Vector3f rotateDelta;

  /**
   * Speed in units per second that this Actor should
   * move in the world.
   */
  private float speed;

  /**
   * Rotational speed in degrees per second
   */
  private int turnSpeed;

  /**
   * Does this Actor move forward according to its rotation or
   * does it follow the world axes?
   */
  private boolean movesRelativeToRotation;

  /**
   * Does this Actor rotate in regards to a fixed Up axis
   * (right now always +Y) or can it rotate freely in any direction?
   */
  private boolean fixedUpAxis;

  private boolean forceRotationUpdate;
  private boolean forceLocationUpdate;


  public PhysicalBehavior() {
    location = Vector3f.ZERO.clone();
    moveDelta = Vector3f.ZERO.clone();

    rotation = Quaternion.IDENTITY.clone();
    rotateDelta = Vector3f.ZERO.clone();

    movesRelativeToRotation = false;
    fixedUpAxis = true;

    speed = 1.0f;
    turnSpeed = 180;
  }

  public void setLocation(Vector3f location) {
    this.location = location;
    this.forceLocationUpdate = true;
  }

  public Vector3f getLocation() {
    return location;
  }

  public Quaternion getRotation() {
    return rotation;
  }

  public void setRotation(Quaternion orientation) {
    this.rotation = orientation;
    this.forceRotationUpdate = true;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public float getSpeed() {
    return speed;
  }

  public void setTurnSpeed(int speed) {
    turnSpeed = speed;
  }

  public int getTurnSpeed() {
    return turnSpeed;
  }

  public void fixUpAxis(boolean fix) {
    fixedUpAxis = fix;
  }

  public boolean hasFixedUpAxis() {
    return fixedUpAxis;
  }

  /**
   * Change how this behavior reacts to move commands
   * @param flag
   */
  public void moveRelativeToRotation(boolean flag) {
    movesRelativeToRotation = flag;
  }

  /**
   * How does this behavior react to move commands?
   * @return
   */
  public boolean movesRelativeToRotation() {
    return movesRelativeToRotation;
  }

  /**
   * @see Behavior.perform
   * @param delta
   */
  @Override
  public void perform(float delta) {
    if((rotateDelta.equals(Vector3f.ZERO) && !forceRotationUpdate) &&
            (moveDelta.equals(Vector3f.ZERO) && !forceLocationUpdate)) {
      return;
    }

    //
    // Update Rotation
    //
    rotateDelta.multLocal(delta);

    Quaternion pitch, yaw, roll;
    Vector3f up, left, dir;

    if(fixedUpAxis) {
      up = Vector3f.UNIT_Y.clone();
    } else {
      up = rotation.mult(Vector3f.UNIT_Y);
    }

    left = rotation.mult(Vector3f.UNIT_X);
    dir = rotation.mult(Vector3f.UNIT_Z);

    pitch = new Quaternion();
    pitch.fromAngleAxis(rotateDelta.x, left);

    yaw = new Quaternion();
    yaw.fromAngleAxis(rotateDelta.y, up);

    roll = new Quaternion();
    roll.fromAngleAxis(rotateDelta.z, dir);

    // Order of operations matter here. The current rotation MUST be last
    rotation = yaw.mult(pitch).mult(roll).mult(rotation);

    //
    // Update location
    //

    if(movesRelativeToRotation) {
      // See Camera.getLeft and Camera.getDirection for the following
      //
      // TODO Me thinks there's an easier way to do this, possibly
      // a single Matrix mult instead of this long-form.
      // But! for now this works, and I'm happy
      left.multLocal(-moveDelta.x * delta);
      dir.multLocal(-moveDelta.z * delta);
      up.multLocal(moveDelta.y * delta);

      location.addLocal(left);
      location.addLocal(dir);
      location.addLocal(up);
    } else {
      location.addLocal(moveDelta.mult(delta));
    }

    //
    // Sync node's spatial information to JME
    //
    Node node = actor.getNode();
    node.move(location.add(node.getWorldTranslation().negate()));
    node.setLocalRotation(rotation);

    //
    // Reset deltas for next frame
    //
    moveDelta = Vector3f.ZERO.clone();
    rotateDelta = Vector3f.ZERO.clone();
  }

  /**
   * Movement requests.
   * The following requests queue up a move to be applied
   * at the next update
   */

  public void moveLeft() {
    moveDelta.x -= speed;
  }

  public void moveRight() {
    moveDelta.x += speed;
  }

  public void moveUp() {
    moveDelta.y += speed;
  }

  public void moveDown() {
    moveDelta.y -= speed;
  }

  public void moveForward() {
    moveDelta.z -= speed;
  }

  public void moveBackward() {
    moveDelta.z += speed;
  }

  /**
   * Turn left at turnRatio percentage of the turn speed.
   * @param turnRatio How much of the turn speed to turn. Must be[0,1]
   */
  public void turnLeft(float turnRatio) {
    rotateDelta.y += FastMath.DEG_TO_RAD * turnSpeed * turnRatio;
  }

  /**
   * Turn left at turnRatio percentage of the turn speed.
   * @param turnRatio How much of the turn speed to turn. Must be[0,1]
   */
  public void turnRight(float turnRatio) {
    rotateDelta.y -= FastMath.DEG_TO_RAD * turnSpeed * turnRatio;
  }

  /**
   * Rotate up at turnRatio percentage of the turn speed.
   * @param turnRatio How much of the turn speed to turn. Must be[0,1]
   */
  public void pitchUp(float turnRatio) {
    rotateDelta.x -= FastMath.DEG_TO_RAD * turnSpeed * turnRatio;
  }

  /**
   * Rotate down at turnRatio percentage of the turn speed.
   * @param turnRatio How much of the turn speed to turn. Must be[0,1]
   */
  public void pitchDown(float turnRatio) {
    rotateDelta.x += FastMath.DEG_TO_RAD * turnSpeed * turnRatio;
  }

  public void rollLeft() {
    rotateDelta.z -= FastMath.DEG_TO_RAD * turnSpeed;
  }

  public void rollRight() {
    rotateDelta.z += FastMath.DEG_TO_RAD * turnSpeed;
  }

}
