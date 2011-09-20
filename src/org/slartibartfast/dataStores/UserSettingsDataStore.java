package org.slartibartfast.dataStores;

import org.slartibartfast.UserSettings;

/**
 * This data store deals with loading and saving information to
 * user_settings.sqlite. Information included in this database:
 *
 *   key mappings
 *   mouse mappings
 *
 */
public class UserSettingsDataStore implements IDataStore<UserSettings> {

  @Override
  public UserSettings load(String key) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
