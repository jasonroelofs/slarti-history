package org.slartibartfast;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
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

  private static final String[] allNames;


  static {
    List<String> names = new ArrayList<String>();
    for(Events e : EnumSet.allOf(Events.class)) {
      lookup.put(e.name(), e);
      names.add(e.name());
    }

    allNames = names.toArray(new String[names.size()]);
  }


  public static Events get(String eventName) {
    return lookup.get(eventName);
  }

  public static String[] all() {
    return allNames;
  }

}
