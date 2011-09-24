package org.slartibartfast.behaviors;

import org.slartibartfast.UserSettings;
import org.slartibartfast.Behavior;
import org.slartibartfast.events.Events;
import org.slartibartfast.events.IInputListener;
import org.slartibartfast.events.InputEvent;
import org.slartibartfast.events.InputSystem;
import org.slartibartfast.events.UserKeyMapping;
import org.slartibartfast.events.UserMouseMapping;

/**
 * Add this behavior to an Actor to hook up Input events
 * to this actor. All input mappings are defined under a scope (global,
 * vehicle, menu, etc). An InputBehavior concerns itself with only
 * one scope, so to add multiple scopes to a single Actor, add
 * multiple behaviors.
 */
public class InputBehavior extends Behavior implements IInputListener {

  private String scope;

  /**
   * Create a new behavior to work on input under the given scope
   * @param scope
   */
  public InputBehavior(String scope) {
    this.scope = scope;
  }

  public String getScope() {
    return scope;
  }

  @Override
  public void handleInputEvent(InputEvent event) {
    System.out.println("Got input event! " + event.event);
    Events.processEvent(getActor(), event);
  }

  public void initialize(InputSystem input, UserSettings settings) {
    UserKeyMapping keyMapping = settings.getKeyMap(scope);
    UserMouseMapping mouseMapping = settings.getMouseMap(scope);

    input.registerInputListener(this, keyMapping, mouseMapping);

    initialize();
  }

  public void shutdown(InputSystem input) {
    input.unregisterInputListener(this);

    shutdown();
  }

}
