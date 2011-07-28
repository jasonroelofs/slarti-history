package org.slartibartfast.behaviors;

import com.jme3.math.Vector3f;
import org.slartibartfast.Behavior;

/**
 * This Behavior keeps track of the actual location, orientation, and
 * scale of the Actor it's added to.
 *
 * @author roelofs
 */
public class PhysicalBehavior implements Behavior {

  private Vector3f location;

  public void setLocation(Vector3f location) {
    this.location = location;
  }

  public Vector3f getLocation() {
    return this.location;
  }

  @Override
  public void perform(float delta) {
    //throw new UnsupportedOperationException("Not supported yet.");
  }

}
