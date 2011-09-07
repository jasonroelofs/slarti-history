package org.slartibartfast.events;

import org.slartibartfast.events.Events;
import org.slartibartfast.events.UserKeyMapping;
import org.slartibartfast.events.UserMouseMapping;
import org.slartibartfast.events.InputEvent;
import org.slartibartfast.events.InputSystem;
import com.jme3.input.MouseInput;
import org.junit.Before;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.ActionListener;
import java.util.List;
import com.jme3.input.InputManager;
import com.jme3.input.controls.MouseAxisTrigger;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.slartibartfast.Actor;
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
    verify(manager).setCursorVisible(false);

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

    verify(e1, atMost(1)).process();
    verify(e2, atMost(1)).process();

    verifyNoMoreInteractions(e1, e2);

    // Make sure the list gets cleared up and we don't
    // duplicate events
    system.update(0.5f);

    verifyNoMoreInteractions(e1, e2);
  }

  @Test
  public void mapsKeyEventsToActorsByScope() {
    Actor a1 = new Actor();
    Actor a2 = new Actor();

    UserKeyMapping mapping1 = new UserKeyMapping("testScope1");
    mapping1.put(Events.MoveUp, "UP");

    UserKeyMapping mapping2 = new UserKeyMapping("testScope2");
    mapping2.put(Events.MoveUp, "DOWN");

    system.mapInputToActor(mapping1, a1);
    system.mapInputToActor(mapping2, a2);

    verify(manager).addMapping(eq("testScope1:MoveUp"),
            any(KeyTrigger.class));
    verify(manager).addMapping(eq("testScope2:MoveUp"),
            any(KeyTrigger.class));

    verify(manager, atLeastOnce()).addListener(any(ActionListener.class),
        eq("testScope1:MoveUp"));
    verify(manager, atLeastOnce()).addListener(any(AnalogListener.class),
        eq("testScope1:MoveUp"));

    verify(manager, atLeastOnce()).addListener(any(ActionListener.class),
        eq("testScope2:MoveUp"));
     verify(manager, atLeastOnce()).addListener(any(AnalogListener.class),
        eq("testScope2:MoveUp"));

    verifyNoMoreInteractions(manager);
  }

  @Test
  public void mapsMouseEventsToActorsByScope() {
    Actor a1 = new Actor();
    Actor a2 = new Actor();

    UserMouseMapping mapping1 = new UserMouseMapping("testScope1");
    mapping1.put(Events.MoveUp, "MOUSE_X", true);

    UserMouseMapping mapping2 = new UserMouseMapping("testScope2");
    mapping2.put(Events.MoveUp, "MOUSE_X", false);

    system.mapInputToActor(mapping1, a1);
    system.mapInputToActor(mapping2, a2);

    verify(manager).addMapping(eq("testScope1:MoveUp"),
            any(MouseAxisTrigger.class));
    verify(manager).addMapping(eq("testScope2:MoveUp"),
            any(MouseAxisTrigger.class));

    verify(manager, atLeastOnce()).addListener(any(AnalogListener.class),
        eq("testScope1:MoveUp"));

    verify(manager, atLeastOnce()).addListener(any(AnalogListener.class),
        eq("testScope2:MoveUp"));

    verifyNoMoreInteractions(manager);
  }

  @Test
  public void sendsActionEventsToMappedActors() {
    UserKeyMapping mapping = new UserKeyMapping("testScope");
    mapping.put(Events.MoveUp, "UP");

    system.mapInputToActor(mapping, actor);

    ActionListener listener = system.getActionListener();

    listener.onAction("testScope:MoveUp", true, 0.1f);

    List<InputEvent> events = system.getCurrentEvents();

    assertEquals(1, events.size());

    InputEvent evt = events.get(0);
    assertEquals("MoveUp", evt.event);
    assertTrue(evt.pressed);
    assertEquals(actor, evt.actor);
  }

  @Test
  public void sendsAnalogEventsToMappedActors() {
    UserMouseMapping mapping = new UserMouseMapping("testScope");
    mapping.put(Events.MoveUp, "MOUSE_X", true);

    system.mapInputToActor(mapping, actor);

    AnalogListener listener = system.getAnalogListener();

    listener.onAnalog("testScope:MoveUp", 12.7f, 0.1f);

    List<InputEvent> events = system.getCurrentEvents();

    InputEvent evt = events.get(0);
    assertEquals(12.7f, evt.value, 0.01f);
    assertEquals(actor, evt.actor);
  }

  @Test
  public void properlyDelegatesAccordingToScope() {
    Actor a1 = new Actor();
    Actor a2 = new Actor();

    UserKeyMapping mapping1 = new UserKeyMapping("testScope1");
    mapping1.put(Events.MoveUp, "UP");

    UserKeyMapping mapping2 = new UserKeyMapping("testScope2");
    mapping2.put(Events.MoveUp, "DOWN");

    system.mapInputToActor(mapping1, a1);
    system.mapInputToActor(mapping2, a2);

    AnalogListener listener = system.getAnalogListener();

    listener.onAnalog("testScope1:MoveUp", 12.7f, 0.1f);
    listener.onAnalog("testScope2:MoveUp", 2.3f, 0f);

    List<InputEvent> events = system.getCurrentEvents();
    assertEquals(2, events.size());

    InputEvent evt = events.get(0);
    assertEquals(a1, evt.actor);

    evt = events.get(1);
    assertEquals(a2, evt.actor);
  }

  @Test
  public void canMapInputToActor_KeyMappings() {
    UserKeyMapping mapping = new UserKeyMapping("testScope");
    mapping.put(Events.MoveUp, "UP");
    mapping.put(Events.MoveDown, "DOWN");

    // Call to set up the mapping
    system.mapInputToActor(mapping, actor);

    ArgumentCaptor<KeyTrigger> triggerUp =
            ArgumentCaptor.forClass(KeyTrigger.class);
    ArgumentCaptor<KeyTrigger> triggerDown =
            ArgumentCaptor.forClass(KeyTrigger.class);

    // Verify we added the MoveDown event
    verify(manager).addMapping(eq("testScope:MoveDown"),
            triggerDown.capture());
    assertEquals(KeyInput.KEY_DOWN, triggerDown.getValue().getKeyCode());

    // Verify we added the MoveUp event
    verify(manager).addMapping(eq("testScope:MoveUp"),
            triggerUp.capture());
    assertEquals(KeyInput.KEY_UP, triggerUp.getValue().getKeyCode());
  }

  @Test
  public void canMapInputToActor_MouseMappings() {
    UserMouseMapping mapping = new UserMouseMapping("testScope");
    mapping.put(Events.MoveUp, "MOUSE_Y", true);
    mapping.put(Events.MoveDown, "MOUSE_Y", false);

    // Call to set up the mapping
    system.mapInputToActor(mapping, actor);

    ArgumentCaptor<MouseAxisTrigger> triggerUp =
            ArgumentCaptor.forClass(MouseAxisTrigger.class);
    ArgumentCaptor<MouseAxisTrigger> triggerDown =
            ArgumentCaptor.forClass(MouseAxisTrigger.class);

    // Verify we added the MoveDown event
    verify(manager).addMapping(eq("testScope:MoveDown"),
            triggerDown.capture());
    assertEquals(MouseInput.AXIS_Y, triggerDown.getValue().getMouseAxis());

    // Verify we added the MoveUp event
    verify(manager).addMapping(eq("testScope:MoveUp"),
            triggerUp.capture());
    assertEquals(MouseInput.AXIS_Y, triggerUp.getValue().getMouseAxis());
  }


}
