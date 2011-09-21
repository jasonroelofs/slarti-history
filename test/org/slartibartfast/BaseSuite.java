package org.slartibartfast;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  ActorTest.class,
  BehaviorControllerTest.class,
  ConstructTest.class,
  GeometryFactoryTest.class,
  PartTest.class,
  SceneGraphTest.class,
  UserSettingsTest.class
})
public class BaseSuite {

}
