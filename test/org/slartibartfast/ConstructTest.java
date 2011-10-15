package org.slartibartfast;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConstructTest {

  public ConstructTest() {
  }

  @Test
  public void hasAName() {
    Construct c = new Construct("nameMe");
    assertEquals("nameMe", c.getName());
  }

  @Test
  public void hasManyParts() {
    Construct c = new Construct("nameMe");
    assertEquals(0, c.getParts().size());

    Part part = new Part(Vector3f.ZERO, Vector3f.ZERO, "This");
    c.addPart(part);

    assertEquals(1, c.getParts().size());
    assertEquals(part, c.getParts().get(0));
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
}