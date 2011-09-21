package org.slartibartfast.behaviors;

import org.slartibartfast.Behavior;
import org.slartibartfast.Construct;
import org.slartibartfast.GeometryFactory;

/**
 * Adding this behavior to an Actor will cause the system
 * to load a construct definition in for the given name and
 * give the actor a mesh according to the data found.
 */
public class ConstructBehavior extends Behavior {

  private final Construct construct;

  public ConstructBehavior(Construct construct) {
    this.construct = construct;
  }

  public void initialize(GeometryFactory geoFac) {
    getActor().getNode().attachChild(geoFac.buildGeometryFor(construct));
    initialize();
  }

}
