package org.slartibartfast.events;

import org.slartibartfast.events.AxisDefinition;
import org.slartibartfast.events.Events;
import org.slartibartfast.events.UserMouseMapping;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserMouseMappingTest {

  public UserMouseMappingTest() {
  }

  @Test
  public void hasScope() {
    UserMouseMapping mapping = new UserMouseMapping("scope");
    assertEquals("scope", mapping.getScope());
  }

  @Test
  public void canAddMappingShorthand() {
    UserMouseMapping mapping = new UserMouseMapping("scope");
    mapping.put(Events.MoveUp, "MOUSE_X", true);

    AxisDefinition p = mapping.get(Events.MoveUp);
    assertEquals("MOUSE_X", p.axis);
    assertEquals(true, p.positiveDir);
  }
}
