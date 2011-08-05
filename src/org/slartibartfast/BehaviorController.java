package org.slartibartfast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slartibartfast.behaviors.InputBehavior;

/**
 * This class takes care of managing all Behaviors in a system.
 * Every SceneGraph needs a reference to a BehaviorController.
 */
public class BehaviorController {

  private Map<Class, List<Behavior>> behaviors;

  private InputSystem inputSystem;
  private UserSettings userSettings;

  public BehaviorController() {
    behaviors = new HashMap<Class, List<Behavior>>();
  }

  public void setInputSystem(InputSystem system) {
    this.inputSystem = system;
  }

  public void setUserSettings(UserSettings settings) {
    this.userSettings = settings;
  }

  /**
   * Register a behavior. Will initialize the behavior
   * and register it for per-frame updates.
   * @param b The behavior to register
   */
  public void registerBehavior(Behavior b) {
    List<Behavior> behaviorsForClass = behaviors.get(b.getClass());
    if(behaviorsForClass == null) {
      behaviorsForClass = new LinkedList<Behavior>();
      behaviors.put(b.getClass(), behaviorsForClass);
    }

    behaviorsForClass.add(b);

    initializeBehavior(b);
  }

  /**
   * Unregister a behavior, removing it from all known lists.
   * @param b The behavior to remove
   */
  public void unregisterBehavior(Behavior b) {
    List<Behavior> behaviorsForClass = behaviors.get(b.getClass());
    if(behaviorsForClass != null) {
      behaviorsForClass.remove(b);
    }
  }

  /**
   * Pre-frame update call, which is given the time since last frame.
   */
  public void update(float delta) {
    for(Map.Entry<Class, List<Behavior>> entry : behaviors.entrySet()) {
      for(Behavior b : entry.getValue()) {
        b.perform(delta);
      }
    }
  }

  /**
   * Behavior Initializers, keyed by the class
   */

  // Generic catch-all initializer
  private void initializeBehavior(Behavior b) {
    if(b instanceof InputBehavior) {
      b.initialize(inputSystem, userSettings);
    } else {
      b.initialize();
    }
  }

}
