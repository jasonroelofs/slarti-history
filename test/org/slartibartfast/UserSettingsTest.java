package org.slartibartfast;

import org.slartibartfast.events.UserMouseMapping;
import org.slartibartfast.events.Events;
import org.slartibartfast.events.UserKeyMapping;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import org.slartibartfast.dataProviders.DataResults;
import org.slartibartfast.dataProviders.IDataProvider;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class UserSettingsTest {

  public UserSettingsTest() {
  }

  private IDataProvider dataMock;

  @Before
  public void setup() {
    dataMock = mock(IDataProvider.class);
  }

  @Test
  public void opensTheUserSettingsDB() {
    UserSettings settings = new UserSettings(dataMock);

    verify(dataMock).open(eq("user_settings"));
  }

  private HashMap<String, Object> buildKeyMap(String scope, String event, String key) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("scope", scope);
    map.put("event", event);
    map.put("key", key);
    return map;
  }

  @Test
  public void canGetAndRememberKeyMappingsForAScope() {
    UserSettings settings = new UserSettings(dataMock);

    DataResults results = new DataResults();
    results.add(buildKeyMap("scope", "MoveLeft", "K"));
    results.add(buildKeyMap("scope", "MoveRight", "J"));
    results.add(buildKeyMap("car", "MoveUp", "U"));
    results.add(buildKeyMap("car", "MoveDown", "P"));

    UserKeyMapping scopeMap = new UserKeyMapping("testScope");
    scopeMap.put(Events.MoveLeft, "K");
    scopeMap.put(Events.MoveRight, "J");

    UserKeyMapping carMap = new UserKeyMapping("testScope");
    carMap.put(Events.MoveUp, "U");
    carMap.put(Events.MoveDown, "P");

    when(dataMock.getAll("key_mappings")).thenReturn(results);

    assertEquals(scopeMap, settings.getKeyMap("scope"));
    assertEquals(carMap, settings.getKeyMap("car"));

    // Make sure we only query the db once, memoize the results
    verify(dataMock, times(1)).getAll("key_mappings");
  }

  private HashMap<String, Object> buildMouseMap(String scope, String event, String axis, String direction) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("scope", scope);
    map.put("event", event);
    map.put("axis", axis);
    map.put("direction", direction);
    return map;
  }

  @Test
  public void canGetAndRememberMouseMappingsForAScope() {
    UserSettings settings = new UserSettings(dataMock);

    DataResults results = new DataResults();
    results.add(buildMouseMap("scope", "MoveLeft", "MOUSE_X", "RIGHT"));
    results.add(buildMouseMap("scope", "MoveRight", "MOUSE_X", "LEFT"));
    results.add(buildMouseMap("car", "MoveUp", "MOUSE_Y", "UP"));
    results.add(buildMouseMap("car", "MoveDown", "MOUSE_Y", "DOWN"));

    UserMouseMapping scopeMap = new UserMouseMapping("testScope");
    scopeMap.put(Events.MoveLeft, "MOUSE_X", true);
    scopeMap.put(Events.MoveRight, "MOUSE_X", false);

    UserMouseMapping carMap = new UserMouseMapping("testScope");
    carMap.put(Events.MoveUp, "MOUSE_Y", true);
    carMap.put(Events.MoveDown, "MOUSE_Y", false);

    when(dataMock.getAll("mouse_mappings")).thenReturn(results);

    assertEquals(scopeMap, settings.getMouseMap("scope"));
    assertEquals(carMap, settings.getMouseMap("car"));

    // Make sure we only query the db once, memoize the results
    verify(dataMock, times(1)).getAll("mouse_mappings");
  }
}
