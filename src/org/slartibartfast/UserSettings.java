package org.slartibartfast;

import org.slartibartfast.events.UserKeyMapping;
import org.slartibartfast.events.Events;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slartibartfast.dataProviders.DataResults;
import org.slartibartfast.dataProviders.IDataProvider;

/**
 * Consolidating container of all things User. This will manage
 * all key mappings.
 */
public class UserSettings {
  private static final Logger logger = Logger.getLogger(UserSettings.class.getName());

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

      logger.log(Level.INFO, "Found kep mapping: {0} / {1} => {2}",
              new Object[]{
                map.get("scope"),
                map.get("key"),
                map.get("event")
              });
    }
  }

  private UserKeyMapping getKeyMappingForScope(String scope) {
    if(keyMappingsByScope.get(scope) == null) {
      keyMappingsByScope.put(scope, new UserKeyMapping(scope));
    }

    return keyMappingsByScope.get(scope);
  }
}
