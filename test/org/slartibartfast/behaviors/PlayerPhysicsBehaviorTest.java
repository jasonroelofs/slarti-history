package org.slartibartfast.behaviors;

import com.jme3.math.Vector3f;
import org.mockito.Matchers;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.math.Quaternion;
import org.slartibartfast.Actor;
import org.junit.Before;
import org.junit.Test;
import org.slartibartfast.Factories;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

public class PlayerPhysicsBehaviorTest {

  public PlayerPhysicsBehaviorTest() {
  }

  private PlayerPhysicsBehavior behavior;
  private Actor actor;
  private PhysicsSpace space;

  @Before
  public void setup() {
    behavior = new PlayerPhysicsBehavior();
    actor = Factories.createActor();

    space = mock(PhysicsSpace.class);

    behavior.setActor(actor);
  }

  @Test
  public void canBeCreated() {
    assertNotNull(behavior);
  }

  @Test
  public void canSetFallSpeed() {
    behavior.setFallSpeed(3.0f);
    assertEquals(3.0f, behavior.getFallSpeed(), 0.001);
  }

  @Test
  public void canSetJumpSpeed() {
    behavior.setJumpSpeed(3.0f);
    assertEquals(3.0f, behavior.getJumpSpeed(), 0.001);

  }

  @Test
  public void canSetGravity() {
    behavior.setGravity(3.0f);
    assertEquals(3.0f, behavior.getGravity(), 0.001);
  }

  @Test
  public void initializeSetsUpCharacterControl() {
    behavior.initialize(space);

    verify(space).add(Matchers.any(CharacterControl.class));
    CharacterControl control =
            actor.getNode().getControl(CharacterControl.class);

    assertNotNull(control);
    assertTrue(control.getCollisionShape() instanceof CapsuleCollisionShape);
    assertEquals(3, control.getJumpSpeed(), 0.001);
    assertEquals(5, control.getFallSpeed(), 0.001);
    assertFalse(control.isUseViewDirection());

    assertTrue(behavior.isInitialized());
  }

  @Test
  public void initializeCopiesAndReplacesTransformBehavior() {
    TransformBehavior transB = actor.getBehavior(TransformBehavior.class);
    transB.setSpeed(12f);
    transB.setTurnSpeed(45);

    behavior.initialize(space);

    assertEquals(12f, behavior.getSpeed(), 0.001);
    assertEquals(45, behavior.getTurnSpeed());

    assertEquals(behavior, actor.getBehavior(TransformBehavior.class));
  }

  @Test
  public void performAppliesAnyJumpSpeedChanges() {
    behavior.initialize(space);
    behavior.setJumpSpeed(123.0f);
    behavior.perform(1.0f);

    CharacterControl control =
            actor.getNode().getControl(CharacterControl.class);

    assertEquals(123.0f, control.getJumpSpeed(), 0.001);
  }

  @Test
  public void performAppliesAnyFallSpeedChanges() {
    behavior.initialize(space);
    behavior.setFallSpeed(123.0f);
    behavior.perform(1.0f);

    CharacterControl control =
            actor.getNode().getControl(CharacterControl.class);

    assertEquals(123.0f, control.getFallSpeed(), 0.001);
  }

  @Test
  public void performAppliesAnyGravityChanges() {
    behavior.initialize(space);
    behavior.setGravity(123.0f);
    behavior.perform(1.0f);

    CharacterControl control =
            actor.getNode().getControl(CharacterControl.class);

    assertEquals(123.0f, control.getGravity(), 0.001);
  }

  @Test
  public void performAppliesRotationalUpdates() {
    behavior.initialize(space);

    behavior.turnLeft(1.0f);
    Quaternion oldQuat = behavior.getRotation().clone();

    behavior.perform(1.0f);

    assertThat(behavior.getRotation(), not(equalTo(oldQuat)));
  }

  @Test
  public void performAppliesLocationUpdates() {
    behavior.initialize(space);

    behavior.moveLeft();
    behavior.perform(1.0f);

    CharacterControl control =
            actor.getNode().getControl(CharacterControl.class);

    assertEquals(new Vector3f(-1, 0, 0), control.getWalkDirection());

    // If no changes made, resets the walk direction

    behavior.perform(1.0f);
    assertEquals(new Vector3f(0, 0, 0), control.getWalkDirection());
  }

  @Test
  public void performLocationUpdateAdheresToRelativeToRotation() {
    behavior.initialize(space);

    behavior.setTurnSpeed(90);
    behavior.moveRelativeToRotation(true);

    // Now facing down -X
    behavior.turnLeft(1.0f);
    behavior.perform(1.0f);

    behavior.moveLeft();
    behavior.perform(1.0f);

    CharacterControl control =
        actor.getNode().getControl(CharacterControl.class);

    assertEquals(-1, control.getWalkDirection().z, 0.001);
  }
}
