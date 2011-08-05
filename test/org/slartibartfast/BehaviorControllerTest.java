package org.slartibartfast;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BehaviorControllerTest {

  BehaviorController controller;

  @Before
  public void setup() {
    controller = new BehaviorController();
  }

  @Test
  public void canRegisterAndUnregisterBehaviorForInitAndUpdate() {
    Behavior b1 = mock(Behavior.class);
    Behavior b2 = mock(Behavior.class);

    controller.registerBehavior(b1);
    controller.registerBehavior(b2);

    verify(b1).initialize(anyVararg());
    verify(b2).initialize(anyVararg());

    controller.update(1.0f);

    // Remove b2
    controller.unregisterBehavior(b2);

    // Run update again, see that b2 doesn't get another update
    controller.update(1.0f);

    verify(b1, atMost(2)).perform(eq(1.0f));
    verify(b2, atMost(1)).perform(eq(1.0f));

  }

}
