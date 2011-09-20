package org.slartibartfast.dataStores;

import org.slartibartfast.UserSettings;
import org.slartibartfast.dataSources.IDataSource;

/**
 * This data store deals with loading and saving information to
 * user_settings.sqlite. Information included in this database:
 *
 *   key mappings
 *   mouse mappings
 *
 */
public class UserSettingsDataStore implements IDataStore<UserSettings> {

  private final IDataSource dataSource;

  public UserSettingsDataStore(IDataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public IDataSource getDataSource() {
    return dataSource;
  }

  @Override
  public UserSettings load(String key) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
