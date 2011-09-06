package org.slartibartfast;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteConstants;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteJob;
import com.almworks.sqlite4java.SQLiteQueue;
import com.almworks.sqlite4java.SQLiteStatement;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slartibartfast.dataProviders.DataResults;
import org.slartibartfast.dataProviders.IDataProvider;
import org.slartibartfast.dataProviders.SQLiteDataProvider;

public class ConstructDataProvider implements IDataProvider {
  private static final Logger logger = Logger.getLogger(SQLiteDataProvider.class.getName());

  private SQLiteQueue queue;

  @Override
  public void open(String dbName) {
    try {
      queue = new SQLiteQueue(SQLiteDataProvider.getDbFile(dbName));
    } catch (IOException ex) {
      logger.log(Level.SEVERE, "Unable to open database " + dbName, ex);
    }
    queue.start();
  }

  @Override
  public DataResults getAll(String collectionName) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void shutdown() {
    if(queue != null) {
      queue.stop(true);
    }
  }

  public ConstructData getConstructDataFor(final String constructName) {
    if(queue == null) {
      open("constructs");
    }

    return queue.execute(new SQLiteJob<ConstructData>() {

      private HashMap<String, Object> getRow(SQLiteStatement st)
              throws SQLiteException {
        HashMap<String, Object> row = new HashMap<String, Object>();
        String columnName, columnValue = null;


        for(int i = 0; i < st.columnCount(); i++) {
          columnName = st.getColumnName(i);
          switch(st.columnType(i)) {
            case SQLiteConstants.SQLITE_INTEGER:
              columnValue = "" + st.columnInt(i);
              break;
            case SQLiteConstants.SQLITE_TEXT:
              columnValue = (String)st.columnString(i);
              break;
          }

          if(columnValue == null) {
            columnValue = (String)st.columnValue(i);
          }

          row.put(columnName, columnValue);
        }

        return row;
      }

      @Override
      protected ConstructData job(SQLiteConnection connection)
        throws SQLiteException {
        SQLiteStatement st = connection.prepare(
          "SELECT parts.* FROM parts LEFT JOIN constructs ON parts.construct_id = constructs.id " +
          "WHERE constructs.name = ?"
        );
        st.bind(1, constructName);

        ConstructData results = new ConstructData();
        results.name = constructName;
        results.parts = new DataResults();

        try {
          while(st.step()) {
            results.parts.add(getRow(st));
          }

          return results;
        } finally {
          st.dispose();
        }
      }

    }).complete();
  }
}
