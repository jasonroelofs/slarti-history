package org.slartibartfast;

import java.util.HashMap;

/**
 * Map of events to axis movement, defined by the user.
 * Called Mouse as that's what it will be used with 99% of the time
 * but it will support joystick movement as well.
 */
public class UserMouseMapping extends HashMap<Events, AxisDefinition> {

  private String scope;

  public UserMouseMapping(String scope) {
    super();
    this.scope = scope;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  /**
   * Add a new mapping.
   *
   * @param event The event this mapping corresponds to
   * @param axis The axis to be listening to
   * @param positiveDir Whether this mapping links to the
   *   positive (right, up) or negative (left, down) actions of the device.
   */
  public void put(Events event, String axis, boolean positiveDir) {
    put(event, new AxisDefinition(axis, positiveDir));
  }

}
