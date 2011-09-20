package org.slartibartfast.dataStores;

import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;

public class DataResults extends ArrayList<HashMap<String, Object>> {

  public static Vector3f parseVector(Object object) {
    if(object instanceof String) {
      return parseVector((String) object);
    } else {
      throw new UnsupportedOperationException("Unknown type to try to parse: " +
              object.getClass().getCanonicalName());
    }
  }

  public static Vector3f parseVector(String s) {
    int x, y, z;
    String[] parts = s.substring(3).split(",");

    x = Integer.parseInt(parts[0]);
    y = Integer.parseInt(parts[1]);
    z = Integer.parseInt(parts[2]);

    return new Vector3f(x, y, z);
  }
}
