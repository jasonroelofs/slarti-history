package org.slartibartfast;

import com.jme3.math.Vector3f;
import org.junit.Before;
import org.junit.Test;
import org.slartibartfast.behaviors.PhysicalBehavior;
import static org.junit.Assert.*;

public class EventsTest {

  private Actor actor;
  private InputEvent event;

  public EventsTest() {
  }

  @Before
  public void setupActorAndEvent() {
    actor = Factories.createActor();
    event = new InputEvent(actor, null, true);
  }

  @Test
  public void canReverseLookupAnEvent() {
    assertEquals(Events.MoveUp, Events.get("MoveUp"));
  }

  @Test
  public void moveLeftEvent() {
    event.event = "MoveLeft";
    PhysicalBehavior b = event.actor.getBehavior(PhysicalBehavior.class);
    Vector3f oldLoc = b.getLocation().clone();

    Events.processEvent(event, 1.0f);
    b.perform(1.0f);

    assertTrue(b.getLocation().x < oldLoc.x);
  }

  @Test
  public void moveRightEvent() {
    event.event = "MoveRight";
    PhysicalBehavior b = event.actor.getBehavior(PhysicalBehavior.class);
    Vector3f oldLoc = b.getLocation().clone();

    Events.processEvent(event, 1.0f);
    b.perform(1.0f);

    assertTrue(b.getLocation().x > oldLoc.x);
  }

  @Test
  public void moveUpEvent() {
    event.event = "MoveUp";
    PhysicalBehavior b = event.actor.getBehavior(PhysicalBehavior.class);
    Vector3f oldLoc = b.getLocation().clone();

    Events.processEvent(event, 1.0f);
    b.perform(1.0f);

    assertTrue(b.getLocation().y > oldLoc.y);
  }

  @Test
  public void moveDownEvent() {
    event.event = "MoveDown";
    PhysicalBehavior b = event.actor.getBehavior(PhysicalBehavior.class);
    Vector3f oldLoc = b.getLocation().clone();

    Events.processEvent(event, 1.0f);
    b.perform(1.0f);

    assertTrue(b.getLocation().y < oldLoc.y);
  }
}
