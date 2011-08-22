package org.slartibartfast;

import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

  private Map<Class, Behavior> behaviors;

  private Map<String, Object> data;

  /**
   * All actors have a unique id in the system
   */
  private long id;

  private final BehaviorController behaviorController;

  /** All actors have a node that hooks them into the scene */
  private Node node;

  /**
   * Build a skeleton Actor. In most cases you'll want
   * the next constructor taking a BehaviorController.
   */
  public Actor() {
    this(null);
  }

  /**
   * Create a new Actor with a link to the current scene's
   * behavior controller, allowing registration with the
   * update loop.
   * @param controller
   */
  public Actor(BehaviorController controller) {
    behaviorController = controller;
    behaviors = new HashMap<Class, Behavior>();
    data = new HashMap<String, Object>();
  }

  /**
   * Tell this actor to use a behavior.
   * @param b The behavior object to add to this Actor
   */
  public void useBehavior(Behavior b) {
    behaviors.put(b.getClass(), b);

    b.setActor(this);

    if(behaviorController != null) {
      behaviorController.registerBehavior(b);
    }
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
    Behavior b = behaviors.remove(klass);

    if(behaviorController != null) {
      behaviorController.unregisterBehavior(b);
    }
  }

  /**
   * Get a pointer to an existing behavior on an Actor.
   * Will return null if a behavior of the requested type
   * doesn't exist.
   */
  public <T> T getBehavior(Class<T> klass) {
    return (T) behaviors.get(klass);
  }

  /**
   * Get a piece of data out of the Actor's blob storage
   *
   * @param dataClass - The class of the data you're trying to get
   * @param dataKey - The key in which the data is stored
   * @return
   */
  public <T> T get(Class<T> dataClass, String dataKey) {
    return (T)data.get(dataKey);
  }

  /**
   * Set data of a random type into the Actor's blob
   *
   * @param key The key to save the data in
   * @param dataObj The object to be saved
   */
  public <T> void set(String key, T dataObj) {
    data.put(key, dataObj);
  }

  public Node getNode() {
    return this.node;
  }

  public void setNode(Node node) {
    this.node = node;
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(long id) {
    this.id = id;
  }

  public ArrayList<Behavior> getBehaviors() {
    ArrayList<Behavior> list = new ArrayList(behaviors.size());
    for(Map.Entry<Class, Behavior> e : behaviors.entrySet()) {
      list.add(e.getValue());
    }
    return list;
  }

  public BehaviorController getBehaviorController() {
    return behaviorController;
  }
}
