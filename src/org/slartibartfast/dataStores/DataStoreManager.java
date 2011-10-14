package org.slartibartfast.dataStores;

import org.slartibartfast.dataSources.DataSource;
import org.slartibartfast.Construct;
import org.slartibartfast.UserSettings;
import org.slartibartfast.dataSources.SQLiteDataSource;

/**
 * This class acts as the entry point and factory to all the
 * data store logic needed in the game.
 */
public class DataStoreManager {

  private DataSource dataSource;

  public DataStoreManager() {
    dataSource = new SQLiteDataSource();
  }

  public void shutdown() {
    dataSource.shutdown();
  }

  /**
   * Get a data store implementation for the given class
   */
  public DataRepository getDataStoreFor(Class klass) {
    if(klass == UserSettings.class) {
      return new UserSettingsDataStore(dataSource);
    } else if(klass == Construct.class) {
      return new ConstructDataStore(dataSource);
    } else {
      return null;
    }
  }

  // For testing
  public void setDataSource(DataSource source) {
    dataSource = source;
  }
}
