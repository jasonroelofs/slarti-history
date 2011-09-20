package org.slartibartfast.dataStores;

import org.slartibartfast.dataSources.IDataSource;

/**
 * Public interface of all data store implementers
 */
public interface IDataStore<T> {

  public T load(String key);

  public IDataSource getDataSource();
}
