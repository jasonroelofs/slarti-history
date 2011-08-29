package org.slartibartfast;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConstructTest {

  public ConstructTest() {
  }

  @Test
  public void canAttachGeometryGraphToNode() {
    String name = "Construct name";
    Node node = new Node("Construct Root");
    Node attachTo = new Node("Attach to me");

    Construct c = new Construct(name, node);

    c.attachTo(attachTo);

    assertEquals(node, attachTo.getChild("Construct Root"));
  }

  @Test
  public void canConvertGridCoordsToLocalCoords() {
    assertEquals(new Vector3f(0, 0, 0), Construct.gridToLocal(Vector3f.ZERO));
    assertEquals(new Vector3f(1, 1, 1), Construct.gridToLocal(new Vector3f(4, 4, 4)));
    assertEquals(new Vector3f(0.5f, 0.5f, 0.5f), Construct.gridToLocal(new Vector3f(2, 2, 2)));
  }
}