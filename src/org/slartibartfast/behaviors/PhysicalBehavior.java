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


  public PhysicalBehavior() {
    location = Vector3f.ZERO.clone();
    moveDelta = Vector3f.ZERO.clone();

    rotation = Quaternion.IDENTITY.clone();
    rotateDelta = Vector3f.ZERO.clone();

    movesRelativeToRotation = false;

    speed = 1.0f;
    turnSpeed = 180;
  }

  public void setLocation(Vector3f location) {
    this.location = location;
  }

  public Vector3f getLocation() {
    return location;
  }

  public Quaternion getRotation() {
    return rotation;
  }

  public void setRotation(Quaternion orientation) {
    this.rotation = orientation;
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
    //
    // Set our local knowledge
    //
    location.addLocal(moveDelta.mult(delta));

    rotateDelta.multLocal(delta);
    Quaternion rotQuat = new Quaternion();
    rotQuat.fromAngles(rotateDelta.x, rotateDelta.y, rotateDelta.z);
    rotation.multLocal(rotQuat);

    //
    // Sync node's spatial information to JME
    //
    Node node = actor.getNode();
    node.move(location.add(node.getWorldTranslation().negate()));
    node.rotate(rotateDelta.x, rotateDelta.y, rotateDelta.z);

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

  public void turnLeft() {
    rotateDelta.y += FastMath.DEG_TO_RAD * turnSpeed;
  }

  public void turnRight() {
    rotateDelta.y -= FastMath.DEG_TO_RAD * turnSpeed;
  }

}
