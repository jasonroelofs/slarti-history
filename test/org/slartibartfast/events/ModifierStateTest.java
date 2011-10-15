package org.slartibartfast.events;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModifierStateTest {

  private ModifierState state;

  public ModifierStateTest() {
  }

  @Before
  public void setup() {
    state = new ModifierState();
  }

  @Test
  public void defaultsAllStateToFalse() {
    assertFalse(state.shift);
    assertFalse(state.ctrl);
    assertFalse(state.alt);
  }

  @Test
  public void canUpdateStateOfShiftModifier() {
    state.update(ModifierState.SHIFT, true);
    assertTrue(state.shift);
  }

  @Test
  public void canUpdateStateOfCtrlModifier() {
    state.update(ModifierState.CTRL, true);
    assertTrue(state.ctrl);
  }

  @Test
  public void canUpdateStateOfAltModifier() {
    state.update(ModifierState.ALT, true);
    assertTrue(state.alt);
  }
}
