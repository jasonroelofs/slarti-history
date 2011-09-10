package org.slartibartfast;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slartibartfast.behaviors.TransformBehavior;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
    actor.useBehavior(new TransformBehavior());
    actor.useBehavior(new TestBehavior());

    ArrayList<Behavior> list = actor.getBehaviors();

    assertEquals(2, list.size());
  }

  class SubBehavior extends TransformBehavior {

  }

  @Test
  public void canReplaceABehaviorWithSubclass() {
    SubBehavior newB = new SubBehavior();
    actor.replaceBehavior(TransformBehavior.class, newB);

    assertEquals(newB, actor.getBehavior(TransformBehavior.class));
    assertEquals(newB, actor.getBehavior(SubBehavior.class));
  }
}
