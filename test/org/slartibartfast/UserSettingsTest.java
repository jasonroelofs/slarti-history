package org.slartibartfast;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserSettingsTest {

  public UserSettingsTest() {
  }

  @Test
  public void canGetKeyMappingsForAScope() {
    UserSettings settings = new UserSettings();
    UserKeyMapping keyMap = new UserKeyMapping();
    keyMap.put(Events.MoveLeft, "K");
    keyMap.put(Events.MoveRight, "J");

    settings.setKeyMap("scope", keyMap);

    assertEquals(keyMap, settings.getKeyMap("scope"));
  }
}
