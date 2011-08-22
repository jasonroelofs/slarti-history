package org.slartibartfast.behaviors;

import org.slartibartfast.Actor;
import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import org.junit.Test;
import org.slartibartfast.Factories;
import static org.junit.Assert.*;

public class PhysicalBehaviorTest {

  public PhysicalBehaviorTest() {
  }

  @Test
  public void hasLocation() {
    PhysicalBehavior b = new PhysicalBehavior();
    Vector3f location = Vector3f.ZERO;
    b.setLocation(location);

    assertEquals(location, b.getLocation());
  }

  @Test
  public void startsAtOrigin() {
    PhysicalBehavior b = new PhysicalBehavior();
    assertEquals(Vector3f.ZERO, b.getLocation());
  }

  @Test
  public void performUpdatesNodeLocation() {
    PhysicalBehavior b = new PhysicalBehavior();
    Vector3f location = new Vector3f(1.0f, 3.0f, 10.0f);
    b.setLocation(location);

    Actor a = Factories.createActor();
    b.setActor(a);

    b.perform(0.1f);

    assertEquals(location, a.getNode().getWorldTranslation());

    // Multiple frames don't send it careening off.
    b.perform(0.1f);
    b.perform(0.1f);

    assertEquals(location, a.getNode().getWorldTranslation());
  }
}
