package org.slartibartfast.events;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import org.junit.Before;
import org.junit.Test;
import org.slartibartfast.Actor;
import org.slartibartfast.Factories;
import org.slartibartfast.behaviors.TransformBehavior;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class EventsTest {

  private Actor actor;
  private InputEvent event;

  public EventsTest() {
  }

  @Before
  public void setupActorAndEvent() {
    actor = Factories.createActor();
    event = new InputEvent(null, true);
  }

  @Test
  public void canReverseLookupAnEvent() throws UnknownEventError {
    assertEquals(Events.MoveUp, Events.get("MoveUp"));
  }

  @Test
  public void unknownEventThrowsAppropriateError() {
    try {
      assertEquals(Events.MoveUp, Events.get("BadError_Test_Something"));
      fail("Expected processEvent to throw error, did not");
    } catch(UnknownEventError e) {
      // Good!
      assertEquals("Unknown event with name of BadError_Test_Something",
              e.getMessage());
    }
  }

  @Test
  public void moveLeftEvent() throws UnknownEventError {
    event.event = "MoveLeft";
    TransformBehavior b = actor.getBehavior(TransformBehavior.class);
    Vector3f oldLoc = b.getLocation().clone();

    Events.processEvent(actor, event);
    b.perform(1.0f);

    assertTrue(b.getLocation().x < oldLoc.x);
  }

  @Test
  public void moveRightEvent() throws UnknownEventError {
    event.event = "MoveRight";
    TransformBehavior b = actor.getBehavior(TransformBehavior.class);
    Vector3f oldLoc = b.getLocation().clone();

    Events.processEvent(actor, event);
    b.perform(1.0f);

    assertTrue(b.getLocation().x > oldLoc.x);
  }

  @Test
  public void moveUpEvent() throws UnknownEventError {
    event.event = "MoveUp";
    TransformBehavior b = actor.getBehavior(TransformBehavior.class);
    Vector3f oldLoc = b.getLocation().clone();

    Events.processEvent(actor, event);
    b.perform(1.0f);

    assertTrue(b.getLocation().y > oldLoc.y);
  }

  @Test
  public void moveDownEvent() throws UnknownEventError {
    event.event = "MoveDown";
    TransformBehavior b = actor.getBehavior(TransformBehavior.class);
    Vector3f oldLoc = b.getLocation().clone();

    Events.processEvent(actor, event);
    b.perform(1.0f);

    assertTrue(b.getLocation().y < oldLoc.y);
  }

  @Test
  public void moveForwardEvent() throws UnknownEventError {
    event.event = "MoveForward";
    TransformBehavior b = actor.getBehavior(TransformBehavior.class);
    Vector3f oldLoc = b.getLocation().clone();

    Events.processEvent(actor, event);
    b.perform(1.0f);

    assertTrue(b.getLocation().z < oldLoc.z);
  }

  @Test
  public void moveBackwardEvent() throws UnknownEventError {
    event.event = "MoveBackward";
    TransformBehavior b = actor.getBehavior(TransformBehavior.class);
    Vector3f oldLoc = b.getLocation().clone();

    Events.processEvent(actor, event);
    b.perform(1.0f);

    assertTrue(b.getLocation().z > oldLoc.z);
  }

  @Test
  public void turnLeftEvent() throws UnknownEventError {
    event.event = "TurnLeft";
    event.value = 1.0f;
    TransformBehavior b = actor.getBehavior(TransformBehavior.class);
    Quaternion oldQuat = b.getRotation().clone();

    Events.processEvent(actor, event);
    b.perform(1.0f);

    assertThat(oldQuat, not(equalTo(b.getRotation())));
  }

  @Test
  public void turnRightEvent() throws UnknownEventError {
    event.event = "TurnRight";
    event.value = 1.0f;
    TransformBehavior b = actor.getBehavior(TransformBehavior.class);
    Quaternion oldQuat = b.getRotation().clone();

    Events.processEvent(actor, event);
    b.perform(1.0f);

    assertThat(oldQuat, not(equalTo(b.getRotation())));
  }

  @Test
  public void pitchUpEvent() throws UnknownEventError {
    event.event = "PitchUp";
    event.value = 1.0f;
    TransformBehavior b = actor.getBehavior(TransformBehavior.class);
    Quaternion oldQuat = b.getRotation().clone();

    Events.processEvent(actor, event);
    b.perform(1.0f);

    assertThat(oldQuat, not(equalTo(b.getRotation())));
  }

  @Test
  public void pitchDownEvent() throws UnknownEventError {
    event.event = "PitchDown";
    event.value = 1.0f;
    TransformBehavior b = actor.getBehavior(TransformBehavior.class);
    Quaternion oldQuat = b.getRotation().clone();

    Events.processEvent(actor, event);
    b.perform(1.0f);

    assertThat(oldQuat, not(equalTo(b.getRotation())));
  }

  @Test
  public void rollLeftEvent() throws UnknownEventError {
    event.event = "RollLeft";
    event.value = 1.0f;
    TransformBehavior b = actor.getBehavior(TransformBehavior.class);
    Quaternion oldQuat = b.getRotation().clone();

    Events.processEvent(actor, event);
    b.perform(1.0f);

    assertThat(oldQuat, not(equalTo(b.getRotation())));
  }

  @Test
  public void rollRightEvent() throws UnknownEventError {
    event.event = "RollRight";
    event.value = 1.0f;
    TransformBehavior b = actor.getBehavior(TransformBehavior.class);
    Quaternion oldQuat = b.getRotation().clone();

    Events.processEvent(actor, event);
    b.perform(1.0f);

    assertThat(oldQuat, not(equalTo(b.getRotation())));
  }

}
