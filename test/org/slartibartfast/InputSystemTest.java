package org.slartibartfast;

import org.junit.Before;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import java.lang.reflect.Field;
import com.jme3.input.controls.ActionListener;
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
  private InputManager manager;
  private InputSystem system;
  private Actor actor;

  public InputSystemTest() {
  }

  @Before
  public void setupInputSystem() {
    manager = mock(InputManager.class);
    system = new InputSystem(manager);
    actor = new Actor();
  }

  @Test
  public void updateExecutesAllExistingEvents() {
    List<InputEvent> events = system.getCurrentEvents();
    InputEvent e1 = mock(InputEvent.class);
    InputEvent e2 = mock(InputEvent.class);
    events.add(e1);
    events.add(e2);

    system.update(1.0f);

    verify(e1, atMost(1)).process(1.0f);
    verify(e2, atMost(1)).process(1.0f);

    verifyNoMoreInteractions(e1, e2);

    // Make sure the list gets cleared up and we don't
    // duplicate events
    system.update(0.5f);

    verifyNoMoreInteractions(e1, e2);
  }

  @Test
  public void sendsActionEventsToMappedActors() {
    UserKeyMapping mapping = new UserKeyMapping();
    mapping.put(Events.MoveUp, "UP");

    system.mapInputToActor(mapping, actor);

    ActionListener listener = system.getActionListener();

    listener.onAction("MoveUp", true, 0.1f);

    List<InputEvent> events = system.getCurrentEvents();

    assertEquals(1, events.size());

    InputEvent evt = events.get(0);
    assertEquals("MoveUp", evt.event);
    assertTrue(evt.pressed);
    assertEquals(actor, evt.actor);
  }

  @Test
  public void sendsAnalogEventsToMappedActors() {
    UserKeyMapping mapping = new UserKeyMapping();
    mapping.put(Events.MoveUp, "UP");

    system.mapInputToActor(mapping, actor);

    AnalogListener listener = system.getAnalogListener();

    listener.onAnalog("MoveUp", 12.7f, 0.1f);

    List<InputEvent> events = system.getCurrentEvents();

    InputEvent evt = events.get(0);
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

    // Call to set up the mapping
    system.mapInputToActor(mapping, actor);

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
