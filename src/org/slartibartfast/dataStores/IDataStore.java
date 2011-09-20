package org.slartibartfast.dataStores;

/**
 * Public interface of all data store implementers
 */
public interface IDataStore<T> {

  public T load(String key);
  
}
