package org.slartibartfast;

import org.slartibartfast.events.IInputListener;
import org.slartibartfast.events.InputEvent;

public class ConstructEditor implements IInputListener {

  @Override
  public void handleInputEvent(InputEvent event) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * Responsibilities:
   *
   * Takes a construct to edit
   * Listens to input events (something higher up does this?)
   *
   * Knows what part is selected
   *  - CRUD parts
   *
   * Initiate save of changes to Construct
   *
   * Undo / Redo of these changes
   *
   * Preview-mode of construct?
   *
   * Later:
   *
   * Handling of Blueprint constructs
   */
}
