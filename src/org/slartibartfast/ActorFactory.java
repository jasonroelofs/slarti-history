package org.slartibartfast;

import org.slartibartfast.behaviors.Physical;

/**
 * Factory that produces Actors.
 * 
 * @author roelofs
 */
public class ActorFactory {

  /**
   * Build and return a new Actor, ready for use.
   * 
   * @return Actor 
   */
  public static Actor create() {
    Actor a = new Actor();
    a.useBehavior(new Physical());
    return a;
  }
  
}
