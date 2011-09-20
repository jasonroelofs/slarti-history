package org.slartibartfast.dataStores;

import org.slartibartfast.dataSources.IDataSource;
import org.slartibartfast.Construct;
import org.slartibartfast.UserSettings;
import org.slartibartfast.dataSources.SQLiteDataSource;

/**
 * This class acts as the entry point and factory to all the
 * data store logic needed in the game.
 */
public class DataStoreManager {

  private IDataSource dataSource;

  public DataStoreManager() {
    dataSource = new SQLiteDataSource();
  }

  /**
   * Get a data store implementation for the given class
   */
  public IDataStore getDataStoreFor(Class klass) {
    if(klass == UserSettings.class) {
      return new UserSettingsDataStore(dataSource);
    } else if(klass == Construct.class) {
      return new ConstructDataStore(dataSource);
    } else {
      return null;
    }
  }
}
