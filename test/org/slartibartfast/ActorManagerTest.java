package org.slartibartfast;

import com.jme3.math.Vector3f;
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
  public void canCreateActorAndGiveInitialLocation() {
    Vector3f location = new Vector3f(2.0f, 3.0f, 4.0f);
    Actor a = manager.create(location);
    PhysicalBehavior b = a.getBehavior(PhysicalBehavior.class);

    assertEquals(location, b.getLocation());
  }

  @Test
  public void createAddsPhysicalToActor() {
    Actor a = manager.create();
    assertTrue(a.hasBehavior(PhysicalBehavior.class));
  }
}
