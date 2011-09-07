package org.slartibartfast.behaviors;

import org.slartibartfast.UserSettings;
import org.slartibartfast.Behavior;
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
public class InputBehavior extends Behavior {

  private String scope;

  private UserKeyMapping keyMapping;
  private UserMouseMapping mouseMapping;

  /**
   * Create a new behavior to work on input under the given scope
   * @param scope
   */
  public InputBehavior(String scope) {
    this.scope = scope;
  }

  public void initialize(InputSystem input, UserSettings settings) {
    keyMapping = settings.getKeyMap(scope);
    mouseMapping = settings.getMouseMap(scope);

    if(keyMapping != null)
      input.mapInputToActor(keyMapping, actor);

    if(mouseMapping != null)
      input.mapInputToActor(mouseMapping, actor);

    initialize();
  }

  public String getScope() {
    return scope;
  }
}
