package org.slartibartfast;

/**
 * @see UserMouseMapping
 */
public class AxisDefinition {
  public String axis;
  public boolean positiveDir;

  public AxisDefinition(String axis, boolean positiveDir) {
    this.axis = axis;
    this.positiveDir = positiveDir;
  }
}
