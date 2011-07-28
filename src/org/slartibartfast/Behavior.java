/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.slartibartfast;

/**
 *
 * @author roelofs
 */
public abstract class Behavior {

  protected boolean initialized = false;

  /**
   * Per-frame call on every Behavior in the system.
   * This method is where all update logic should be put.
   *
   * By default this method does nothing
   * 
   * @param delta Time since the last frame
   */
  public void perform(float delta) { }

  /**
   * Check to see if this Behavior has been initialized.
   */
  public boolean isInitialized() {
    return initialized;
  }
}
