package org.slartibartfast;

import com.jme3.input.MouseInput;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of all axis definitions in JME
 */
public enum Axis {

  MOUSE_X(MouseInput.AXIS_X, "MOUSE_X"),
  MOUSE_Y(MouseInput.AXIS_Y, "MOUSE_Y");

  int code;
  String name;

  Axis(int code, String name) {
    this.code = code;
    this.name = name;
  }

  private static final Map<String, Integer> lookup =
          new HashMap<String, Integer>();

  static {
    for (Axis k : EnumSet.allOf(Axis.class)) {
      lookup.put(k.name, k.code);
    }
  }

  /**
   * Given a name of the Axis, probably from the user database,
   * find out the JME MouseInput or JoyInput (not yet implemented)
   * value for that key.
   * @param keyName Name of the axis, as defined above
   * @return Value saved by the appropriate MouseInput.AXIS_
   */
  public static int get(String keyName) {
    return lookup.get(keyName);
  }

}
