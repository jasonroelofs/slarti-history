package org.slartibartfast;

/**
 *
 * @author roelofs
 */
public class InputEvent {

  public String event;
  public boolean pressed;

  public InputEvent(String name, boolean pressed) {
    this.event = name;
    this.pressed = pressed;
  }

}
