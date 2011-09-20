package org.slartibartfast.dataSources;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteJob;
import com.almworks.sqlite4java.SQLiteQueue;
import com.almworks.sqlite4java.SQLiteStatement;
import com.jme3.system.JmeSystem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slartibartfast.dataStores.DataResults;

public class SQLiteDataSource implements IDataSource {
  private static final Logger logger =
          Logger.getLogger(SQLiteDataSource.class.getName());

  /**
   * Collection of the various queues built for the various database
   * files used in the system. Keyed by database name
   */
  private Map<String, SQLiteQueue> queueMap;

  public SQLiteDataSource() {
    queueMap = new HashMap<String, SQLiteQueue>();
  }

  @Override
  public DataResults query(
          final String database, final String query, final Object... params) {

    SQLiteQueue queue;
    try {
      queue = getQueueForDatabase(database);
    } catch(IOException ex) {
      logger.log(Level.SEVERE, "Unable to open database " + database, ex);
      return null;
    }

    return queue.execute(new SQLiteJob<DataResults>() {

      /**
       * Build up internal representation of a row into a hash map of
       * effectively <String, String>, and let the code deal with
       * the actual data
       */
      private HashMap<String, Object> getRow(SQLiteStatement st)
              throws SQLiteException {
        HashMap<String, Object> row = new HashMap<String, Object>();
        String columnName, columnValue;


        for(int i = 0; i < st.columnCount(); i++) {
          columnName = st.getColumnName(i);
          if(!columnName.equals("id")) {
            columnValue = (String)st.columnValue(i);

            row.put(columnName, columnValue);
          }
        }

        return row;
      }

      @Override
      protected DataResults job(SQLiteConnection connection)
        throws SQLiteException {

        SQLiteStatement st = connection.prepare(query);
        for(int i = 0; i < params.length; i++) {
          st.bind(i + 1, (String)params[i]);
        }

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

  private SQLiteQueue getQueueForDatabase(String database) throws IOException {
    if(!queueMap.containsKey(database)) {
      SQLiteQueue queue = new SQLiteQueue(getDbFileFor(database));
      queue.start();

      queueMap.put(database, queue);
    }

    return queueMap.get(database);
  }

  private File getDbFileFor(String database) throws IOException {
        // TODO Make this work on Windows. user.home is horri-bad broke there
    // Possible solution is System.getEnv("APPDATA"), but that's
    // only on Vista and 7 and doesn't always exist anyway
    String userHome = System.getProperty("user.home");
    File savePath = new File(userHome, ".slartibartfast");
    if(!savePath.exists()) {
      savePath.mkdir();
    }

    File dbFile = new File(savePath, database + ".sqlite");

    // If the database file doesn't exist, look to see if there's
    // one in assets to copy in as a default.
    if(!dbFile.exists()) {
      InputStream defaultDb = JmeSystem.getResourceAsStream("/databases/" + database + ".sqlite");

      // Change this when there's a case where we don't need a default
      if(defaultDb == null) {
        throw new IOException("Unable to find default for " + database + ".sqlite");
      }

      FileOutputStream fos = new FileOutputStream(dbFile);
      byte[] buffer = new byte[1024];
      int bytesRead;

      while( (bytesRead = defaultDb.read(buffer)) != -1 ) {
        fos.write(buffer, 0, bytesRead);
      }

      fos.flush();
      defaultDb.close();
    }

    return dbFile;
  }

}
