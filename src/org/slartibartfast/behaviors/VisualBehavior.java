package org.slartibartfast.behaviors;

import org.slartibartfast.Behavior;

/**
 * This behavior deals with the visual representation of the Actor. Use
 * this behavior to add a modelPath and material.
 */
public class VisualBehavior implements Behavior {
  private String modelPath;
  private String materialPath;

  public VisualBehavior(String modelPath, String materialPath) {
    this.modelPath = modelPath;
    this.materialPath = materialPath;
  }

  @Override
  public void perform(float delta) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
