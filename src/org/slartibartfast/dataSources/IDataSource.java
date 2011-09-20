package org.slartibartfast.dataSources;

import org.slartibartfast.dataProviders.DataResults;

/**
 * The IDataSource interface to the raw data provider, whether it
 * be raw file system, sqlite, or what not.
 */
public interface IDataSource {

  /**
   * Run a query against this data store specifying the database to
   * read from and a query string.
   * @param database
   * @param query
   * @param params
   * @return
   */
  public DataResults query(String database, String query, Object ... params);
}
