package org.slartibartfast;

import org.slartibartfast.events.UserKeyMapping;
import org.slartibartfast.events.Events;
import java.util.HashMap;
import java.util.Map;
import org.slartibartfast.events.AxisDefinition;
import org.slartibartfast.events.UserMouseMapping;

/**
 * Consolidating container of all things User. This will manage
 * all key mappings.
 */
public class UserSettings {

  private Map<String, UserKeyMapping> keyMappingsByScope;
  private Map<String, UserMouseMapping> mouseMappingsByScope;

  public UserSettings() {
    keyMappingsByScope = new HashMap<String, UserKeyMapping>();
    mouseMappingsByScope = new HashMap<String, UserMouseMapping>();
  }

  public UserKeyMapping getKeyMap(String scope) {
    return keyMappingsByScope.get(scope);
  }

  public UserMouseMapping getMouseMap(String scope) {
    return mouseMappingsByScope.get(scope);
  }

  public void addKeyMap(String scope, Events event, String key) {
    getKeyMappingForScope(scope).put(event, key);
  }

  public void addMouseMap(
          String scope, Events event, String axis, boolean isPositive) {
    getMouseMappingForScope(scope).put(event,
            new AxisDefinition(axis, isPositive));
  }

  private UserMouseMapping getMouseMappingForScope(String scope) {
    if(mouseMappingsByScope.get(scope) == null) {
      mouseMappingsByScope.put(scope, new UserMouseMapping(scope));
    }

    return mouseMappingsByScope.get(scope);
  }

  private UserKeyMapping getKeyMappingForScope(String scope) {
    if(keyMappingsByScope.get(scope) == null) {
      keyMappingsByScope.put(scope, new UserKeyMapping(scope));
    }

    return keyMappingsByScope.get(scope);
  }


}
