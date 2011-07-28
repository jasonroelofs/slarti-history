package org.slartibartfast;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slartibartfast.behaviors.PhysicalBehavior;
import static org.junit.Assert.*;

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

  @Test
  public void canGetAllBehaviors() {
    Actor a = new Actor();
    a.useBehavior(new PhysicalBehavior());
    a.useBehavior(new TestBehavior());

    ArrayList<Behavior> list = a.getBehaviors();

    assertEquals(2, list.size());
  }

  @Test
  public void canGetAndSetDataSavedOnActor() {
    Actor a = new Actor();
    assertNull(a.get(String.class, "someStringValue"));

    a.set("someStringValue", "This is a string thing");

    assertEquals("This is a string thing", a.get(String.class, "someStringValue"));
  }

  @Test
  public void handlesMultipleDifferentTypesInBlob() {
    Actor a = new Actor();
    a.set("integer", new Integer(14));
    a.set("string", "Some string here");

    assertEquals(14, a.get(Integer.class, "integer").intValue());
    assertEquals("Some string here", a.get(String.class, "string"));
  }
}
