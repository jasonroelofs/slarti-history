package org.slartibartfast.behaviors;

import org.slartibartfast.Actor;
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

  @Test
  public void initializesItselfWithFactory() {
    ConstructFactory factory = mock(ConstructFactory.class);
    ConstructBehavior b = new ConstructBehavior("constructName");
    Construct con = new Construct();

    b.setActor(Factories.createActor());

    stub(factory.getConstruct("constructName")).toReturn(con);

    b.initialize(factory);

    assertEquals(1, b.getActor().getNode().getChildren().size());
  }
}
