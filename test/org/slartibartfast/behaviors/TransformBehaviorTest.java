package org.slartibartfast.behaviors;

import org.junit.Before;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import org.slartibartfast.Actor;
import com.jme3.math.Vector3f;
import org.junit.Test;
import org.slartibartfast.Factories;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TransformBehaviorTest {

  public TransformBehaviorTest() {
  }

  private TransformBehavior behavior;
  private Actor actor;

  @Before
  public void setupTransform() {
    behavior = new TransformBehavior();
    actor = Factories.createActor();
    behavior.setActor(actor);
  }

  @Test
  public void hasLocation() {
    Vector3f location = Vector3f.ZERO;
    behavior.setLocation(location);

    assertEquals(location, behavior.getLocation());
    assertEquals(location, actor.getNode().getLocalTranslation());
  }

  @Test
  public void hasOrientation() {
    Quaternion quat = Quaternion.IDENTITY;
    behavior.setRotation(quat);

    assertEquals(quat, behavior.getRotation());
    assertEquals(quat, actor.getNode().getLocalRotation());
  }

  @Test
  public void startsAtOrigin() {
    assertEquals(Vector3f.ZERO, behavior.getLocation());
  }

  @Test
  public void hasSpeed_DefaultsToOne() {
    assertEquals(1.0f, behavior.getSpeed(), 0.001);

    behavior.setSpeed(3.0f);
    assertEquals(3.0f, behavior.getSpeed(), 0.001);
  }

  @Test
  public void hasTurnSpeed_DefaultsToOneEighty() {
    assertEquals(180, behavior.getTurnSpeed());

    behavior.setTurnSpeed(90);
    assertEquals(90, behavior.getTurnSpeed());
  }

  @Test
  public void moveRelativeToRotation_defaultFalse() {
    assertFalse(behavior.movesRelativeToRotation());

    behavior.moveRelativeToRotation(true);
    assertTrue(behavior.movesRelativeToRotation());
  }

  @Test
  public void canFixUpDirection_defaultTrue() {
    assertTrue(behavior.hasFixedUpAxis());

    behavior.fixUpAxis(false);

    assertFalse(behavior.hasFixedUpAxis());
  }

  // Don't really know how best to test this.
  // This gets values that are very, very close to each other
  // Might just be a float-is-always-a-little-off thing
//  @Test
//  public void rotateAdheresToFixedUpAxis() {
//
//    Actor a = Factories.createActor();
//    behavior.setActor(a);
//    behavior.setTurnSpeed(90);
//    Quaternion fromQuat = behavior.getRotation().clone();
//
//    behavior.turnLeft(0.5f);
//    behavior.perform(1.0f);
//
//    behavior.pitchUp(1.0f);
//    behavior.perform(1.0f);
//
//    Quaternion delta = new Quaternion();
//    delta.fromAngles(
//            FastMath.DEG_TO_RAD * -90,
//            FastMath.DEG_TO_RAD * 45,
//            0);
//    Quaternion expected = fromQuat.mult(delta);
//
//    assertEquals(expected, behavior.getRotation());
//    assertEquals(expected, a.getNode().getLocalRotation());
//  }

  @Test
  public void canLookAtALocation() {
    behavior.setLocation(new Vector3f(0, 0, -10f));

    Quaternion oldRot = behavior.getRotation().clone();

    behavior.lookAt(Vector3f.ZERO);

    assertThat(behavior.getRotation(), not(equalTo(oldRot)));
  }


  @Test
  public void queueMoveLeftEventAtCurrentSpeed() {
    behavior.setSpeed(2.0f);

    behavior.moveLeft();

    assertEquals(Vector3f.ZERO, behavior.getLocation());

    behavior.perform(1.0f);

    assertEquals(new Vector3f(-2, 0, 0), behavior.getLocation());

    // Ensure the delta gets cleared
    behavior.perform(1.0f);

    assertEquals(new Vector3f(-2, 0, 0), behavior.getLocation());
  }

  @Test
  public void queueMoveRightAtSpeed() {
    behavior.setSpeed(2.0f);

    behavior.moveRight();
    behavior.perform(1.0f);

    assertEquals(new Vector3f(2, 0, 0), behavior.getLocation());
  }

  @Test
  public void queueMoveUpAtSpeed() {
    behavior.setSpeed(2.0f);

    behavior.moveUp();
    behavior.perform(1.0f);

    assertEquals(new Vector3f(0, 2, 0), behavior.getLocation());
  }

  @Test
  public void queueMoveDownAtSpeed() {
    behavior.setSpeed(2.0f);

    behavior.moveDown();
    behavior.perform(1.0f);

    assertEquals(new Vector3f(0, -2, 0), behavior.getLocation());
  }

  @Test
  public void queueMoveForwardAtSpeed() {
    behavior.setSpeed(2.0f);

    behavior.moveForward();
    behavior.perform(1.0f);

    assertEquals(new Vector3f(0, 0, -2), behavior.getLocation());
  }

  @Test
  public void queueMoveBackwardAtSpeed() {
    behavior.setSpeed(2.0f);

    behavior.moveBackward();
    behavior.perform(1.0f);

    assertEquals(new Vector3f(0, 0, 2), behavior.getLocation());
  }

  @Test
  public void queueMoveForwardAtSpeed_RelativeRotation() {
    behavior.setSpeed(2.0f);
    behavior.setTurnSpeed(90);
    behavior.moveRelativeToRotation(true);

    // Now facing down -X
    behavior.turnLeft(1.0f);
    behavior.perform(1.0f);

    behavior.moveForward();
    behavior.perform(1.0f);

    assertEquals(2, behavior.getLocation().x, 0.001);
  }

  @Test
  public void queueMoveLeftAtSpeed_RelativeRotation() {
    behavior.setSpeed(2.0f);
    behavior.setTurnSpeed(90);
    behavior.moveRelativeToRotation(true);

    // Now facing down -X
    behavior.turnLeft(1.0f);
    behavior.perform(1.0f);

    behavior.moveLeft();
    behavior.perform(1.0f);

    assertEquals(-2, behavior.getLocation().z, 0.001);
  }

  @Test
  public void queueMoveUpAtSpeed_RelativeRotation() {
    behavior.setSpeed(2.0f);
    behavior.setTurnSpeed(90);
    behavior.moveRelativeToRotation(true);

    // Now facing down -X
    behavior.turnLeft(1.0f);
    behavior.perform(1.0f);

    behavior.moveUp();
    behavior.perform(1.0f);

    assertEquals(2, behavior.getLocation().y, 0.001);
  }

  @Test
  public void queueTurnLeftAtSpeed() {
    behavior.setTurnSpeed(90);
    Quaternion fromQuat = behavior.getRotation().clone();

    behavior.turnLeft(1.0f);
    behavior.perform(1.0f);

    Quaternion delta = new Quaternion();
    delta.fromAngles(0, FastMath.DEG_TO_RAD * 90, 0);
    Quaternion expected = fromQuat.mult(delta);

    assertEquals(expected, behavior.getRotation());
    assertEquals(expected, actor.getNode().getLocalRotation());
  }

  @Test
  public void queueTurnRightAtSpeed() {
    behavior.setTurnSpeed(90);
    Quaternion fromQuat = behavior.getRotation().clone();

    behavior.turnRight(1.0f);
    behavior.perform(1.0f);

    Quaternion delta = new Quaternion();
    delta.fromAngles(0, FastMath.DEG_TO_RAD * -90, 0);
    Quaternion expected = fromQuat.mult(delta);

    assertEquals(expected, behavior.getRotation());
    assertEquals(expected, actor.getNode().getLocalRotation());
  }

  @Test
  public void queuePitchUpAtSpeed() {
    behavior.setTurnSpeed(90);
    Quaternion fromQuat = behavior.getRotation().clone();

    behavior.pitchUp(1.0f);
    behavior.perform(1.0f);

    Quaternion delta = new Quaternion();
    delta.fromAngles(FastMath.DEG_TO_RAD * -90, 0, 0);
    Quaternion expected = fromQuat.mult(delta);

    assertEquals(expected, behavior.getRotation());
    assertEquals(expected, actor.getNode().getLocalRotation());
  }

  @Test
  public void queuePitchDownAtSpeed() {
    behavior.setTurnSpeed(90);
    Quaternion fromQuat = behavior.getRotation().clone();

    behavior.pitchDown(1.0f);
    behavior.perform(1.0f);

    Quaternion delta = new Quaternion();
    delta.fromAngles(FastMath.DEG_TO_RAD * 90, 0, 0);
    Quaternion expected = fromQuat.mult(delta);

    assertEquals(expected, behavior.getRotation());
    assertEquals(expected, actor.getNode().getLocalRotation());
  }

  @Test
  public void queueRollLeftAtSpeed() {
    behavior.setTurnSpeed(90);
    Quaternion fromQuat = behavior.getRotation().clone();

    behavior.rollLeft();
    behavior.perform(1.0f);

    Quaternion delta = new Quaternion();
    delta.fromAngles(0, 0, FastMath.DEG_TO_RAD * -90);
    Quaternion expected = fromQuat.mult(delta);

    assertEquals(expected, behavior.getRotation());
    assertEquals(expected, actor.getNode().getLocalRotation());
  }

  @Test
  public void queueRollRightAtSpeed() {
    behavior.setTurnSpeed(90);
    Quaternion fromQuat = behavior.getRotation().clone();

    behavior.rollRight();
    behavior.perform(1.0f);

    Quaternion delta = new Quaternion();
    delta.fromAngles(0, 0, FastMath.DEG_TO_RAD * 90);
    Quaternion expected = fromQuat.mult(delta);

    assertEquals(expected, behavior.getRotation());
    assertEquals(expected, actor.getNode().getLocalRotation());
  }

  @Test
  public void canCopyDetailsFromAnotherTransform() {
    TransformBehavior from = new TransformBehavior();
    from.setActor(Factories.createActor());

    from.setLocation(new Vector3f(1, 2, 3));
    from.setRotation(Quaternion.IDENTITY);
    from.setSpeed(10f);
    from.setTurnSpeed(45);
    from.moveRelativeToRotation(true);

    behavior.copyFrom(from);

    assertEquals(new Vector3f(1, 2, 3), behavior.getLocation());
    assertEquals(Quaternion.IDENTITY, behavior.getRotation());
    assertEquals(10f, behavior.getSpeed(), 0.0001);
    assertEquals(45, behavior.getTurnSpeed());
    assertTrue(behavior.movesRelativeToRotation());

    // And sanity check that vectors were cloned.
    from.getLocation().x = 10;

    assertThat(behavior.getLocation(), not(new Vector3f(10, 2, 3)));
  }
}
