package org.slartibartfast.behaviors;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.slartibartfast.Behavior;

/**
 * This Behavior keeps track of the actual location, rotation, and
 * scale of the Actor it's added to.
 */
public class TransformBehavior extends Behavior {

  /**
   * This keeps a running tally of all move requests
   * made to this Actor for the given frame
   */
  protected Vector3f moveDelta;

  /**
   * Like moveDelta, keep track of the rotation requests
   * received so far. Kept in a vector to make it much
   * easier to use than a Quaternion.
   */
  private Vector3f rotateDelta;

  /**
   * Speed in units per second that this Actor should
   * move in the world.
   */
  private float speed;

  /**
   * Rotational speed in radians per second
   */
  private float turnSpeed;

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

  public TransformBehavior() {
    moveDelta = Vector3f.ZERO.clone();

    rotateDelta = Vector3f.ZERO.clone();

    movesRelativeToRotation = false;
    fixedUpAxis = true;

    speed = 1.0f;
    setTurnSpeed(180);
  }

  public void setLocation(Vector3f location) {
    getActor().getNode().setLocalTranslation(location);
  }

  public Vector3f getLocation() {
    return getActor().getNode().getLocalTranslation();
  }

  public Quaternion getRotation() {
    return getActor().getNode().getLocalRotation();
  }

  public void setRotation(Quaternion orientation) {
    getActor().getNode().setLocalRotation(orientation);
  }

  /**
   * Set speed in units per second this Actor moves.
   * @param speed Movement speed in units per second
   */
  public final void setSpeed(float speed) {
    this.speed = speed;
  }

  public final float getSpeed() {
    return speed;
  }

  /**
   * Set speed in degrees per second this Actor can rotate
   * @param speed Rotational speed in Degrees per second
   */
  public final void setTurnSpeed(int speed) {
    turnSpeed = speed * FastMath.DEG_TO_RAD;
  }

  public final int getTurnSpeed() {
    return Math.round(turnSpeed * FastMath.RAD_TO_DEG);
  }

  /**
   * Set this Actor to yaw around a fixed Up axis (+Y)
   * @param fix
   */
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
   * Update the current location and rotation of this Actor's Node.
   * @see Behavior.perform
   * @param delta Seconds since last frame
   */
  @Override
  public void perform(float delta) {
    if((rotateDelta.equals(Vector3f.ZERO)) &&
            (moveDelta.equals(Vector3f.ZERO))) {
      return;
    }

    performRotationUpdate(delta);
    performLocationUpdate(delta);
  }

  protected void performRotationUpdate(float delta) {
    rotateDelta.multLocal(delta);

    Quaternion pitch, yaw, roll;
    Quaternion currentRotation = getRotation();
    Vector3f up, left, dir;

    if(fixedUpAxis) {
      up = Vector3f.UNIT_Y.clone();
    } else {
      up = currentRotation.mult(Vector3f.UNIT_Y);
    }

    left = currentRotation.mult(Vector3f.UNIT_X);
    dir = currentRotation.mult(Vector3f.UNIT_Z);

    pitch = new Quaternion();
    pitch.fromAngleAxis(rotateDelta.x, left);

    yaw = new Quaternion();
    yaw.fromAngleAxis(rotateDelta.y, up);

    roll = new Quaternion();
    roll.fromAngleAxis(rotateDelta.z, dir);

    // Order of operations matter here. The current rotation MUST be last
    currentRotation = yaw.mult(pitch).mult(roll).mult(currentRotation);

    Node node = actor.getNode();
    node.setLocalRotation(currentRotation);

    rotateDelta = Vector3f.ZERO.clone();
  }

  protected void performLocationUpdate(float delta) {

    Vector3f location = getLocation();

    if(movesRelativeToRotation) {
      // See Camera.getLeft and Camera.getDirection for the following
      //
      // TODO Me thinks there's an easier way to do this, possibly
      // a single Matrix mult instead of this long-form.
      // But! for now this works, and I'm happy
      Vector3f up, left, dir;
      Quaternion currentRotation = getRotation();

      up = currentRotation.mult(Vector3f.UNIT_Y);
      left = currentRotation.mult(Vector3f.UNIT_X);
      dir = currentRotation.mult(Vector3f.UNIT_Z);

      left.multLocal(-moveDelta.x * delta);
      dir.multLocal(-moveDelta.z * delta);
      up.multLocal(moveDelta.y * delta);

      location.addLocal(left);
      location.addLocal(dir);
      location.addLocal(up);
    } else {
      location.addLocal(moveDelta.mult(delta));
    }

    Node node = actor.getNode();
    node.setLocalTranslation(location);

    moveDelta = Vector3f.ZERO.clone();
  }

  /**
   * Look at a certain point in space
   */
  public void lookAt(Vector3f point) {
    Quaternion rot = getRotation();
    rot.lookAt(point, Vector3f.UNIT_Y);
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
    rotateDelta.y += turnSpeed * turnRatio;
  }

  /**
   * Turn left at turnRatio percentage of the turn speed.
   * @param turnRatio How much of the turn speed to turn. Must be[0,1]
   */
  public void turnRight(float turnRatio) {
    rotateDelta.y -= turnSpeed * turnRatio;
  }

  /**
   * Rotate up at turnRatio percentage of the turn speed.
   * @param turnRatio How much of the turn speed to turn. Must be[0,1]
   */
  public void pitchUp(float turnRatio) {
    rotateDelta.x -= turnSpeed * turnRatio;
  }

  /**
   * Rotate down at turnRatio percentage of the turn speed.
   * @param turnRatio How much of the turn speed to turn. Must be[0,1]
   */
  public void pitchDown(float turnRatio) {
    rotateDelta.x += turnSpeed * turnRatio;
  }

  public void rollLeft() {
    rotateDelta.z -= turnSpeed;
  }

  public void rollRight() {
    rotateDelta.z += turnSpeed;
  }

}
