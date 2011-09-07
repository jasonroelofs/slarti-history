package org.slartibartfast;

import com.jme3.input.MouseInput;
import org.junit.Test;
import static org.junit.Assert.*;

public class AxisTest {

  public AxisTest() {
  }

  @Test
  public void testGet() {
    assertEquals(MouseInput.AXIS_X, Axis.get("MOUSE_X"));
  }
}
