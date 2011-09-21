package org.slartibartfast.behaviors;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  CameraBehaviorTest.class,
  ConstructBehaviorTest.class,
  DirectionalLightBehaviorTest.class,
  FollowingBehaviorTest.class,
  InputBehaviorTest.class,
  PointLightBehaviorTest.class,
  PhysicsBehaviorTest.class,
  PlayerPhysicsBehaviorTest.class,
  TransformBehaviorTest.class,
  VisualBehaviorTest.class
})
public class BehaviorsSuite {

}
