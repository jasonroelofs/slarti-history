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
    lightMover.put(Events.MoveLeft,   "H");
    lightMover.put(Events.MoveRight,  "L");
    lightMover.put(Events.MoveUp,     "K");
    lightMover.put(Events.MoveDown,   "J");

    keyMappingsByScope.put("lightMover", lightMover);
  }

  public UserKeyMapping getKeyMap(String scope) {
    return keyMappingsByScope.get(scope);
  }

  public void setKeyMap(String scope, UserKeyMapping mapping) {
    keyMappingsByScope.put(scope, mapping);
  }

}
