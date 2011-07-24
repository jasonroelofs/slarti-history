package org.slartibartfast;

/**
 * Interface defines what methods an object needs to implement if they are
 * to receive input from the input system.
 * @author roelofs
 */
public interface InputReceiver {
  public void receiveInput(InputEvent[] events);
}
