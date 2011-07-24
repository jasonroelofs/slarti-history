package org.slartibartfast;

import com.jme3.input.controls.AnalogListener;
import java.lang.reflect.Field;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.event.KeyInputEvent;
import java.util.ArrayList;
import java.util.List;
import com.jme3.input.InputManager;
import static org.mockito.Mockito.*;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author roelofs
 */
public class InputSystemTest {

  public InputSystemTest() {
  }

  class TestReceiver implements InputReceiver {
    public List<InputEvent> events;

    TestReceiver() {
      events = new ArrayList<InputEvent>();
    }

    @Override
    public void receiveInput(InputEvent[] events) {
      for(InputEvent evt: events) {
        this.events.add(evt);
      }
    }

  }

  @Test
  public void sendsActionEventsToInputReceivers() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    InputManager manager = mock(InputManager.class);
    InputSystem system = new InputSystem(manager);
    TestReceiver receiver = new TestReceiver();

    system.setInputReceiver(receiver);

    // Pretend JME sent us an action event
    Field listenerField = InputSystem.class.getDeclaredField("actionListener");
    listenerField.setAccessible(true);
    ActionListener listener = (ActionListener) listenerField.get(system);

    listener.onAction("Forward", true, 0.1f);

    system.update(0.1f);

    assertEquals(1, receiver.events.size());

    InputEvent evt = receiver.events.get(0);
    assertEquals("Forward", evt.event);
    assertTrue(evt.pressed);

    // See that the events get cleared out and not resent the next frame
    system.update(0.1f);

    assertEquals(1, receiver.events.size());
  }

  @Test
  public void sendsAnalogEventsToReceivers() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    InputManager manager = mock(InputManager.class);
    InputSystem system = new InputSystem(manager);
    TestReceiver receiver = new TestReceiver();

    system.setInputReceiver(receiver);

    // Pretend JME sent us an action event
    Field listenerField = InputSystem.class.getDeclaredField("analogListener");
    listenerField.setAccessible(true);
    AnalogListener listener = (AnalogListener) listenerField.get(system);

    listener.onAnalog("TurnLeft", 12.7f, 0.1f);

    system.update(0.1f);

    InputEvent evt = receiver.events.get(0);
    assertEquals(12.7f, evt.value, 0.01f);
  }

}
