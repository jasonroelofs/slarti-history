package org.slartibartfast;

import com.jme3.scene.Geometry;
import org.junit.Before;
import com.jme3.material.Material;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GeometryFactoryTest {
  private AssetManager manager;
  private GeometryFactory factory;

  public GeometryFactoryTest() {
  }

  @Before
  public void setup() {
    manager = mock(AssetManager.class);
    factory = new GeometryFactory(manager);
  }

  @Test
  public void isASingleton() {
    assertSame(factory, GeometryFactory.get());
  }

  @Test
  public void loadsAMeshFromTheGivenPath_returnsGeometry() {
    String path = "/path/to/model";
    Geometry geo = new Geometry("testGeo");
    when(manager.loadModel(path)).thenReturn(geo);

    assertEquals(geo, factory.load(path));
  }

  @Test
  public void buildsAndReturnsNodeTreeFromConstruct() {
    Construct construct = new Construct("construct");
    construct.addPart(
            new Part(Vector3f.ZERO, new Vector3f(4, 4, 4), "Steel"));
    construct.addPart(
            new Part(Vector3f.ZERO, new Vector3f(8, 8, 8), "Steel"));

    when(manager.loadMaterial(anyString())).thenReturn(new Material());

    Node node = factory.buildGeometryFor(construct);

    // Check that we have the right tree of nodes
    assertNotNull("construct_parent", node.getName());
    assertEquals(2, node.getChildren().size());

    assertEquals(new Vector3f(0,0,0),
            node.getChild(0).getLocalTranslation());
    assertEquals(new Vector3f(0,0,0),
            node.getChild(1).getLocalTranslation());

    // TODO Test the size of the box?
  }

}
