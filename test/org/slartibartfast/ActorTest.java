package org.slartibartfast;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author roelofs
 */
public class ActorTest {

  class TestBehavior implements Behavior {
    @Override
    public void perform(float delta) {

    }
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void canBeGivenANewBehavior() {
    Actor a = new Actor();
    TestBehavior b = new TestBehavior();
    a.useBehavior(b);

    assertTrue(a.hasBehavior(TestBehavior.class));
  }

  @Test
  public void canBeToldToDropBehavior() {
    Actor a = new Actor();
    TestBehavior b = new TestBehavior();
    a.useBehavior(b);

    a.removeBehavior(TestBehavior.class);

    assertFalse(a.hasBehavior(TestBehavior.class));
  }

  @Test
  public void canBeAskedForAnExistingBehavior() {
    Actor a = new Actor();
    TestBehavior b = new TestBehavior();
    a.useBehavior(b);

    assertEquals(b, a.getBehavior(TestBehavior.class));
  }

  @Test
  public void askingForUnknownBehaviorReturnsNull() {
    Actor a = new Actor();

    assertNull(a.getBehavior(TestBehavior.class));
  }
}