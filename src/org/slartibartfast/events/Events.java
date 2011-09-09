package org.slartibartfast.events;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slartibartfast.behaviors.TransformBehavior;

/**
 * An enumeration of the various events available to input mapping.
 * Each event is given a human readable value.
 */
public enum Events {
  MoveLeft  ("Move Left") {
    @Override
    protected void execute(InputEvent e) {
      TransformBehavior b = e.actor.getBehavior(TransformBehavior.class);
      b.moveLeft();
    }
  },
  MoveRight ("Move Right") {
    @Override
    protected void execute(InputEvent e) {
      TransformBehavior b = e.actor.getBehavior(TransformBehavior.class);
      b.moveRight();
    }
  },
  MoveUp    ("Move Up") {
    @Override
    protected void execute(InputEvent e) {
      TransformBehavior b = e.actor.getBehavior(TransformBehavior.class);
      b.moveUp();
    }
  },
  MoveDown  ("Move Down") {
    @Override
    protected void execute(InputEvent e) {
      TransformBehavior b = e.actor.getBehavior(TransformBehavior.class);
      b.moveDown();
    }
  },
  MoveForward ("Move Forward") {
    @Override
    protected void execute(InputEvent e) {
      TransformBehavior b = e.actor.getBehavior(TransformBehavior.class);
      b.moveForward();

    }
  },
  MoveBackward ("Move Backward") {
    @Override
    protected void execute(InputEvent e) {
      TransformBehavior b = e.actor.getBehavior(TransformBehavior.class);
      b.moveBackward();
    }
  },
  TurnLeft ("Turn Left") {
    @Override
    protected void execute(InputEvent e) {
      TransformBehavior b = e.actor.getBehavior(TransformBehavior.class);
      b.turnLeft(e.value);
    }
  },
  TurnRight ("Turn Right") {
    @Override
    protected void execute(InputEvent e) {
      TransformBehavior b = e.actor.getBehavior(TransformBehavior.class);
      b.turnRight(e.value);
    }
  },
  PitchUp ("Look Up") {
    @Override
    protected void execute(InputEvent e) {
      TransformBehavior b = e.actor.getBehavior(TransformBehavior.class);
      b.pitchUp(e.value);
    }
  },
  PitchDown ("Look Down") {
    @Override
    protected void execute(InputEvent e) {
      TransformBehavior b = e.actor.getBehavior(TransformBehavior.class);
      b.pitchDown(e.value);
    }
  },
  RollLeft ("Roll Left") {
    @Override
    protected void execute(InputEvent e) {
      TransformBehavior b = e.actor.getBehavior(TransformBehavior.class);
      b.rollLeft();
    }
  },
  RollRight ("Roll Right") {
    @Override
    protected void execute(InputEvent e) {
      TransformBehavior b = e.actor.getBehavior(TransformBehavior.class);
      b.rollRight();
    }
  }
  ;

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
  public static Events get(String eventName) throws UnknownEventError {
    Events ret = lookup.get(eventName);
    if(ret != null) {
      return ret;
    } else {
      throw new UnknownEventError(eventName);
    }
  }

  /**
   * Get the names of all events registered in the system.
   */
  public static String[] all() {
    return allNames;
  }

  public static void processEvent(InputEvent e) {
    try {
      Events.get(e.event).execute(e);
    } catch (UnknownEventError error) {
      // TODO get the logger and let people know what's up
      // For now STDERR it goes!
      System.err.println("Error executing event: " + error.getMessage());
    }
  }

}
