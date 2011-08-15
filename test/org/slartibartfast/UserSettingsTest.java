package org.slartibartfast;

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

    UserKeyMapping scopeMap = new UserKeyMapping();
    scopeMap.put(Events.MoveLeft, "K");
    scopeMap.put(Events.MoveRight, "J");

    UserKeyMapping carMap = new UserKeyMapping();
    carMap.put(Events.MoveUp, "U");
    carMap.put(Events.MoveDown, "P");

    when(dataMock.getAll("key_mappings")).thenReturn(results);

    assertEquals(scopeMap, settings.getKeyMap("scope"));
    assertEquals(carMap, settings.getKeyMap("car"));

    // Make sure we only query the db once, memoize the results
    verify(dataMock, times(1)).getAll("key_mappings");
  }
}
