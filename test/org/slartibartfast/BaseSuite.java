package org.slartibartfast;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  ActorTest.class,
  BehaviorControllerTest.class,
  ConstructTest.class,
  EditorGameStateTest.class,
  GeometryFactoryTest.class,
  GridTest.class,
  MaterialFactoryTest.class,
  PartTest.class,
  SceneGraphTest.class,
  UserSettingsTest.class
})
public class BaseSuite {

}
