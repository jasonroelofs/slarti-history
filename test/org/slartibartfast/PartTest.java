package org.slartibartfast;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import org.junit.Test;
import static org.junit.Assert.*;

public class PartTest {

  public PartTest() {
  }

  @Test
  public void constructableWithStartEndAndMaterial() {
    Part part = new Part(Vector3f.ZERO, Vector3f.UNIT_XYZ, "Material");
    assertNotNull(part);

    assertEquals(Vector3f.ZERO, part.getStartPoint());
    assertEquals(Vector3f.UNIT_XYZ, part.getEndPoint());
    assertEquals("Material", part.getMaterial());
  }

  @Test
  public void canBeGivenAGeometry() {
    Part part = new Part(Vector3f.ZERO, Vector3f.ZERO, null);
    Geometry geo = new Geometry();
    part.setGeometry(geo);

    assertEquals(geo, part.getGeometry());
  }

  @Test
  public void canBeSelected() {

  }

  @Test
  public void canBeDeselected() {
  }
}
