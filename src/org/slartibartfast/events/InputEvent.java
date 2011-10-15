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

  // Knows current state of modifier keys
  private final ModifierState modifiers;

  public InputEvent(String name, boolean pressed, ModifierState state) {
    this.event = name;
    this.pressed = pressed;
    this.value = pressed ? 1.0f : 0.0f;
    this.modifiers = state;
  }

  public InputEvent(String name, float value, ModifierState state) {
    this.event = name;
    this.value = value;
    this.pressed = false;
    this.modifiers = state;
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

  public boolean shift() {
    return modifiers.shift;
  }

  public boolean alt() {
    return modifiers.alt;
  }

  public boolean ctrl() {
    return modifiers.ctrl;
  }
}
