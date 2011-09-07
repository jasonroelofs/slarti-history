package org.slartibartfast;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserKeyMappingTest {

  public UserKeyMappingTest() {
  }

  @Test
  public void hasScope() {
    UserKeyMapping mapping = new UserKeyMapping("scope");
    assertEquals("scope", mapping.getScope());
  }
}
