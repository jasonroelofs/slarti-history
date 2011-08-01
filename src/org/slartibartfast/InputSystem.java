package org.slartibartfast;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import java.util.ArrayList;
import java.util.List;

/**
 * This class abstracts the usage of JME's input management
 * into an event queue system.
 *
 * @author roelofs
 */
public class InputSystem {

  private List<InputReceiver> receivers;
  private final InputManager inputManager;

  private List<InputEvent> currentEvents;

  public InputSystem(InputManager manager) {
    inputManager = manager;
    receivers = new ArrayList<InputReceiver>();
    currentEvents = new ArrayList<InputEvent>();
  }

  /**
   * Tell the InputSystem to send input events to the
   * given Receiver. This class will start receiving
   * InputEvents.
   * @param receiver
   */
  public void setInputReceiver(InputReceiver receiver) {
    receivers.add(receiver);
  }

  /**
   * Per-frame update call. Take all queued events and
   * pass them down to each of the receivers we know about.
   */
  public void update(float delta) {
    InputEvent[] eventList = currentEvents.toArray(
            new InputEvent[currentEvents.size()]);
    for(InputReceiver ir : receivers) {
      ir.receiveInput(eventList);
    }

    currentEvents.clear();
  }

  /**
   * Given a UserKeyMapping, set up all required key mappings
   * for inputs to be properly forwarded.
   * @param mapping
   */
  public void useMapping(UserKeyMapping mapping) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  // Plan on this:
  //  some sort of UserKeyMappings class that has the
  //  full list of mappings to key codes, and then run through
  //  that list to set up the actual mappings.
  //  Allow rebuilding of these mappings at any time.
  protected void initializeEvents() {
//    inputManager.addMapping("Forward",
//            new KeyTrigger(KeyInput.KEY_E));
//    inputManager.addMapping("Backward",
//            new KeyTrigger(KeyInput.KEY_D));
//    inputManager.addMapping("Left",
//            new KeyTrigger(KeyInput.KEY_S));
//    inputManager.addMapping("Right",
//            new KeyTrigger(KeyInput.KEY_F));
//
//    inputManager.addMapping("TurnLeft",
//            new KeyTrigger(KeyInput.KEY_LEFT),
//            new MouseAxisTrigger(MouseInput.AXIS_X, false));
//    inputManager.addMapping("TurnRight",
//            new KeyTrigger(KeyInput.KEY_RIGHT),
//            new MouseAxisTrigger(MouseInput.AXIS_X, true));
//
//    inputManager.addListener(actionListener, new String[]{
//      "TurnLeft",
//      "TurnRight",
//      "Forward",
//      "Backward",
//      "Left",
//      "Right"});
//
//    inputManager.addListener(analogListener, new String[]{
//      "TurnLeft",
//      "TurnRight"
//    });
  }

  /**
   * Input event listener for all events that are on/off,
   * like mouse and keyboard button presses.
   */
  private ActionListener actionListener = new ActionListener() {
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
      currentEvents.add(new InputEvent(name, isPressed));
    }
  };

  /**
   * Input event listener for all inputs that return a range
   * of values, like joystick and mouse axis.
   */
  private AnalogListener analogListener = new AnalogListener() {
    @Override
    public void onAnalog(String name, float value, float tpf) {
      currentEvents.add(new InputEvent(name, value));
    }
  };

}
