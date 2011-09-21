package org.slartibartfast.events;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  AxisDefinitionTest.class,
  AxisTest.class,
  EventsTest.class,
  InputSystemTest.class,
  KeysTest.class,
  UserKeyMappingTest.class,
  UserMouseMappingTest.class
})
public class EventsSuite {

}
