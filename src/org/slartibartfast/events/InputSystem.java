package org.slartibartfast.events;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slartibartfast.Actor;

/**
 * This class abstracts the usage of JME's input management
 * into an event queue system.
 *
 * Here's how input is currently handled:
 *
 * Start by giving an Actor an InputBehavior with a scope.
 * Input mapping (keys to events) is defined by the scope.
 * On initialization of the behavior, the appropriate set of key
 *   mapping is sent to mapInputToActor
 *
 * When an input event comes in we:
 *
 * Find all actors waiting for that event
 * Build InputEvent with the appropriate information
 *
 * On Update (per frame):
 *
 * Process all InputEvents in the Current list
 *
 * @author roelofs
 */
public class InputSystem {

  private final InputManager inputManager;

  private List<InputEvent> currentEvents;

  /**
   * Map which keeps track of which actor is supposed to
   * receive which input event.
   */
  private Map<String, List<Actor>> actorMapping;

  public InputSystem(InputManager manager) {
    inputManager = manager;
    currentEvents = new ArrayList<InputEvent>();
    actorMapping = new HashMap<String, List<Actor>>();
    inputManager.setCursorVisible(false);
  }

  /**
   * Per-frame update call. Take all queued events and
   * execute them for the current frame.
   */
  public void update(float delta) {
    if(currentEvents.isEmpty()) {
      return;
    }

    // Should events get pulled inside, or at least
    // pulled out of the Enum system it's currently in?
    // This feels cludgy
    for(InputEvent e : currentEvents) {
      e.process();
    }

    currentEvents.clear();
  }

  /**
   * Given a UserKeyMapping, set up all required key mappings
   * for inputs to be properly forwarded.
   * @param mapping
   */
  public void mapInputToActor(UserKeyMapping mapping, Actor actor) {
    List<String> events = new ArrayList<String>(mapping.size());
    String eventName;
    String scope = mapping.getScope();
    int keyCode;

    for(Entry<Events, String> entry : mapping.entrySet()) {
      eventName = scope + ":" + entry.getKey().name();
      keyCode = Keys.get(entry.getValue());

      events.add(eventName);
      addActorToEvent(eventName, actor);

      inputManager.addMapping(eventName, new KeyTrigger(keyCode));

      inputManager.addListener(actionListener, eventName);
//      inputManager.addListener(analogListener, eventName);
    }
  }

  public void mapInputToActor(UserMouseMapping mapping, Actor actor) {
    List<String> events = new ArrayList<String>(mapping.size());
    String eventName;
    String scope = mapping.getScope();
    int axisCode;

    for (Entry<Events, AxisDefinition> entry : mapping.entrySet()) {
      eventName = scope + ":" + entry.getKey().name();
      axisCode = Axis.get(entry.getValue().axis);

      events.add(eventName);
      addActorToEvent(eventName, actor);

      inputManager.addMapping(eventName,
              new MouseAxisTrigger(axisCode,
                      entry.getValue().positiveDir));

      inputManager.addListener(analogListener, eventName);
    }
  }

  private void addActorToEvent(String eventName, Actor actor) {
    if(actorMapping.get(eventName) == null) {
      actorMapping.put(eventName, new LinkedList<Actor>());
    }

    actorMapping.get(eventName).add(actor);
  }

  private List<Actor> getActors(String eventName) {
    return actorMapping.get(eventName);
  }

  /**
   * Input event listener for all events that are on/off,
   * like mouse and keyboard button presses.
   */
  private ActionListener actionListener = new ActionListener() {
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
      List<Actor> actors = getActors(name);
      if(actors != null) {

        // The event name is prefixed by the scope the event
        // is registered under. Remove the scope string when
        // creating the event
        String eventName = name.split(":")[1];

        for(Actor a : actors) {
          currentEvents.add(new InputEvent(a, eventName, isPressed));
        }
      }
    }
  };

  /**
   * Input event listener for all inputs that return a range
   * of values, like joystick and mouse axis.
   */
  private AnalogListener analogListener = new AnalogListener() {
    @Override
    public void onAnalog(String name, float value, float tpf) {
      List<Actor> actors = getActors(name);
      if(actors != null) {

        // See actionListener
        String eventName = name.split(":")[1];

        for(Actor a : actors) {
          currentEvents.add(new InputEvent(a, eventName, value));
        }
      }
    }
  };

  public List<InputEvent> getCurrentEvents() {
    return currentEvents;
  }

  public ActionListener getActionListener() {
    return actionListener;
  }

  public AnalogListener getAnalogListener() {
    return analogListener;
  }
}
