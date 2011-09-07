package org.slartibartfast.behaviors;

import org.slartibartfast.events.UserMouseMapping;
import org.slartibartfast.events.Events;
import org.slartibartfast.Actor;
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
    UserKeyMapping mapping = new UserKeyMapping("testScope");
    mapping.put(Events.MoveUp, "Key");

    UserSettings settings = mock(UserSettings.class);
    when(settings.getKeyMap("scope")).thenReturn(mapping);

    InputSystem system = mock(InputSystem.class);
    Actor a = new Actor();

    b.setActor(a);
    b.initialize(system, settings);

    verify(system).mapInputToActor(mapping, a);
  }


  @Test
  public void initializesTheMouseMappings() {
    InputBehavior b = new InputBehavior("scope");
    UserMouseMapping mapping = new UserMouseMapping("testScope");
    mapping.put(Events.MoveUp, "Key", true);

    UserSettings settings = mock(UserSettings.class);
    when(settings.getMouseMap("scope")).thenReturn(mapping);

    InputSystem system = mock(InputSystem.class);
    Actor a = new Actor();

    b.setActor(a);
    b.initialize(system, settings);

    verify(system).mapInputToActor(mapping, a);
  }
}
