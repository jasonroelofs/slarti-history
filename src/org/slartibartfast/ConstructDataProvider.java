package org.slartibartfast;

import org.slartibartfast.dataProviders.DataResults;
import org.slartibartfast.dataProviders.IDataProvider;

public class ConstructDataProvider implements IDataProvider {

  @Override
  public void open(String dbName) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public DataResults getAll(String collectionName) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void shutdown() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public ConstructData getConstructDataFor(String constructName) {
    return new ConstructData();
  }
}
