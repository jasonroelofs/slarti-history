package org.slartibartfast.events;

import org.mockito.ArgumentMatcher;
import java.util.ArrayList;
import org.junit.Before;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.ActionListener;
import java.util.List;
import com.jme3.input.InputManager;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.junit.Assert.*;

public class InputSystemTest {
  private InputManager manager;
  private InputSystem system;
  private UserKeyMapping keyMapping;
  private UserMouseMapping mouseMapping;

  public InputSystemTest() {
  }

  @Before
  public void setupInputSystem() {
    manager = mock(InputManager.class);
    system = new InputSystem(manager);
    verify(manager).setCursorVisible(false);


    keyMapping = new UserKeyMapping("testScope1");
    keyMapping.put(Events.MoveUp, "UP");
    keyMapping.put(Events.MoveDown, "DOWN");
    keyMapping.put(Events.Select, "MOUSE_LEFT");

    mouseMapping = new UserMouseMapping("testScope1");
    mouseMapping.put(Events.TurnLeft, "MOUSE_X", true);
    mouseMapping.put(Events.TurnRight, "MOUSE_X", false);
  }

  @Test
  public void canShowAndHideMouseCursor() {
    system.showMouseCursor();

    verify(manager).setCursorVisible(true);

    system.hideMouseCursor();

    // Default is to hide it, see that we get 2 calls
    // in this case
    verify(manager, times(2)).setCursorVisible(false);
  }

  @Test
  public void registersListenerForEvents() {
    IInputListener listener = mock(IInputListener.class);

    system.registerInputListener(listener, keyMapping, mouseMapping);

    /**
     * Verify hooking up keyboard actions to JME
     */
    verify(manager).addMapping(eq("testScope1:MoveUp"),
        any(KeyTrigger.class));
    verify(manager).addMapping(eq("testScope1:MoveDown"),
            any(KeyTrigger.class));

    /**
     * Verify hooking up Mouse button actions to JME
     */
    verify(manager).addMapping(eq("testScope1:Select"),
        any(MouseButtonTrigger.class));

    /**
     * Verify hooking up mouse actions to JME
     */
    verify(manager).addMapping(eq("testScope1:TurnLeft"),
        any(MouseAxisTrigger.class));
    verify(manager).addMapping(eq("testScope1:TurnRight"),
            any(MouseAxisTrigger.class));

    // Not sure how best to test the addListener calls.
    // Previous attempt was too fragile.
  }

  class TestListener implements IInputListener {
    public List<InputEvent> received;

    public TestListener() {
      received = new ArrayList<InputEvent>();
    }

    @Override
    public void handleInputEvent(InputEvent event) {
      received.add(event);
    }

  }

  @Test
  public void sendsActionEventsToRegisteredListeners() {
    TestListener tester = new TestListener();

    system.registerInputListener(tester, keyMapping, mouseMapping);

    ActionListener listener = system.getActionListener();

    listener.onAction("testScope1:MoveUp", true, 0.1f);

    List<InputEvent> events = tester.received;

    assertEquals(1, events.size());

    InputEvent evt = events.get(0);
    assertEquals("MoveUp", evt.event);
    assertTrue(evt.pressed);
  }

  @Test
  public void sendsAnalogEventsToRegisteredListeners() {
    TestListener tester = new TestListener();

    system.registerInputListener(tester, keyMapping, mouseMapping);

    AnalogListener listener = system.getAnalogListener();

    listener.onAnalog("testScope1:TurnLeft", 12.7f, 1.0f);

    List<InputEvent> events = tester.received;

    InputEvent evt = events.get(0);
    assertEquals("TurnLeft", evt.event);
    assertEquals(12.7f, evt.value, 0.01f);
  }

  @Test
  public void properlyDelegatesAccordingToScope() {
    TestListener tester1 = new TestListener();
    TestListener tester2 = new TestListener();

    UserKeyMapping key2 = new UserKeyMapping("testScope2");
    key2.put(Events.MoveUp, "UP");
    key2.put(Events.MoveUp, "DOWN");

    UserMouseMapping mouse2 = new UserMouseMapping("testScope2");

    system.registerInputListener(tester1, keyMapping, mouseMapping);
    system.registerInputListener(tester2, key2, mouse2);

    ActionListener listener = system.getActionListener();

    listener.onAction("testScope1:MoveUp", true, 1.0f);
    listener.onAction("testScope2:MoveUp", false, 0f);

    List<InputEvent> events;

    events = tester1.received;
    assertEquals(1, events.size());
    assertEquals("MoveUp", events.get(0).event);
    assertEquals(1.0f, events.get(0).value, 0.001);

    events = tester2.received;
    assertEquals(1, events.size());
    assertEquals("MoveUp", events.get(0).event);
    assertEquals(0.0f, events.get(0).value, 0.001);
  }

  @Test
  public void canUnregisterInputListener() {
    TestListener tester = new TestListener();

    system.registerInputListener(tester, keyMapping, mouseMapping);

    system.unregisterInputListener(tester);

    ActionListener listener = system.getActionListener();

    listener.onAction("testScope1:MoveUp", true, 0.1f);

    List<InputEvent> events = tester.received;

    // No events for joo!
    assertEquals(0, events.size());
  }
}
