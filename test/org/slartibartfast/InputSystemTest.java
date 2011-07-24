package org.slartibartfast;

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
  public void sendsEventsToInputReceivers() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
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

    // See that the events get cleared out and not resent the next frame
    system.update(0.1f);

    assertEquals(1, receiver.events.size());
  }
}
