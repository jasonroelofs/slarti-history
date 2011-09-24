package org.slartibartfast.events;

import org.junit.Test;
import static org.junit.Assert.*;

public class KeysTest {

  public KeysTest() {
  }

  @Test
  public void testGet() {
    assertEquals(Keys.KEY_HOME, Keys.get("HOME"));
  }
}
