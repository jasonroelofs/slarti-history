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
public class PhysicalBehavior extends Behavior {

  /**
   * World location of the current Actor
   */
  private Vector3f location;

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

  public PhysicalBehavior() {
    location = Vector3f.ZERO.clone();
    moveDelta = Vector3f.ZERO.clone();

    speed = 1.0f;
  }

  public void setLocation(Vector3f location) {
    this.location = location;
  }

  public Vector3f getLocation() {
    return location;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public float getSpeed() {
    return speed;
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

}
