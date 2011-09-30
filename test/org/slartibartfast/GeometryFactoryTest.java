package org.slartibartfast;

import com.jme3.bounding.BoundingBox;
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
    Part part1 = new Part(Vector3f.ZERO, new Vector3f(4, 4, 4), "Steel");
    Part part2 = new Part(Vector3f.ZERO, new Vector3f(8, 8, 8), "Steel");

    construct.addPart(part1);
    construct.addPart(part2);


    when(manager.loadMaterial(anyString())).thenReturn(new Material());

    Node node = factory.buildGeometryFor(construct);

    // Check that we have the right tree of nodes
    assertNotNull("construct_parent", node.getName());
    assertEquals(2, node.getChildren().size());

    Geometry geo1 = (Geometry) node.getChild(0);
    Geometry geo2 = (Geometry) node.getChild(1);

    assertEquals(new Vector3f(0.5f, 0.5f, 0.5f),
            geo1.getLocalTranslation());
    assertEquals(new Vector3f(1f, 1f, 1f),
            geo2.getLocalTranslation());

    assertEquals(part1, geo1.getUserData("part"));
    assertEquals(part2, geo2.getUserData("part"));

    assertEquals(geo1, part1.getGeometry());
    assertEquals(geo2, part2.getGeometry());

    BoundingBox box;
    box = (BoundingBox) geo1.getWorldBound();
    assertEquals(0.5, box.getXExtent(), 0.001);
    assertEquals(0.5, box.getYExtent(), 0.001);
    assertEquals(0.5, box.getZExtent(), 0.001);

    box = (BoundingBox) geo2.getWorldBound();
    assertEquals(1, box.getXExtent(), 0.001);
    assertEquals(1, box.getYExtent(), 0.001);
    assertEquals(1, box.getZExtent(), 0.001);
  }

}
