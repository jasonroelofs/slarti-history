package org.slartibartfast.events;

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

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (!this.getClass().equals(obj.getClass())) return false;
    AxisDefinition obj2 = (AxisDefinition) obj;

    return obj2.axis.equals(this.axis) &&
            obj2.positiveDir == this.positiveDir;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + (this.axis != null ? this.axis.hashCode() : 0);
    hash = 97 * hash + (this.positiveDir ? 1 : 0);
    return hash;
  }
}
