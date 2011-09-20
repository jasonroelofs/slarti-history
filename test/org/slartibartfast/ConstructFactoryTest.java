package org.slartibartfast;

import com.jme3.material.Material;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import java.util.HashMap;
import com.jme3.scene.Node;
import org.junit.Test;
import org.slartibartfast.dataStores.DataResults;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConstructFactoryTest {

  public ConstructFactoryTest() {
  }

  private HashMap<String, Object> buildSection(String fromPoint, String toPoint, String material) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("start_point", fromPoint);
    map.put("end_point", toPoint);
    map.put("material", material);
    return map;
  }

  @Test
  public void someTest() {
    
  }

//  @Test
//  public void buildsAndReturnsConstructOnName() {
//    ConstructDataProvider data = mock(ConstructDataProvider.class);
//    Node node = new Node("root");
//    AssetManager manager = mock(AssetManager.class);
//
//    ConstructFactory factory = new ConstructFactory(data, manager);
//
//    ConstructData constructData = new ConstructData();
//    constructData.name = "construct";
//
//    DataResults sections = new DataResults();
//    sections.add(buildSection("v3:0,0,0", "v3:4,4,4", "Steel"));
//    sections.add(buildSection("v3:0,0,0", "v3:8,8,8", "Rock"));
//
//    constructData.parts = sections;
//
//    when(manager.loadMaterial(anyString())).thenReturn(new Material());
//    when(data.getConstructDataFor("construct")).thenReturn(constructData);
//
//    Construct c = factory.getConstruct("construct");
//    c.attachTo(node);
//
//    // Check that we have the right tree of nodes
//    assertNotNull(node.getChild("construct_construct"));
//    assertEquals(1, node.getChildren().size());
//    assertEquals(2, ((Node)node.getChild(0)).getChildren().size());
//
//    Node testNode = (Node) node.getChild(0);
//
//    assertEquals(new Vector3f(0,0,0), testNode.getChild(0).getLocalTranslation());
//    assertEquals(new Vector3f(0,0,0), testNode.getChild(1).getLocalTranslation());
//
//    // TODO Test the size of the box?
//  }
}
