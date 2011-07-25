package org.slartibartfast;

import org.slartibartfast.behaviors.PhysicalBehavior;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author roelofs
 */
public class ActorManagerTest {

  ActorManager manager;

  public ActorManagerTest() {
    manager = new ActorManager();
  }

  @Test
  public void createReturnsAnActor() {
    Actor a = manager.create();
    assertNotNull(a);
  }

  @Test
  public void createAddsPhysicalToActor() {
    Actor a = manager.create();
    assertTrue(a.hasBehavior(PhysicalBehavior.class));
  }
}
