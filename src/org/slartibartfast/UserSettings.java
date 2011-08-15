package org.slartibartfast;

import java.util.HashMap;
import java.util.Map;
import org.slartibartfast.dataProviders.DataResults;
import org.slartibartfast.dataProviders.IDataProvider;

/**
 * Consolidating container of all things User. This will manage
 * all key mappings.
 */
public class UserSettings {

  private Map<String, UserKeyMapping> keyMappingsByScope;
  private IDataProvider provider;

  public UserSettings(IDataProvider provider) {
    keyMappingsByScope = new HashMap<String, UserKeyMapping>();

    this.provider = provider;

    this.provider.open("user_settings");
  }

  public UserKeyMapping getKeyMap(String scope) {
    if(keyMappingsByScope.get(scope) == null) {
      loadKeyMappings();
    }

    return keyMappingsByScope.get(scope);
  }

  private void loadKeyMappings() {
    DataResults results = provider.getAll("key_mappings");
    UserKeyMapping mapping;

    for(HashMap<String, Object> map : results) {
      mapping = getKeyMappingForScope((String)map.get("scope"));
      mapping.put(Events.get(
              (String)map.get("event")),
              (String)map.get("key"));
    }
  }

  private UserKeyMapping getKeyMappingForScope(String scope) {
    if(keyMappingsByScope.get(scope) == null) {
      keyMappingsByScope.put(scope, new UserKeyMapping());
    }

    return keyMappingsByScope.get(scope);
  }
}
