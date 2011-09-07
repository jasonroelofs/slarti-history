package org.slartibartfast;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  org.slartibartfast.ConstructTest.class,
  org.slartibartfast.BehaviorControllerTest.class,
  org.slartibartfast.SceneGraphTest.class,
  org.slartibartfast.ConstructFactoryTest.class,
  org.slartibartfast.UserSettingsTest.class,
  org.slartibartfast.ActorTest.class})
public class BaseSuite {

}
