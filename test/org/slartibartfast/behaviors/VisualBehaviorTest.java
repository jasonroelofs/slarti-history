package org.slartibartfast.behaviors;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author roelofs
 */
public class VisualBehaviorTest {

  public VisualBehaviorTest() {
  }

  @Test
  public void canBeConstructedWithModelAndMaterial() {
    VisualBehavior b = new VisualBehavior("model", "material");
  }

}
