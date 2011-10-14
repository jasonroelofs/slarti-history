package org.slartibartfast.dataStores;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slartibartfast.UserSettings;
import org.slartibartfast.dataSources.DataSource;
import org.slartibartfast.events.Axis;
import org.slartibartfast.events.Events;

/**
 * This data store deals with loading and saving information to
 * user_settings.sqlite. Information included in this database:
 *
 *   key mappings
 *   mouse mappings
 *
 */
public class UserSettingsDataStore implements DataRepository<UserSettings> {
  private static final Logger logger = Logger.getLogger(
          UserSettingsDataStore.class.getName());

  private final DataSource dataSource;

  public UserSettingsDataStore(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public DataSource getDataSource() {
    return dataSource;
  }

  @Override
  public UserSettings load(String key) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * Pull all defined key and mouse mappings from the user_settings
   * database and build up an appropriate UserSettings object
   * representing this information
   * @return
   */
  @Override
  public UserSettings load() {
    UserSettings userSettings = new UserSettings();

    DataResults results = dataSource.query("user_settings",
            "select * from key_mappings");

    for(HashMap<String, String> map : results) {
      userSettings.addKeyMap(
              map.get("scope"),
              Events.get(map.get("event")),
              map.get("key"));

      logger.log(Level.INFO, "Found key mapping: {0} / {1} => {2}",
              new Object[]{
                map.get("scope"),
                map.get("key"),
                map.get("event")
              });
    }

    results = dataSource.query("user_settings",
        "select * from mouse_mappings");

    for(HashMap<String, String> map : results) {
      userSettings.addMouseMap(
              map.get("scope"),
              Events.get(map.get("event")),
              map.get("axis"),
              Axis.parseDirection(map.get("direction")));

      logger.log(Level.INFO,
              "Found mouse mapping: {0} / {1} => {2} ({3})",
              new Object[]{
                map.get("scope"),
                map.get("axis"),
                map.get("event"),
                map.get("direction")
              });
    }

    return userSettings;
  }

}
