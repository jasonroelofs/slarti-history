/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.slartibartfast;

import org.slartibartfast.behaviors.PhysicalBehavior;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author roelofs
 */
public class ActorFactoryTest {

  public ActorFactoryTest() {
  }

  @Test
  public void createReturnsAnActor() {
    Actor a = ActorFactory.create();
    assertNotNull(a);
  }

  @Test
  public void createAddsPhysicalToActor() {
    Actor a = ActorFactory.create();
    assertTrue(a.hasBehavior(PhysicalBehavior.class));
  }
}
