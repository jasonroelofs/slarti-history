
package org.slartibartfast;

/**
 * A behavior is a concrete set of functionality that can be added
 * to an Actor to change how it behaves in the world.
 * Without Behaviors, Actors are nothing but shells around a JME Node.
 * All logic to be added to an Actor must be added through a Behavior.
 */
public abstract class Behavior {

  protected boolean initialized = false;

  protected Actor actor;

  /**
   * Per-frame call on every Behavior in the system.
   * This method is where all update logic should be put.
   *
   * By default this method does nothing
   *
   * @param delta Seconds since the last frame
   */
  public void perform(float delta) { }

  /**
   * Check to see if this Behavior has been initialized.
   */
  public boolean isInitialized() {
    return initialized;
  }

  /**
   * Initialize this Behavior. The implementation of this
   * method is responsible for setting initialize = true
   *
   * @param params A list of any needed params to pass in.
   */
  public void initialize() {
    initialized = true;
  }

  public Actor getActor() {
    return actor;
  }

  public void setActor(Actor a) {
    actor = a;
  }
}
