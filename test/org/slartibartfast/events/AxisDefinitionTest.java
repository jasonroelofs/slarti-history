package org.slartibartfast.events;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AxisDefinitionTest {

  public AxisDefinitionTest() {
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Test
  public void testEquals() {
    assertEquals(
            new AxisDefinition("axis", true),
            new AxisDefinition("axis", true));
    assertNotSame(new AxisDefinition("axis", true), null);
    assertNotSame(new AxisDefinition("axis", true), "Some String");
  }

  @Test
  public void testHashCode() {
    AxisDefinition a1 = new AxisDefinition("axis", true);
    AxisDefinition a2 = new AxisDefinition("rockin", false);

    assertNotSame(a1.hashCode(), a2.hashCode());
  }
}
