package org.slartibartfast.events;

/**
 * Internal representation of an event from the input system.
 */
public class InputEvent {

  // Name of the event sent
  public String event;

  // Whether the button was pressed or not
  public boolean pressed;

  // Analog: the distance travelled, [0,1]
  public float value;

  public InputEvent(String name, boolean pressed) {
    this.event = name;
    this.pressed = pressed;
    this.value = pressed ? 1.0f : 0.0f;
  }

  public InputEvent(String name, float value) {
    this.event = name;
    this.value = value;
    this.pressed = false;
  }

  public boolean is(Events event) {
    return Events.get(this.event) == event;
  }

  public boolean isRelease() {
    return value == 0;
  }

  public boolean isPress() {
    return value == 1;
  }

  public boolean isHold() {
    return value > 0 && value < 1;
  }

}
