package org.slartibartfast;

import org.slartibartfast.behaviors.PhysicalBehavior;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author roelofs
 */
public class ActorManagerTest {

  public ActorManagerTest() {
  }

  @Test
  public void createReturnsAnActor() {
    Actor a = ActorManager.create();
    assertNotNull(a);
  }

  @Test
  public void createAddsPhysicalToActor() {
    Actor a = ActorManager.create();
    assertTrue(a.hasBehavior(PhysicalBehavior.class));
  }
}
