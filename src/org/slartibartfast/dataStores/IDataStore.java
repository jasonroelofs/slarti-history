package org.slartibartfast.dataStores;

import org.slartibartfast.dataSources.DataSource;

/**
 * Public interface of all data store implementers
 */
public interface IDataStore<T> {

  public T load();

  public T load(String key);

  public DataSource getDataSource();
}
