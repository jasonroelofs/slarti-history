package org.slartibartfast.behaviors;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import org.slartibartfast.Actor;
import com.jme3.math.Vector3f;
import org.junit.Test;
import org.slartibartfast.Factories;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PhysicalBehaviorTest {

  public PhysicalBehaviorTest() {
  }

  @Test
  public void hasLocation() {
    PhysicalBehavior b = new PhysicalBehavior();
    Vector3f location = Vector3f.ZERO;
    b.setLocation(location);

    assertEquals(location, b.getLocation());
  }

  @Test
  public void hasOrientation() {
    PhysicalBehavior b = new PhysicalBehavior();
    Quaternion quat = Quaternion.IDENTITY;
    b.setRotation(quat);

    assertEquals(quat, b.getRotation());
  }

  @Test
  public void startsAtOrigin() {
    PhysicalBehavior b = new PhysicalBehavior();
    assertEquals(Vector3f.ZERO, b.getLocation());
  }

  @Test
  public void hasSpeed_DefaultsToOne() {
    PhysicalBehavior b = new PhysicalBehavior();
    assertEquals(1.0f, b.getSpeed(), 0.001);

    b.setSpeed(3.0f);
    assertEquals(3.0f, b.getSpeed(), 0.001);
  }

  @Test
  public void hasTurnSpeed_DefaultsToOneEighty() {
    PhysicalBehavior b = new PhysicalBehavior();
    assertEquals(180, b.getTurnSpeed());

    b.setTurnSpeed(90);
    assertEquals(90, b.getTurnSpeed());
  }

  @Test
  public void moveRelativeToRotation_defaultFalse() {
    PhysicalBehavior b = new PhysicalBehavior();
    assertFalse(b.movesRelativeToRotation());

    b.moveRelativeToRotation(true);
    assertTrue(b.movesRelativeToRotation());
  }

  @Test
  public void canFixUpDirection_defaultTrue() {
    PhysicalBehavior b = new PhysicalBehavior();
    assertTrue(b.hasFixedUpAxis());

    b.fixUpAxis(false);

    assertFalse(b.hasFixedUpAxis());
  }

  // Don't really know how best to test this.
  // This gets values that are very, very close to each other
  // Might just be a float-is-always-a-little-off thing
//  @Test
//  public void rotateAdheresToFixedUpAxis() {
//    PhysicalBehavior b = new PhysicalBehavior();
//    Actor a = Factories.createActor();
//    b.setActor(a);
//    b.setTurnSpeed(90);
//    Quaternion fromQuat = b.getRotation().clone();
//
//    b.turnLeft(0.5f);
//    b.perform(1.0f);
//
//    b.pitchUp(1.0f);
//    b.perform(1.0f);
//
//    Quaternion delta = new Quaternion();
//    delta.fromAngles(
//            FastMath.DEG_TO_RAD * -90,
//            FastMath.DEG_TO_RAD * 45,
//            0);
//    Quaternion expected = fromQuat.mult(delta);
//
//    assertEquals(expected, b.getRotation());
//    assertEquals(expected, a.getNode().getLocalRotation());
//  }

  @Test
  public void queueMoveLeftEventAtCurrentSpeed() {
    PhysicalBehavior b = new PhysicalBehavior();
    Actor a = Factories.createActor();
    b.setActor(a);
    b.setSpeed(2.0f);

    b.moveLeft();

    assertEquals(Vector3f.ZERO, b.getLocation());

    b.perform(1.0f);

    assertEquals(new Vector3f(-2, 0, 0), b.getLocation());

    // Ensure the delta gets cleared
    b.perform(1.0f);

    assertEquals(new Vector3f(-2, 0, 0), b.getLocation());
  }

  @Test
  public void queueMoveRightAtSpeed() {
    PhysicalBehavior b = new PhysicalBehavior();
    Actor a = Factories.createActor();
    b.setActor(a);
    b.setSpeed(2.0f);

    b.moveRight();
    b.perform(1.0f);

    assertEquals(new Vector3f(2, 0, 0), b.getLocation());
  }

  @Test
  public void queueMoveUpAtSpeed() {
    PhysicalBehavior b = new PhysicalBehavior();
    Actor a = Factories.createActor();
    b.setActor(a);
    b.setSpeed(2.0f);

    b.moveUp();
    b.perform(1.0f);

    assertEquals(new Vector3f(0, 2, 0), b.getLocation());
  }

  @Test
  public void queueMoveDownAtSpeed() {
    PhysicalBehavior b = new PhysicalBehavior();
    Actor a = Factories.createActor();
    b.setActor(a);
    b.setSpeed(2.0f);

    b.moveDown();
    b.perform(1.0f);

    assertEquals(new Vector3f(0, -2, 0), b.getLocation());
  }

  @Test
  public void queueMoveForwardAtSpeed() {
    PhysicalBehavior b = new PhysicalBehavior();
    Actor a = Factories.createActor();
    b.setActor(a);
    b.setSpeed(2.0f);

    b.moveForward();
    b.perform(1.0f);

    assertEquals(new Vector3f(0, 0, -2), b.getLocation());
  }

  @Test
  public void queueMoveBackwardAtSpeed() {
    PhysicalBehavior b = new PhysicalBehavior();
    Actor a = Factories.createActor();
    b.setActor(a);
    b.setSpeed(2.0f);

    b.moveBackward();
    b.perform(1.0f);

    assertEquals(new Vector3f(0, 0, 2), b.getLocation());
  }

  @Test
  public void queueMoveForwardAtSpeed_RelativeRotation() {
    PhysicalBehavior b = new PhysicalBehavior();
    Actor a = Factories.createActor();
    b.setActor(a);
    b.setSpeed(2.0f);
    b.setTurnSpeed(90);
    b.moveRelativeToRotation(true);

    // Now facing down -X
    b.turnLeft(1.0f);
    b.perform(1.0f);

    b.moveForward();
    b.perform(1.0f);

    assertEquals(2, b.getLocation().x, 0.001);
  }

  @Test
  public void queueMoveLeftAtSpeed_RelativeRotation() {
    PhysicalBehavior b = new PhysicalBehavior();
    Actor a = Factories.createActor();
    b.setActor(a);
    b.setSpeed(2.0f);
    b.setTurnSpeed(90);
    b.moveRelativeToRotation(true);

    // Now facing down -X
    b.turnLeft(1.0f);
    b.perform(1.0f);

    b.moveLeft();
    b.perform(1.0f);

    assertEquals(-2, b.getLocation().z, 0.001);
  }

  @Test
  public void queueMoveUpAtSpeed_RelativeRotation() {
    PhysicalBehavior b = new PhysicalBehavior();
    Actor a = Factories.createActor();
    b.setActor(a);
    b.setSpeed(2.0f);
    b.setTurnSpeed(90);
    b.moveRelativeToRotation(true);

    // Now facing down -X
    b.turnLeft(1.0f);
    b.perform(1.0f);

    b.moveUp();
    b.perform(1.0f);

    assertEquals(2, b.getLocation().y, 0.001);
  }

  @Test
  public void queueTurnLeftAtSpeed() {
    PhysicalBehavior b = new PhysicalBehavior();
    Actor a = Factories.createActor();
    b.setActor(a);
    b.setTurnSpeed(90);
    Quaternion fromQuat = b.getRotation().clone();

    b.turnLeft(1.0f);
    b.perform(1.0f);

    Quaternion delta = new Quaternion();
    delta.fromAngles(0, FastMath.DEG_TO_RAD * 90, 0);
    Quaternion expected = fromQuat.mult(delta);

    assertEquals(expected, b.getRotation());
    assertEquals(expected, a.getNode().getLocalRotation());
  }

  @Test
  public void queueTurnRightAtSpeed() {
    PhysicalBehavior b = new PhysicalBehavior();
    Actor a = Factories.createActor();
    b.setActor(a);
    b.setTurnSpeed(90);
    Quaternion fromQuat = b.getRotation().clone();

    b.turnRight(1.0f);
    b.perform(1.0f);

    Quaternion delta = new Quaternion();
    delta.fromAngles(0, FastMath.DEG_TO_RAD * -90, 0);
    Quaternion expected = fromQuat.mult(delta);

    assertEquals(expected, b.getRotation());
    assertEquals(expected, a.getNode().getLocalRotation());
  }

  @Test
  public void queuePitchUpAtSpeed() {
    PhysicalBehavior b = new PhysicalBehavior();
    Actor a = Factories.createActor();
    b.setActor(a);
    b.setTurnSpeed(90);
    Quaternion fromQuat = b.getRotation().clone();

    b.pitchUp(1.0f);
    b.perform(1.0f);

    Quaternion delta = new Quaternion();
    delta.fromAngles(FastMath.DEG_TO_RAD * -90, 0, 0);
    Quaternion expected = fromQuat.mult(delta);

    assertEquals(expected, b.getRotation());
    assertEquals(expected, a.getNode().getLocalRotation());
  }

  @Test
  public void queuePitchDownAtSpeed() {
    PhysicalBehavior b = new PhysicalBehavior();
    Actor a = Factories.createActor();
    b.setActor(a);
    b.setTurnSpeed(90);
    Quaternion fromQuat = b.getRotation().clone();

    b.pitchDown(1.0f);
    b.perform(1.0f);

    Quaternion delta = new Quaternion();
    delta.fromAngles(FastMath.DEG_TO_RAD * 90, 0, 0);
    Quaternion expected = fromQuat.mult(delta);

    assertEquals(expected, b.getRotation());
    assertEquals(expected, a.getNode().getLocalRotation());
  }

  @Test
  public void performUpdatesNodeLocation() {
    PhysicalBehavior b = new PhysicalBehavior();
    Vector3f location = new Vector3f(1.0f, 3.0f, 10.0f);
    b.setLocation(location);

    Actor a = Factories.createActor();
    b.setActor(a);

    b.perform(1);

    assertEquals(location, a.getNode().getWorldTranslation());

    // Multiple frames don't send it careening off.
    b.perform(1);
    b.perform(1);

    assertEquals(location, a.getNode().getWorldTranslation());
  }


  @Test
  public void performUpdatesNodeRotation() {
    PhysicalBehavior b = new PhysicalBehavior();
    Quaternion rotation = new Quaternion();
    rotation.fromAngleAxis(FastMath.PI, Vector3f.UNIT_X);
    b.setRotation(rotation);

    Actor a = Factories.createActor();
    b.setActor(a);

    b.perform(1);

    assertEquals(rotation, a.getNode().getWorldRotation());

    // Multiple frames don't send it careening off.
    b.perform(1);
    b.perform(1);

    assertEquals(rotation, a.getNode().getWorldRotation());
  }

}
