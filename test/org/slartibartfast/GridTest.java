package org.slartibartfast;

import com.jme3.math.Vector3f;
import org.junit.Test;
import org.slartibartfast.Grid;
import static org.junit.Assert.*;

public class GridTest {

  public GridTest() {
  }

  @Test
  public void canConvertGridCoordsToLocalCoords() {
    assertEquals(new Vector3f(0, 0, 0), Grid.toWorldSpace(Vector3f.ZERO));
    assertEquals(new Vector3f(1, 1, 1), Grid.toWorldSpace(new Vector3f(4, 4, 4)));
    assertEquals(new Vector3f(0.5f, 0.5f, 0.5f), Grid.toWorldSpace(new Vector3f(2, 2, 2)));
  }
}
