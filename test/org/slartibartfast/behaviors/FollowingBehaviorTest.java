package org.slartibartfast.behaviors;

import com.jme3.math.Vector3f;
import org.slartibartfast.Actor;
import org.junit.Test;
import org.slartibartfast.Factories;
import static org.junit.Assert.*;

public class FollowingBehaviorTest {

  public FollowingBehaviorTest() {
  }

  @Test
  public void createdWithActorAndFollowOffset() {
    Actor a = new Actor();
    FollowingBehavior b = new FollowingBehavior(a, Vector3f.ZERO);

    assertEquals(a, b.getActorFollowing());
    assertEquals(Vector3f.ZERO, b.getFollowOffset());
  }

  @Test
  public void performUpdatesOurTransform() {
    Actor following = Factories.createActor();
    Actor us = Factories.createActor();

    FollowingBehavior b = new FollowingBehavior(following, Vector3f.ZERO);
    b.setActor(us);

    following.getBehavior(TransformBehavior.class).moveLeft();
    following.getBehavior(TransformBehavior.class).perform(1.0f);

    b.perform(1.0f);

    assertEquals(
            new Vector3f(-1.0f, 0, 0),
            us.getBehavior(TransformBehavior.class).getLocation());
  }

  @Test
  public void performUpdatesOurTransform_keepingDistance() {
    Actor following = Factories.createActor();
    Actor us = Factories.createActor();

    FollowingBehavior b = new FollowingBehavior(following,
            new Vector3f(0, 0, -5));

    b.setActor(us);

    following.getBehavior(TransformBehavior.class).moveLeft();
    following.getBehavior(TransformBehavior.class).perform(1.0f);

    b.perform(1.0f);

    assertEquals(
            new Vector3f(-1.0f, 0, -5),
            us.getBehavior(TransformBehavior.class).getLocation());
  }

}
