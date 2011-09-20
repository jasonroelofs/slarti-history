package org.slartibartfast.dataStores;

import org.slartibartfast.Construct;
import org.slartibartfast.UserSettings;

/**
 * This class acts as the entry point and factory to all the
 * data store logic needed in the game.
 */
public class DataStoreManager {

  public DataStoreManager() {

  }

  /**
   * Get a data store implementation for the given class
   */
  public IDataStore getDataStoreFor(Class klass) {
    if(klass == UserSettings.class) {
      return new UserSettingsDataStore();
    } else if(klass == Construct.class) {
//      return new ConstructDataStore();
      return null;
    } else {
      return null;
    }
  }
}
