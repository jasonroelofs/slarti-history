package org.slartibartfast.behaviors;

import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import org.junit.Test;
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

}
