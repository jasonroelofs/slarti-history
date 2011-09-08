package org.slartibartfast.behaviors;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  org.slartibartfast.behaviors.ConstructBehaviorTest.class,
  org.slartibartfast.behaviors.DirectionalLightBehaviorTest.class,
  org.slartibartfast.behaviors.VisualBehaviorTest.class,
  org.slartibartfast.behaviors.CameraBehaviorTest.class,
  org.slartibartfast.behaviors.InputBehaviorTest.class,
  org.slartibartfast.behaviors.PhysicalBehaviorTest.class,
  org.slartibartfast.behaviors.PointLightBehaviorTest.class})
public class BehaviorsSuite {

}
