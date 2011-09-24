package org.slartibartfast.events;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
 */
public class InputSystem {

  private final InputManager inputManager;

  /**
   * Keep track of which listeners want to receive the
   * given event type. Key is a String to match what we
   * get back from jME's input system.
   */
  private Map<String, List<IInputListener>> listenerMap;

  public InputSystem(InputManager manager) {
    inputManager = manager;
    listenerMap = new HashMap<String, List<IInputListener>>();

    inputManager.setCursorVisible(false);
  }

  public void showMouseCursor() {
    inputManager.setCursorVisible(true);
  }

  public void hideMouseCursor() {
    inputManager.setCursorVisible(false);
  }

  /**
   * Register an input listener with the passed in key and mouse
   * mappings.
   *
   * TODO Possibly find a way to combine the two mapping types
   * together. The reason there's two types is that Axis definitions
   * need the Axis and the direction, while Key definitions just
   * need the Key.
   *
   * @param listener IInputListener object
   * @param keyMapping Key bindings to events
   * @param mouseMapping Mouse axis bindings to events
   */
  public void registerInputListener(
          IInputListener listener,
          UserKeyMapping keyMapping,
          UserMouseMapping mouseMapping) {
    List<String> actionEvents = new ArrayList<String>();
    List<String> analogEvents = new ArrayList<String>();
    String eventKey;
    String scope;
    int keyCode, axisCode;

    if(keyMapping != null) {
      /**
       * Build up internal handling of all key mappings
       */
      scope = keyMapping.getScope();
      for(Entry<Events, String> entry : keyMapping.entrySet()) {
        eventKey = scope + ":" + entry.getKey().name();
        keyCode = Keys.get(entry.getValue());

        // Hold is handled as an analog event so we need to work
        // both for key presses
        analogEvents.add(eventKey);
        actionEvents.add(eventKey);

        inputManager.addMapping(eventKey, new KeyTrigger(keyCode));

        addListenerForEvent(eventKey, listener);
      }
    }

    /**
     * And all internal handling of axis movement mappings
     */
    if(mouseMapping != null) {
      scope = mouseMapping.getScope();
      for(Entry<Events, AxisDefinition> entry : mouseMapping.entrySet()) {
        eventKey = scope + ":" + entry.getKey().name();
        axisCode = Axis.get(entry.getValue().axis);

        analogEvents.add(eventKey);

        inputManager.addMapping(eventKey,
          new MouseAxisTrigger(axisCode,
                  !entry.getValue().positiveDir));

        addListenerForEvent(eventKey, listener);
      }
    }

    if(actionEvents.size() > 0) {
      inputManager.addListener(actionListener,
              actionEvents.toArray(new String[actionEvents.size()]));
    }

    if(analogEvents.size() > 0) {
      inputManager.addListener(analogListener,
            analogEvents.toArray(new String[analogEvents.size()]));
    }
  }

  /**
   * Unregister a previously registered input listener. This will
   * stop the passed in object from receiving any more inputs.
   * @param listener The listener to shut down
   */
  public void unregisterInputListener(IInputListener listener) {
    // TODO As we keep information in a call-optimized manner, removing
    // a listener is a bit convoluted. If this is getting called a
    // lot (which I doubt it will), potentially refactor into something
    // nicer.
    Iterator<IInputListener> iterator;

    for(Entry<String, List<IInputListener>> e : listenerMap.entrySet()) {
      iterator = e.getValue().iterator();

      while(iterator.hasNext()) {
        if(iterator.next().equals(listener)) {
          iterator.remove();
        }
      }
    }

    // Also note: we don't unregister anything from JME right now
    // Could check to see if the listener list for a given Event
    // is empty after this and then unregister, but that's
    // to be dealt with later if needed
  }

  private void addListenerForEvent(String event, IInputListener listener) {
    List<IInputListener> current = getListenersFor(event);
    current.add(listener);
  }

  private List<IInputListener> getListenersFor(String event) {
    if(!listenerMap.containsKey(event)) {
      listenerMap.put(event, new ArrayList<IInputListener>());
    }

    return listenerMap.get(event);
  }

  /**
   * Input event listener for all events that are on/off,
   * like mouse and keyboard button presses.
   */
  private ActionListener actionListener = new ActionListener() {
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
      List<IInputListener> listeners = getListenersFor(name);

      // The event name is prefixed by the scope the event
      // is registered under. Remove the scope string when
      // creating the event
      String eventName = name.split(":")[1];

      for(int i = 0; i < listeners.size(); i++) {
        listeners.get(i).handleInputEvent(
                new InputEvent(eventName, isPressed));
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
      List<IInputListener> listeners = getListenersFor(name);

      // See actionListener
      String eventName = name.split(":")[1];

      for(int i = 0; i < listeners.size(); i++) {
        // JME is using analog listenerMap to keep sending key-down
        // messages to act like "HOLD" events. It's also giving me
        // per frame values automatically, which I don't want.
        // I undo that here to get the raw value and let
        // TransformBehavior take care of time-per-frame delta logic

        listeners.get(i).handleInputEvent(
                new InputEvent(eventName, value / tpf));
      }
    }
  };

  public ActionListener getActionListener() {
    return actionListener;
  }

  public AnalogListener getAnalogListener() {
    return analogListener;
  }
}
