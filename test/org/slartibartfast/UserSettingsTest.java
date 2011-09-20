package org.slartibartfast;

import org.slartibartfast.events.UserMouseMapping;
import org.slartibartfast.events.Events;
import org.junit.Test;
import org.slartibartfast.events.UserKeyMapping;
import static org.junit.Assert.*;

public class UserSettingsTest {

  public UserSettingsTest() {
  }


  @Test
  public void hasKeyMappingsByScope() {
    UserSettings settings = new UserSettings();
    assertNull(settings.getKeyMap("myScope"));

    settings.addKeyMap("myScope", Events.MoveUp, "K");

    UserKeyMapping mapping = settings.getKeyMap("myScope");
    assertNotNull(mapping);
    assertEquals("K", mapping.get(Events.MoveUp));
  }

  @Test
  public void hasMouseMappingsByScope() {
    UserSettings settings = new UserSettings();
    assertNull(settings.getMouseMap("myScope"));

    settings.addMouseMap("myScope", Events.TurnLeft, "MOUSE_X", true);

    UserMouseMapping mapping = settings.getMouseMap("myScope");
    assertNotNull(mapping);
    assertEquals("MOUSE_X", mapping.get(Events.TurnLeft).axis);
    assertTrue(mapping.get(Events.TurnLeft).positiveDir);
  }
}
