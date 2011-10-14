package org.slartibartfast.dataStores;

import org.slartibartfast.dataSources.DataSource;

/**
 * Public interface of all data repository implementers
 */
public interface DataRepository<T> {

  public T load();

  public T load(String key);

  public DataSource getDataSource();
}
