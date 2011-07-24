package org.slartibartfast;

import org.slartibartfast.behaviors.PhysicalBehavior;

/**
 * Factory that produces Actors.
 *
 * @author roelofs
 */
public class ActorManager implements InputReceiver {

  /**
   * Build and return a new Actor, ready for use.
   *
   * @return Actor
   */
  public static Actor create() {
    Actor a = new Actor();
    a.useBehavior(new PhysicalBehavior());
    return a;
  }

  @Override
  public void receiveInput() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
