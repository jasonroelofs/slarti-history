package org.slartibartfast.behaviors;

import org.slartibartfast.GeometryFactory;
import com.jme3.scene.Node;
import org.slartibartfast.Construct;
import org.junit.Test;
import org.slartibartfast.Factories;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConstructBehaviorTest {

  public ConstructBehaviorTest() {
  }

  @Test
  public void canBeConstructedWithConstruct() {
    ConstructBehavior b = new ConstructBehavior(
            new Construct("name", new Node("name")));
  }

  @Test
  public void initializesItselfWithFactory() {
    Construct con = new Construct("name");

    ConstructBehavior b = new ConstructBehavior(con);
    b.setActor(Factories.createActor());

    GeometryFactory factory = mock(GeometryFactory.class);
    GeometryFactory.set(factory);

    when(factory.buildGeometryFor(con)).thenReturn(new Node("Parent"));

    b.initialize();

    assertNotNull(b.getActor().getNode().getChild("Parent"));
    assertTrue(b.isInitialized());
  }
}
