package org.slartibartfast;

import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import java.lang.reflect.Field;
import com.jme3.input.controls.ActionListener;
import java.util.ArrayList;
import java.util.List;
import com.jme3.input.InputManager;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
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

  ActionListener getActionListener(InputSystem system) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    // Pretend JME sent us an action event
    Field listenerField = InputSystem.class.getDeclaredField("actionListener");
    listenerField.setAccessible(true);
    return (ActionListener) listenerField.get(system);
  }

  @Test
  public void sendsActionEventsToInputReceivers() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    InputManager manager = mock(InputManager.class);
    InputSystem system = new InputSystem(manager);
    TestReceiver receiver = new TestReceiver();
    Actor actor = new Actor();

    UserKeyMapping mapping = new UserKeyMapping();
    mapping.put(Events.MoveUp, "UP");

    system.setInputReceiver(receiver);
    system.mapInputToActor(mapping, actor);

    ActionListener listener = getActionListener(system);

    listener.onAction("MoveUp", true, 0.1f);

    system.update(0.1f);

    assertEquals(1, receiver.events.size());

    InputEvent evt = receiver.events.get(0);
    assertEquals("MoveUp", evt.event);
    assertTrue(evt.pressed);
    assertEquals(actor, evt.actor);

    // See that the events get cleared out and not resent the next frame
    system.update(0.1f);

    assertEquals(1, receiver.events.size());
  }

  @Test
  public void sendsAnalogEventsToReceivers() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    InputManager manager = mock(InputManager.class);
    InputSystem system = new InputSystem(manager);
    TestReceiver receiver = new TestReceiver();
    Actor actor = new Actor();

    UserKeyMapping mapping = new UserKeyMapping();
    mapping.put(Events.MoveUp, "UP");

    system.setInputReceiver(receiver);
    system.mapInputToActor(mapping, actor);

    // Pretend JME sent us an action event
    Field listenerField = InputSystem.class.getDeclaredField("analogListener");
    listenerField.setAccessible(true);
    AnalogListener listener = (AnalogListener) listenerField.get(system);

    listener.onAnalog("MoveUp", 12.7f, 0.1f);

    system.update(0.1f);

    InputEvent evt = receiver.events.get(0);
    assertEquals(12.7f, evt.value, 0.01f);
    assertEquals(actor, evt.actor);
  }

//  @Test
//  public void canMapInputToActor_MouseMappings() {
//    fail("Not yet implemented");
//  }

  @Test
  public void canMapInputToActor_KeyMappings() {
    UserKeyMapping mapping = new UserKeyMapping();
    mapping.put(Events.MoveUp, "UP");
    mapping.put(Events.MoveDown, "DOWN");

    InputManager manager = mock(InputManager.class);
    InputSystem system = new InputSystem(manager);

    Actor a = new Actor();

    // Call to set up the mapping
    system.mapInputToActor(mapping, a);

    ArgumentCaptor<KeyTrigger> triggerUp =
            ArgumentCaptor.forClass(KeyTrigger.class);
    ArgumentCaptor<KeyTrigger> triggerDown =
            ArgumentCaptor.forClass(KeyTrigger.class);

    // Verify we added the MoveDown event
    verify(manager).addMapping(eq("MoveDown"), triggerDown.capture());
    assertEquals(KeyInput.KEY_DOWN, triggerDown.getValue().getKeyCode());

    // Verify we added the MoveUp event
    verify(manager).addMapping(eq("MoveUp"), triggerUp.capture());
    assertEquals(KeyInput.KEY_UP, triggerUp.getValue().getKeyCode());

    ArgumentCaptor<String[]> listCapture =
            ArgumentCaptor.forClass(String[].class);
  }

}
