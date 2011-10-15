package org.slartibartfast;

import com.jme3.math.Vector3f;

public class Grid {

  /**
   * Convert a grid coord to JME world space coords
   * @param gridLocation Point on the grid
   * @return Vector of point scaled to world space
   */
  public static Vector3f toWorldSpace(Vector3f gridLocation) {
    return gridLocation.mult(0.25f);
  }

}
