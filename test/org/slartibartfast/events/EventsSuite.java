package org.slartibartfast.events;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  AxisDefinitionTest.class,
  AxisTest.class,
  EventsTest.class,
  InputSystemTest.class,
  ButtonsTest.class,
  UserKeyMappingTest.class,
  UserMouseMappingTest.class
})
public class EventsSuite {

}
