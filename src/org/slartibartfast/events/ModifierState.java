package org.slartibartfast.events;

/**
 * Keep track of the current state of various modifier keys the user could
 * be trying to use.
 */
public class ModifierState {
  public static final String SHIFT = "SHIFT";
  public static final String CTRL = "CTRL";
  public static final String ALT = "ALT";

  public boolean shift = false;
  public boolean ctrl = false;
  public boolean alt = false;

  /**
   * Update the current state of the selected modifier.
   * @param modifier
   * @param newState
   */
  public void update(String modifier, boolean newState) {
    if(modifier.equals(SHIFT)) {
      shift = newState;
    } else if(modifier.equals(CTRL)) {
      ctrl = newState;
    } else if(modifier.equals(ALT)) {
      alt = newState;
    }
  }
}
