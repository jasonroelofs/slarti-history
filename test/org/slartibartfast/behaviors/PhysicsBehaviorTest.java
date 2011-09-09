package org.slartibartfast.behaviors;

import org.slartibartfast.Actor;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import org.junit.Test;
import org.slartibartfast.Factories;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PhysicsBehaviorTest {

  public PhysicsBehaviorTest() {
  }

  @Test
  public void createdWithAMass() {
    PhysicsBehavior b = new PhysicsBehavior(5.0f);
    assertEquals(5.0f, b.getMass(), 0.001f);
  }

  @Test
  public void initializeHooksUpPhysicsControl() {
    PhysicsBehavior b = new PhysicsBehavior(1.0f);
    Actor actor = Factories.createActor();
    b.setActor(actor);

    PhysicsSpace space = mock(PhysicsSpace.class);

    b.initialize(space);

    verify(space).add(any(RigidBodyControl.class));

    RigidBodyControl control = actor.getNode().
            getControl(RigidBodyControl.class);
    assertNotNull(control);
    assertEquals(1.0f, control.getMass(), 0.001f);
  }
}
