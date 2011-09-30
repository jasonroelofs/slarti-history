package org.slartibartfast;

import org.slartibartfast.events.InputSystem;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slartibartfast.behaviors.ConstructBehavior;
import org.slartibartfast.behaviors.InputBehavior;
import org.slartibartfast.behaviors.PhysicsBehavior;
import org.slartibartfast.behaviors.PlayerPhysicsBehavior;
import org.slartibartfast.behaviors.VisualBehavior;

/**
 * This class takes care of managing all Behaviors in a system.
 * Every SceneGraph needs a reference to a BehaviorController.
 */
public class BehaviorController {

  private Map<Class, List<Behavior>> behaviors;

  private InputSystem inputSystem;
  private UserSettings userSettings;
  private PhysicsSpace physicsSpace;

  public BehaviorController() {
    behaviors = new HashMap<Class, List<Behavior>>();
  }

  public void setInputSystem(InputSystem system) {
    this.inputSystem = system;
  }

  public void setUserSettings(UserSettings settings) {
    this.userSettings = settings;
  }

  public void setPhysicsSpace(PhysicsSpace space) {
    this.physicsSpace = space;
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

    shutdownBehavior(b);
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
   *
   * TODO: Is there a good refactoring that can be done here
   * and shutdownBehavior to remove this if branch and casting mess?
   * A refactoring that doesn't introduce a ton of new objects that
   * have to themselves be given a bunch of data?
   */
  private void initializeBehavior(Behavior b) {
    if(b instanceof InputBehavior) {
      ((InputBehavior)b).initialize(inputSystem, userSettings);
    } else if(b instanceof PhysicsBehavior) {
      ((PhysicsBehavior)b).initialize(physicsSpace);
    } else if(b instanceof PlayerPhysicsBehavior) {
      ((PlayerPhysicsBehavior)b).initialize(physicsSpace);
    } else {
      b.initialize();
    }
  }

  private void shutdownBehavior(Behavior b) {
    if(b instanceof InputBehavior) {
      ((InputBehavior)b).shutdown(inputSystem);
    } else {
      b.shutdown();
    }
  }
}
