package org.slartibartfast;

import com.jme3.material.Material;
import org.junit.Before;
import com.jme3.asset.AssetManager;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MaterialFactoryTest {
  private AssetManager manager;
  private MaterialFactory factory;

  public MaterialFactoryTest() {
  }

  @Before
  public void setup() {
    manager = mock(AssetManager.class);
    factory = new MaterialFactory(manager);
  }

  @Test
  public void isASingleton() {
    assertSame(factory, MaterialFactory.get());
  }

  @Test
  public void loadsAMeshFromTheGivenPath_returnsMaterial() {
    String path = "/path/to/model";
    Material mat = new Material();
    when(manager.loadMaterial(path)).thenReturn(mat);

    assertEquals(mat, factory.load(path));
  }
}
