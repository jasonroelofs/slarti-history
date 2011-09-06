package org.slartibartfast.behaviors;

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
   * Speed in units per second that this Actor should
   * move in the world.
   */
  private float speed;

  /**
   * Does this Actor move forward according to its rotation or
   * does it follow the world axes?
   */
  private boolean movesRelativeToRotation;

  public PhysicalBehavior() {
    location = Vector3f.ZERO.clone();
    moveDelta = Vector3f.ZERO.clone();

    rotation = Quaternion.IDENTITY.clone();

    movesRelativeToRotation = false;

    speed = 1.0f;
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
    location.addLocal(moveDelta.mult(delta));

    Node node = actor.getNode();
    node.move(location.add(node.getWorldTranslation().negate()));

    moveDelta = Vector3f.ZERO.clone();
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


}
