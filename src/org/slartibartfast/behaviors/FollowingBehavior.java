package org.slartibartfast.behaviors;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import org.slartibartfast.Actor;
import org.slartibartfast.Behavior;

/**
 * This behavior links one actor to another, following
 * at a given distance away from the passed in actor.
 */
public class FollowingBehavior extends Behavior {

  /**
   * The actor we are following
   */
  private Actor following;

  /**
   * The distance from the actor's position.
   */
  private Vector3f followOffset;

  /**
   * Create a new FollowingBehavior by specifying the actor to be
   * followed and the distance this actor should follow from
   */
  public FollowingBehavior(Actor following, Vector3f followOffset) {
    this.following = following;
    this.followOffset = followOffset;
  }

  public Actor getActorFollowing() {
    return following;
  }

  public Vector3f getFollowOffset() {
    return followOffset;
  }

  @Override
  public void perform(float delta) {
    TransformBehavior ourTrans, followTrans;

    ourTrans = getActor().getBehavior(TransformBehavior.class);
    followTrans = following.getBehavior(TransformBehavior.class);

    ourTrans.setLocation(followTrans.getLocation().add(followOffset));
    ourTrans.setRotation(followTrans.getRotation());
  }
}
