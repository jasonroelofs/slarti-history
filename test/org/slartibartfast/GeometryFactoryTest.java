package org.slartibartfast;

import com.jme3.material.Material;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GeometryFactoryTest {

  public GeometryFactoryTest() {
  }

  @Test
  public void buildsAndReturnsNodeTreeFromConstruct() {
    AssetManager manager = mock(AssetManager.class);

    GeometryFactory factory = new GeometryFactory(manager);

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
