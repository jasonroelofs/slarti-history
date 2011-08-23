package org.slartibartfast;

import com.jme3.math.Vector3f;
import java.util.HashMap;
import com.jme3.scene.Node;
import org.junit.Test;
import org.slartibartfast.dataProviders.DataResults;
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
  public void buildsAndReturnsConstructOnName() {
    ConstructDataProvider data = mock(ConstructDataProvider.class);
    Node node = new Node("root");

    ConstructFactory factory = new ConstructFactory(data);

    ConstructData constructData = new ConstructData();
    constructData.name = "construct";

    DataResults sections = new DataResults();
    sections.add(buildSection("v3:0,0,0", "v3:1,1,1", "Steel"));
    sections.add(buildSection("v3:2,2,2", "v3:3,3,3", "Rock"));
    sections.add(buildSection("v3:1,1,1", "v3:4,4,4", "Wool"));

    constructData.parts = sections;

    when(data.getConstructDataFor("construct")).thenReturn(constructData);

    Construct c = factory.getConstruct("construct");
    c.attachTo(node);

    // Check that we have the right tree of nodes
    assertNotNull(node.getChild("construct_construct"));
    assertEquals(1, node.getChildren().size());
    assertEquals(3, ((Node)node.getChild(0)).getChildren().size());

    Node testNode = (Node) node.getChild(0);

    assertEquals(new Vector3f(0,0,0), testNode.getChild(0).getLocalTranslation());
    assertEquals(new Vector3f(1,1,1), testNode.getChild(0).getLocalScale());

    assertEquals(new Vector3f(2,2,2), testNode.getChild(1).getLocalTranslation());
    assertEquals(new Vector3f(1,1,1), testNode.getChild(1).getLocalScale());

    assertEquals(new Vector3f(1,1,1), testNode.getChild(2).getLocalTranslation());
    assertEquals(new Vector3f(3,3,3), testNode.getChild(2).getLocalScale());

  }
}
