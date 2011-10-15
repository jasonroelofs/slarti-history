package org.slartibartfast;

import com.jme3.scene.Geometry;
import com.jme3.math.Vector3f;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PartManipulatorTest {

  public PartManipulatorTest() {
  }

  @Test
  public void constructable() {
    PartManipulator p = new PartManipulator();
    assertNotNull(p);
  }

  @Test
  public void canBeGivenAPartToWorkWith() {
    PartManipulator manipulator = new PartManipulator();
    MaterialFactory mFac = Factories.getMaterialFactoryMock();

    Part part = new Part(Vector3f.ZERO, Vector3f.ZERO, "material");
    Geometry geo = mock(Geometry.class);

    manipulator.select(part);

    verify(mFac.loadTexture("Textures/grid.png"));
  }
}
