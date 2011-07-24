package org.slartibartfast;

/**
 *
 * @author roelofs
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
    this.value = 0.0f;
  }

  public InputEvent(String name, float value) {
    this.event = name;
    this.value = value;
    this.pressed = false;
  }

}
