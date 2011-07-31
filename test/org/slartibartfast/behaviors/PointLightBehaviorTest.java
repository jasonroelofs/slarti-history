package org.slartibartfast.behaviors;

import com.jme3.math.Vector3f;
import org.slartibartfast.Factories;

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
    Actor a = Factories.createActor(new Vector3f(1.0f, 2.0f, 3.0f));

    PointLightBehavior lightB = new PointLightBehavior(1.0f);
    a.useBehavior(lightB);

    lightB.initialize(a);

    assertEquals(Vector3f.ZERO, lightB.getLight().getPosition());

    lightB.perform(a, 1.0f);

    assertEquals(new Vector3f(1.0f, 2.0f, 3.0f),
            lightB.getLight().getPosition());
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
