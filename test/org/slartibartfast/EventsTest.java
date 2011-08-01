package org.slartibartfast;

import org.junit.Test;
import static org.junit.Assert.*;

public class EventsTest {

  public EventsTest() {
  }

  @Test
  public void canReverseLookupAnEvent() {
    assertEquals(Events.MoveUp, Events.get("MoveUp"));
  }

}
