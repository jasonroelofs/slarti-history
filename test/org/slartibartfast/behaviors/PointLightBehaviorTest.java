package org.slartibartfast.behaviors;

import com.jme3.light.PointLight;
import com.jme3.light.Light;
import com.jme3.scene.Node;
import org.slartibartfast.Actor;
import org.junit.Test;
import static org.junit.Assert.*;

public class PointLightBehaviorTest {

  public PointLightBehaviorTest() {
  }

  @Test
  public void isConstructedWithARadius() {
    PointLightBehavior b = new PointLightBehavior(1.5f);
  }

  @Test
  public void updatesLightPositionWithActorPosition() {
    fail("not implemented");
  }

  @Test
  public void initializeCreatesLight() {
    PointLightBehavior b = new PointLightBehavior(1.5f);

    Actor a = new Actor();
    Node node = new Node("Parent Node");
    Node child = new Node("childNode");
    node.attachChild(child);
    a.set("node", child);

    b.initialize(a);

    assertEquals(1, node.getLocalLightList().size());

    Light light = node.getLocalLightList().get(0);
    assertEquals(1.5f, ((PointLight)light).getRadius(), 0.01f);

    assertTrue(b.isInitialized());
  }
}
