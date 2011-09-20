package org.slartibartfast.dataSources;

import org.slartibartfast.dataStores.DataResults;

/**
 * The IDataSource interface to the raw data provider, whether it
 * be raw file system, sqlite, or what not.
 */
public interface IDataSource {

  /**
   * Run a query against this data store specifying the database to
   * read from and a query string.
   *
   * Uses prepared statement syntax to bind parameters to the query.
   *
   * @param database
   * @param query
   * @param params List of params to bind to the query
   * @return
   */
  public DataResults query(String database, String query, Object ... params);

  /**
   * Let the data source implement how to shut itself down.
   */
  public void shutdown();
}
