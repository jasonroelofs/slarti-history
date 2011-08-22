package org.slartibartfast.behaviors;

import com.jme3.scene.Geometry;
import org.slartibartfast.Behavior;
import org.slartibartfast.Construct;
import org.slartibartfast.ConstructFactory;

/**
 * Adding this behavior to an Actor will cause the system
 * to load a construct definition in for the given name and
 * give the actor a mesh according to the data found.
 */
public class ConstructBehavior extends Behavior {

  /** Name of the construct to associate */
  private final String constructName;

  public ConstructBehavior(String constructName) {
    this.constructName = constructName;
  }

  public void initialize(ConstructFactory factory) {
    Construct construct = factory.getConstruct(constructName);
    construct.attachTo(actor.getNode());

    initialize();
  }

}
