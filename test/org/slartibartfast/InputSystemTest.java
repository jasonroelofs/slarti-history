package org.slartibartfast;

import java.util.Arrays;
import org.mockito.InOrder;
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

    system.setInputReceiver(receiver);

    ActionListener listener = getActionListener(system);

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

//  @Test
//  public void useMappingUpdatesAnalogMappings() {
//    fail("Not yet implemented");
//  }

  @Test
  public void useMappingUpdatesActionMappings() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    UserKeyMapping mapping = new UserKeyMapping();
    mapping.put(Events.MoveUp, "UP");
    mapping.put(Events.MoveDown, "DOWN");

    InputManager manager = mock(InputManager.class);
    InputSystem system = new InputSystem(manager);

    // Call to set up the mapping
    system.useMapping(mapping);

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

    // Verify we set up the action listener
    //
    // TODO: Figure out how to test this. The fact that it uses
    //       String[] instead of a Collection class is making it
    //       hard to deal with
    //
//    verify(manager).addListener(any(ActionListener.class),
//            listCapture.capture());
//
//    assertEquals(2, listCapture.getValue().length);
//
//    List<String> theList = Arrays.asList(listCapture.getValue());
//    assertTrue(theList.contains("MoveUp"));
//    assertTrue(theList.contains("MoveDown"));
//
//    verifyNoMoreInteractions(manager);
  }

}
