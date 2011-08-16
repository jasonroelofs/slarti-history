package org.slartibartfast.dataProviders;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteJob;
import com.almworks.sqlite4java.SQLiteQueue;
import com.almworks.sqlite4java.SQLiteStatement;
import com.jme3.system.JmeSystem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteDataProvider implements IDataProvider {
  private static final Logger logger = Logger.getLogger(SQLiteDataProvider.class.getName());
  private SQLiteQueue queue;

  @Override
  public void open(String dbName) {
    try {
      queue = new SQLiteQueue(getDbFile(dbName));
    } catch (IOException ex) {
      logger.log(Level.SEVERE, "Unable to open database " + dbName, ex);
    }
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


  private File getDbFile(String dbName) throws IOException {
    // TODO Make this work on Windows. user.home is horri-bad broke there
    // Possible solution is System.getEnv("APPDATA"), but that's
    // only on Vista and 7 and doesn't always exist anyway
    String userHome = System.getProperty("user.home");
    File savePath = new File(userHome, ".slartibartfast");
    if(!savePath.exists()) {
      savePath.mkdir();
    }

    File dbFile = new File(savePath, dbName + ".sqlite");

    // If the database file doesn't exist, look to see if there's
    // one in assets to copy in as a default.
    if(!dbFile.exists()) {
      InputStream defaultDb = JmeSystem.getResourceAsStream("/databases/" + dbName + ".sqlite");

      // Change this when there's a case where we don't need a default
      if(defaultDb == null) {
        throw new IOException("Unable to find default for " + dbName + ".sqlite");
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
