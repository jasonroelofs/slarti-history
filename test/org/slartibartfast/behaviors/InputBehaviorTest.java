package org.slartibartfast.behaviors;

import org.slartibartfast.Events;
import org.slartibartfast.Actor;
import org.slartibartfast.InputSystem;
import org.junit.Test;
import org.slartibartfast.UserKeyMapping;
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
  public void initializesTheKeyset() {
    InputBehavior b = new InputBehavior("scope");
    UserKeyMapping mapping = new UserKeyMapping();
    mapping.put(Events.MoveUp, "Key");

    UserSettings settings = new UserSettings();
    settings.setKeyMap("scope", mapping);

    InputSystem system = mock(InputSystem.class);

    b.initialize(new Actor(), system, settings);

    verify(system).useMapping(mapping);
  }
}
