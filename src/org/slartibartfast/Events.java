package org.slartibartfast;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of the various events available to input mapping.
 * Each event is given a human readable value.
 */
public enum Events {
  MoveLeft  ("Move Left"),
  MoveRight ("Move Right"),
  MoveUp    ("Move Up"),
  MoveDown  ("Move Down");

  public String humanName;

  Events(String name) {
    this.humanName = name;
  }

  private static final Map<String, Events> lookup =
          new HashMap<String, Events>();

  static {
    for(Events e : EnumSet.allOf(Events.class)) {
      lookup.put(e.name(), e);
    }
  }

  public static Events get(String eventName) {
    return lookup.get(eventName);
  }
}
