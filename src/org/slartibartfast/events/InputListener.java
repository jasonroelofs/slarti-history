package org.slartibartfast.events;

public interface InputListener {

  /**
   * This listener will receive events from the input system
   * one at a time through this callback.
   * @param event The event to handle
   */
  public void handleInputEvent(InputEvent event, InputSystem inputSystem);
}
