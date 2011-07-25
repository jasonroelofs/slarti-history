package org.slartibartfast.behaviors;

import org.slartibartfast.Behavior;

/**
 * This behavior deals with the visual representation of the Actor. Use
 * this behavior to add a model and material.
 */
public class VisualBehavior implements Behavior {
  private String model;
  private String material;

  public VisualBehavior(String model, String material) {
    this.model = model;
    this.material = material;
  }

  @Override
  public void perform(float delta) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
