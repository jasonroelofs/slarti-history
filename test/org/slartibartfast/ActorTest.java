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
  public void testCanBeGivenANewBehavior() {
    Actor a = new Actor();
    TestBehavior b = new TestBehavior();
    a.useBehavior(b);

    assertTrue(a.hasBehavior(TestBehavior.class));
  }

  @Test
  public void testCanBeToldToDropBehavior() {
    Actor a = new Actor();
    TestBehavior b = new TestBehavior();
    a.useBehavior(b);

    a.removeBehavior(TestBehavior.class);

    assertFalse(a.hasBehavior(TestBehavior.class));
  }
}
