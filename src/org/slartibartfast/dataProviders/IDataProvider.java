package org.slartibartfast.dataProviders;

import java.util.Map;

/**
 * Interface for communicating with data providers.
 * This can be user settings, world save data, etc
 */
public interface IDataProvider {

  /**
   * Tell the provider to open the 'database' with
   * the given name
   * @param string Name of the database to open
   */
  public void open(String dbName);

  /**
   * Ask the data provider for all entries in
   * the given collection
   * @param string Name of collection
   */
  public DataResults getAll(String collectionName);

}
