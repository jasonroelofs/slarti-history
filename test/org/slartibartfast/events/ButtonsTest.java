package org.slartibartfast.events;

import org.junit.Test;
import static org.junit.Assert.*;

public class ButtonsTest {

  public ButtonsTest() {
  }

  @Test
  public void testGet() {
    assertEquals(Buttons.KEY_HOME, Buttons.get("HOME"));
  }
}
