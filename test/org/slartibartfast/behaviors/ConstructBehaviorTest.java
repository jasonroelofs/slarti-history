package org.slartibartfast.behaviors;

import com.jme3.scene.Node;
import org.slartibartfast.Construct;
import org.junit.Test;
import org.slartibartfast.ConstructFactory;
import org.slartibartfast.Factories;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author roelofs
 */
public class ConstructBehaviorTest {

  public ConstructBehaviorTest() {
  }

  @Test
  public void canBeConstructedWithName() {
    ConstructBehavior b = new ConstructBehavior("constructName");
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
    ConstructFactory factory = mock(ConstructFactory.class);
    ConstructBehavior b = new ConstructBehavior("constructName");
    TestConstruct con = new TestConstruct("", new Node());

    b.setActor(Factories.createActor());

    stub(factory.getConstruct("constructName")).toReturn(con);

    b.initialize(factory);

    assertEquals(b.getActor().getNode(), con.node);
  }
}
