package org.slartibartfast.behaviors;

import org.slartibartfast.UserSettings;
import org.slartibartfast.Actor;
import org.slartibartfast.Behavior;
import org.slartibartfast.InputSystem;
import org.slartibartfast.UserKeyMapping;

/**
 * Add this behavior to an Actor to hook up Input events
 * to this actor. All input mappings are defined under a scope (global,
 * vehicle, menu, etc). An InputBehavior concerns itself with only
 * one scope, so to add multiple scopes to a single Actor, add
 * multiple behaviors.
 */
public class InputBehavior extends Behavior {

  private String scope;

  private UserKeyMapping mapping;

  /**
   * Create a new behavior to work on input under the given scope
   * @param scope
   */
  public InputBehavior(String scope) {
    this.scope = scope;
  }

  @Override
  public void initialize(Actor actor, Object ... params) {
    InputSystem input = (InputSystem) params[0];
    UserSettings settings = (UserSettings) params[1];
    mapping = settings.getKeyMap(scope);

    input.useMapping(mapping);
  }

  public String getScope() {
    return scope;
  }
}
