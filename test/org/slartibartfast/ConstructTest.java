package org.slartibartfast;

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
}