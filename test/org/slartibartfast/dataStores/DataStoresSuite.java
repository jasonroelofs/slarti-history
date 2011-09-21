package org.slartibartfast.dataStores;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  ConstructDataStoreTest.class,
  DataResultsTest.class,
  DataStoreManagerTest.class,
  UserSettingsDataStoreTest.class
})
public class DataStoresSuite {

}
