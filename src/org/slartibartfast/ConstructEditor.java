package org.slartibartfast;

import org.slartibartfast.events.IInputListener;
import org.slartibartfast.events.InputEvent;

public class ConstructEditor implements IInputListener {

  @Override
  public void handleInputEvent(InputEvent event) {
    System.out.println("ConstructEditor.inputEvent: " + event.event);
  }

  /**
   * Give this object a chance to clean itself up before
   * editing mode is completely removed.
   * Once this method is called this object should be GC-able.
   */
  public void shutdown() {

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
