package org.slartibartfast.dataStores;

import org.slartibartfast.events.UserMouseMapping;
import org.slartibartfast.UserSettings;
import org.slartibartfast.events.Events;
import org.slartibartfast.events.UserKeyMapping;
import java.util.HashMap;
import org.junit.Test;

import org.junit.Before;
import org.slartibartfast.dataSources.DataSource;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserSettingsDataStoreTest {

  private UserSettingsDataStore store;
  private DataSource dataSource;

  @Before
  public void setupDataSource() {
    dataSource = mock(DataSource.class);
    store = new UserSettingsDataStore(dataSource);
  }

  @Test
  public void canBeConstructedWithDataSource() {
    assertNotNull(store);
    assertEquals(dataSource, store.getDataSource());
  }

  private HashMap<String, String> buildKeyMap(
          String scope, String event, String key) {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("scope", scope);
    map.put("event", event);
    map.put("key", key);
    return map;
  }

  @Test
  public void canLoadUserKeyMappings() {
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

    when(dataSource.query("user_settings",
            "select * from key_mappings")).thenReturn(results);
    when(dataSource.query("user_settings",
            "select * from mouse_mappings")).thenReturn(new DataResults());


    UserSettings settings = store.load();

    assertEquals(scopeMap, settings.getKeyMap("scope"));
    assertEquals(carMap, settings.getKeyMap("car"));
  }

  private HashMap<String, String> buildMouseMap(
          String scope, String event, String axis, String direction) {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("scope", scope);
    map.put("event", event);
    map.put("axis", axis);
    map.put("direction", direction);
    return map;
  }

  @Test
  public void canLoadUserMouseMappings() {
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

    when(dataSource.query("user_settings",
            "select * from key_mappings")).thenReturn(new DataResults());
    when(dataSource.query("user_settings",
            "select * from mouse_mappings")).thenReturn(results);

    UserSettings settings = store.load();

    assertEquals(scopeMap, settings.getMouseMap("scope"));
    assertEquals(carMap, settings.getMouseMap("car"));
  }
}
