package org.slartibartfast;

import org.slartibartfast.events.UserKeyMapping;
import org.slartibartfast.events.Events;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slartibartfast.dataProviders.DataResults;
import org.slartibartfast.dataProviders.IDataProvider;
import org.slartibartfast.events.Axis;
import org.slartibartfast.events.UserMouseMapping;

/**
 * Consolidating container of all things User. This will manage
 * all key mappings.
 */
public class UserSettings {
  private static final Logger logger = Logger.getLogger(UserSettings.class.getName());

  private Map<String, UserKeyMapping> keyMappingsByScope;
  private Map<String, UserMouseMapping> mouseMappingsByScope;

  private IDataProvider provider;

  public UserSettings(IDataProvider provider) {
    keyMappingsByScope = new HashMap<String, UserKeyMapping>();
    mouseMappingsByScope = new HashMap<String, UserMouseMapping>();

    this.provider = provider;

    this.provider.open("user_settings");
  }

  public UserKeyMapping getKeyMap(String scope) {
    if(!keyMappingsByScope.containsKey(scope)) {
      loadKeyMappings();
    }

    return keyMappingsByScope.get(scope);
  }

  public UserMouseMapping getMouseMap(String scope) {
    if(!mouseMappingsByScope.containsKey(scope)) {
      loadMouseMappings();
    }

    return mouseMappingsByScope.get(scope);
  }

  /**
   * TODO HACK
   * Move this logic into the User{Key,Mouse}Mappings class
   * and clean this mess up!
   */

  private void loadKeyMappings() {
    DataResults results = provider.getAll("key_mappings");
    UserKeyMapping mapping;

    for(HashMap<String, Object> map : results) {
      mapping = getKeyMappingForScope((String)map.get("scope"));
      mapping.put(Events.get(
              (String)map.get("event")),
              (String)map.get("key"));

      logger.log(Level.INFO, "Found key mapping: {0} / {1} => {2}",
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

  private void loadMouseMappings() {
    DataResults results = provider.getAll("mouse_mappings");
    UserMouseMapping mapping;

    for(HashMap<String, Object> map : results) {
      mapping = getMouseMappingForScope((String)map.get("scope"));
      mapping.put(
              Events.get((String)map.get("event")),
              (String)map.get("axis"),
              Axis.parseDirection((String)map.get("direction")));

      logger.log(Level.INFO, "Found mouse mapping: {0} / {1} => {2} ({3})",
              new Object[]{
                map.get("scope"),
                map.get("axis"),
                map.get("event"),
                map.get("direction")
              });
    }
  }

  private UserMouseMapping getMouseMappingForScope(String scope) {
    if(mouseMappingsByScope.get(scope) == null) {
      mouseMappingsByScope.put(scope, new UserMouseMapping(scope));
    }

    return mouseMappingsByScope.get(scope);
  }
}
