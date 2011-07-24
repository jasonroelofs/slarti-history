package org.slartibartfast;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
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

    initializeEvents();
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
   * Per-frame update call.
   */
  public void update(float delta) {
    InputEvent[] eventList = currentEvents.toArray(
            new InputEvent[currentEvents.size()]);
    for(InputReceiver ir : receivers) {
      ir.receiveInput(eventList);
    }

    currentEvents.clear();
  }

  protected void initializeEvents() {
    inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_E));
    inputManager.addMapping("Backward", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_S));
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_F));

    inputManager.addListener(actionListener, new String[]{
      "Forward",
      "Backward",
      "Left",
      "Right"});
  }

  private ActionListener actionListener = new ActionListener() {

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
      currentEvents.add(new InputEvent(name, isPressed));
    }

  };
}
