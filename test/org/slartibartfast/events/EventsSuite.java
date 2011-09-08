package org.slartibartfast.events;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  org.slartibartfast.events.AxisDefinitionTest.class,
  org.slartibartfast.events.UserKeyMappingTest.class,
  org.slartibartfast.events.AxisTest.class,
  org.slartibartfast.events.EventsTest.class,
  org.slartibartfast.events.InputSystemTest.class,
  org.slartibartfast.events.UserMouseMappingTest.class,
  org.slartibartfast.events.KeysTest.class})
public class EventsSuite {

}
