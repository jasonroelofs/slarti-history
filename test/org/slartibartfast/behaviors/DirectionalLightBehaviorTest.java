package org.slartibartfast.behaviors;

import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.scene.Node;
import org.slartibartfast.Actor;
import com.jme3.math.Vector3f;
import org.junit.Test;
import static org.junit.Assert.*;

public class DirectionalLightBehaviorTest {

  public DirectionalLightBehaviorTest() {
  }

  @Test
  public void canBeCreatedWithADirection() {
    DirectionalLightBehavior b = new DirectionalLightBehavior(Vector3f.ZERO);
  }

  @Test
  public void initializeCreatesLight() {
    DirectionalLightBehavior b = new DirectionalLightBehavior(Vector3f.ZERO);

    Actor a = new Actor();
    Node node = new Node("Node");
    a.set("node", node);

    b.initialize(a);

    assertEquals(1, node.getLocalLightList().size());

    Light light = node.getLocalLightList().get(0);
    assertEquals(Vector3f.ZERO, ((DirectionalLight)light).getDirection());

    assertTrue(b.isInitialized());
  }
}