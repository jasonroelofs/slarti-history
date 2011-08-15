package org.slartibartfast.dataProviders;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteJob;
import com.almworks.sqlite4java.SQLiteQueue;
import com.almworks.sqlite4java.SQLiteStatement;
import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteDataProvider implements IDataProvider {
  private static final Logger logger = Logger.getLogger(SQLiteDataProvider.class.getName());
  private SQLiteQueue queue;

  @Override
  public void open(String dbName) {
    queue = new SQLiteQueue(getDbFile(dbName));
    queue.start();
  }

  @Override
  public DataResults getAll(final String collectionName) {
    return queue.execute(new SQLiteJob<DataResults>() {

      private HashMap<String, Object> getRow(SQLiteStatement st)
              throws SQLiteException {
        HashMap<String, Object> row = new HashMap<String, Object>();
        String columnName, columnValue;


        for(int i = 0; i < st.columnCount(); i++) {
          columnName = st.getColumnName(i);
          columnValue = (String)st.columnValue(i);

          row.put(columnName, columnValue);
        }

        return row;
      }

      @Override
      protected DataResults job(SQLiteConnection connection)
        throws SQLiteException {
        SQLiteStatement st = connection.prepare(
                "SELECT * FROM " + collectionName);
        DataResults results = new DataResults();

        try {
          while(st.step()) {
            results.add(getRow(st));
          }

          return results;
        } finally {
          st.dispose();
        }
      }

    }).complete();
  }

  @Override
  public void shutdown() {
    queue.stop(true);
  }


  // TODO: Some way of getting a default mapping file in place
  // Possibly have one built and in the jar, and just write it
  // out to the place we want it to be if one isn't there.
  private File getDbFile(String dbName) {
    String userHome = System.getProperty("user.home");
    File savePath = new File(userHome, ".slartibartfast");
    if(!savePath.exists()) {
      savePath.mkdir();
    }

    return new File(savePath, dbName + ".sqlite");
  }

}
