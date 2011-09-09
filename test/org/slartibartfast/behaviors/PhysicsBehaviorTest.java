package org.slartibartfast.behaviors;

import com.jme3.math.Vector3f;
import org.slartibartfast.Actor;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Geometry;
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
    Actor actor = Factories.createActor(new Vector3f(1, 2, 3));
    b.setActor(actor);

    Geometry geo = mock(Geometry.class);
    actor.getNode().attachChild(geo);

    PhysicsSpace space = mock(PhysicsSpace.class);

    b.initialize(space);

    // No control on the top level node
    RigidBodyControl control = actor.getNode().
            getControl(RigidBodyControl.class);
    assertNull(control);

    // Geometry got added
    verify(geo).addControl(any(RigidBodyControl.class));

    // And the control was passed to the space
    verify(space).add(any(RigidBodyControl.class));

    // Make sure we've moved the actor to it's expected location
    // before physics took over
    assertEquals(new Vector3f(1, 2, 3),
            actor.getNode().getLocalTranslation());
  }
}
