package org.slartibartfast;

import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slartibartfast.behaviors.PhysicalBehavior;

/**
 * An enumeration of the various events available to input mapping.
 * Each event is given a human readable value.
 */
public enum Events {
  MoveLeft  ("Move Left") {
    @Override
    protected void execute(InputEvent e) {
      PhysicalBehavior b = e.actor.getBehavior(PhysicalBehavior.class);
      b.move(new Vector3f(-0.1f, 0.0f, 0.0f));
    }
  },
  MoveRight ("Move Right") {
    @Override
    protected void execute(InputEvent e) {
      PhysicalBehavior b = e.actor.getBehavior(PhysicalBehavior.class);
      b.move(new Vector3f(0.1f, 0.0f, 0.0f));
    }
  },
  MoveUp    ("Move Up") {
    @Override
    protected void execute(InputEvent e) {
      PhysicalBehavior b = e.actor.getBehavior(PhysicalBehavior.class);
      b.move(new Vector3f(0.0f, 0.1f, 0.0f));
    }
  },
  MoveDown  ("Move Down") {
    @Override
    protected void execute(InputEvent e) {
      PhysicalBehavior b = e.actor.getBehavior(PhysicalBehavior.class);
      b.move(new Vector3f(0.0f, -0.1f, 0.0f));
    }
  };

  protected abstract void execute(InputEvent e);

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


  /**
   * Get an Event enum value from an event name
   * @param eventName The name of the event
   * @return an Events object
   */
  public static Events get(String eventName) {
    return lookup.get(eventName);
  }

  /**
   * Get the names of all events registered in the system.
   */
  public static String[] all() {
    return allNames;
  }

  public static void processEvent(InputEvent e) {
    Events.get(e.event).execute(e);
  }

}
