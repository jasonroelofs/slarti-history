package org.slartibartfast.behaviors;

import com.jme3.scene.Node;
import org.slartibartfast.Construct;
import org.junit.Test;
import org.slartibartfast.Factories;
import static org.junit.Assert.*;

public class ConstructBehaviorTest {

  public ConstructBehaviorTest() {
  }

  @Test
  public void canBeConstructedWithConstruct() {
    ConstructBehavior b = new ConstructBehavior(
            new Construct("name", new Node("name")));
  }

  class TestConstruct extends Construct {
    public Node node;

    public TestConstruct(String name, Node root) {
      super(name, root);
    }

    @Override
    public void attachTo(Node node) {
      this.node = node;
    }
  }

  @Test
  public void initializesItselfWithFactory() {
    TestConstruct con = new TestConstruct("", new Node());

    ConstructBehavior b = new ConstructBehavior(con);
    b.setActor(Factories.createActor());

    b.initialize();

    assertEquals(b.getActor().getNode(), con.node);
    assertTrue(b.isInitialized());
  }
}
