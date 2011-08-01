package org.slartibartfast;

import com.jme3.input.KeyInput;
import org.junit.Test;
import static org.junit.Assert.*;

public class KeysTest {

  public KeysTest() {
  }

  @Test
  public void testGet() {
    assertEquals(KeyInput.KEY_HOME, Keys.get("HOME"));
  }
}
