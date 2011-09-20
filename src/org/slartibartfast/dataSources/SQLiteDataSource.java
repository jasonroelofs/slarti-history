package org.slartibartfast.dataSources;

import org.slartibartfast.dataProviders.DataResults;

public class SQLiteDataSource implements IDataSource {

  @Override
  public DataResults query(String database, String query, Object... params) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
