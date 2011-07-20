package org.slartibartfast;

import java.util.HashMap;
import java.util.Map;

/**
 * An Actor is anything that can / will be put into the game world. Actors can
 * be given any number of Behaviors who will be used to determine how the
 * Actor behaves in the game.
 *
 * Do not instantiate this class directly. Use ActorFactory instead.
 *
 * @author roelofs
 */
public class Actor {

  Map<Class, Behavior> behaviors;

  public Actor() {
    behaviors = new HashMap<Class, Behavior>();
  }

  /**
   * Tell this actor to use a behavior.
   * @param b The behavior object to add to this Actor
   */
  public void useBehavior(Behavior b) {
    behaviors.put(b.getClass(), b);
  }

  /**
   * Query for the existence of a Behavior.
   * @param klass The class of the Behavior being queried.
   * @return
   */
  public boolean hasBehavior(Class klass) {
    return behaviors.containsKey(klass);
  }

  /**
   * Remove a Behavior from this Actor by the class of Behavior
   * the Behavior.
   * @param aClass The class of the Behavior to remove. If this Actor does not
   *               have the requested Behavior this request is ignored.
   */
  public void removeBehavior(Class klass) {
    behaviors.remove(klass);
  }

  /**
   * Get a pointer to an existing behavior on an Actor.
   * Will return null if a behavior of the requested type
   * doesn't exist.
   */
  public <T> T getBehavior(Class<T> klass) {
    return (T) behaviors.get(klass);
  }
}  