package org.slartibartfast;

import java.util.HashMap;
import java.util.Map;

/**
 * Consolidating container of all things User. This will manage
 * all key mappings.
 */
public class UserSettings {

  private Map<String, UserKeyMapping> keyMappingsByScope;

  public UserSettings() {
    keyMappingsByScope = new HashMap<String, UserKeyMapping>();

    // TODO Make this read in from user files, sqlite?
    UserKeyMapping lightMover = new UserKeyMapping();
    lightMover.put(Events.MoveLeft,   "LEFT");
    lightMover.put(Events.MoveRight,  "RIGHT");
    lightMover.put(Events.MoveUp,     "UP");
    lightMover.put(Events.MoveDown,   "DOWN");

    keyMappingsByScope.put("lightMover", lightMover);
  }

  public UserKeyMapping getKeyMap(String scope) {
    return keyMappingsByScope.get(scope);
  }

  public void setKeyMap(String scope, UserKeyMapping mapping) {
    keyMappingsByScope.put(scope, mapping);
  }

}
