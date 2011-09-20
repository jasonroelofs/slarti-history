package org.slartibartfast.dataStores;

import org.slartibartfast.dataSources.SQLiteDataSource;
import org.slartibartfast.Construct;
import org.slartibartfast.UserSettings;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class DataStoreManagerTest {

  private DataStoreManager manager;

  public DataStoreManagerTest() {
    manager = new DataStoreManager();
  }

  @Test
  public void canBeConstructed() {
    assertNotNull(manager);
  }

  @Test
  public void canGetUserSettingsDataStore() {
    IDataStore store = manager.getDataStoreFor(UserSettings.class);
    assertNotNull(store);
    assertThat(store, is(UserSettingsDataStore.class));

    assertThat(store.getDataSource(), is(SQLiteDataSource.class));
  }

  @Test
  public void canGetConstructDataStore() {
    IDataStore store = manager.getDataStoreFor(Construct.class);
    assertNotNull(store);
    assertThat(store, is(ConstructDataStore.class));

    assertThat(store.getDataSource(), is(SQLiteDataSource.class));
  }

  @Test
  public void askingForUnknownClassReturnsNull() {
    IDataStore store = manager.getDataStoreFor(String.class);
    assertNull(store);
  }
}
