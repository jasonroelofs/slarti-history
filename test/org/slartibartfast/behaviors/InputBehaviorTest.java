package org.slartibartfast.behaviors;

import org.slartibartfast.events.UserMouseMapping;
import org.slartibartfast.events.Events;
import org.slartibartfast.events.InputSystem;
import org.junit.Test;
import org.slartibartfast.events.UserKeyMapping;
import org.slartibartfast.UserSettings;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class InputBehaviorTest {

  public InputBehaviorTest() {
  }

  @Test
  public void constructableWithAScope() {
    InputBehavior b = new InputBehavior("scope");
    assertEquals("scope", b.getScope());
  }

  @Test
  public void initializesTheKeyMappings() {
    InputBehavior b = new InputBehavior("scope");

    UserKeyMapping keyMapping = new UserKeyMapping("testScope");
    keyMapping.put(Events.MoveUp, "Key");

    UserMouseMapping mouseMapping = new UserMouseMapping("testScope");
    mouseMapping.put(Events.MoveUp, "Key", true);

    UserSettings settings = mock(UserSettings.class);
    when(settings.getKeyMap("scope")).thenReturn(keyMapping);
    when(settings.getMouseMap("scope")).thenReturn(mouseMapping);

    InputSystem system = mock(InputSystem.class);

    b.initialize(system, settings);

    verify(system).registerInputListener(b, keyMapping, mouseMapping);
    assertTrue(b.isInitialized());
  }

  @Test
  public void shutdownRemovesListener() {
    InputBehavior b = new InputBehavior("scope");
    InputSystem system = mock(InputSystem.class);

    b.shutdown(system);

    verify(system).unregisterInputListener(b);
    assertFalse(b.isInitialized());
  }
}
