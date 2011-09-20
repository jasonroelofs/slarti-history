package org;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.slartibartfast.BaseSuite;
import org.slartibartfast.behaviors.BehaviorsSuite;
import org.slartibartfast.dataProviders.DataProvidersSuite;
import org.slartibartfast.dataStores.DataStoresSuite;
import org.slartibartfast.events.EventsSuite;

/**
 * Suite that aggregates all package-specific suites
 * into one single runner.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  BaseSuite.class,
  BehaviorsSuite.class,
  DataProvidersSuite.class,
  EventsSuite.class,
  DataStoresSuite.class
})
public class AllTests {

}
