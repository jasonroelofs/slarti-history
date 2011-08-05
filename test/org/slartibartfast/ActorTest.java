package org.slartibartfast;

import org.mockito.Mock;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slartibartfast.behaviors.PhysicalBehavior;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author roelofs
 */
public class ActorTest {

  class TestBehavior extends Behavior {
    @Override
    public void perform(float delta) {

    }
  }

  BehaviorController behaviorController;

  private Actor actor;

  @Before
  public void setUp() {
    behaviorController = mock(BehaviorController.class);
    actor = new Actor(behaviorController);
  }

  @After
  public void tearDown() {
  }

  @Test
  public void canBeGivenANewBehavior() {
    TestBehavior b = new TestBehavior();
    actor.useBehavior(b);

    assertTrue(actor.hasBehavior(TestBehavior.class));

    assertEquals(actor, b.getActor());
  }

  @Test
  public void addingNewBehaviorInformsController() {
    TestBehavior b = new TestBehavior();
    actor.useBehavior(b);

    verify(behaviorController).registerBehavior(b);
  }

  @Test
  public void canBeToldToDropBehavior() {
    TestBehavior b = new TestBehavior();
    actor.useBehavior(b);

    actor.removeBehavior(TestBehavior.class);

    assertFalse(actor.hasBehavior(TestBehavior.class));
  }

  @Test
  public void removingBehaviorUnregistersWithController() {
    TestBehavior b = new TestBehavior();
    actor.useBehavior(b);

    actor.removeBehavior(TestBehavior.class);

    verify(behaviorController).unregisterBehavior(b);
  }

  @Test
  public void canBeAskedForAnExistingBehavior() {
    TestBehavior b = new TestBehavior();
    actor.useBehavior(b);

    assertEquals(b, actor.getBehavior(TestBehavior.class));
  }

  @Test
  public void askingForUnknownBehaviorReturnsNull() {
    assertNull(actor.getBehavior(TestBehavior.class));
  }

  @Test
  public void canGetAllBehaviors() {
    actor.useBehavior(new PhysicalBehavior());
    actor.useBehavior(new TestBehavior());

    ArrayList<Behavior> list = actor.getBehaviors();

    assertEquals(2, list.size());
  }

  @Test
  public void canGetAndSetDataSavedOnActor() {
    assertNull(actor.get(String.class, "someStringValue"));

    actor.set("someStringValue", "This is a string thing");

    assertEquals("This is a string thing", actor.get(String.class, "someStringValue"));
  }

  @Test
  public void handlesMultipleDifferentTypesInBlob() {
    actor.set("integer", new Integer(14));
    actor.set("string", "Some string here");

    assertEquals(14, actor.get(Integer.class, "integer").intValue());
    assertEquals("Some string here", actor.get(String.class, "string"));
  }
}
