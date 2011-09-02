package org.slartibartfast;

/**
 *
 * @author roelofs
 */
public class InputEvent {

  // Actor that should be given this input event
  public Actor actor;

  // Name of the event sent
  public String event;

  // Whether the button was pressed or not
  public boolean pressed;

  // Analog: the distance travelled, [0,1]
  public float value;

  public InputEvent(Actor actor, String name, boolean pressed) {
    this.actor = actor;
    this.event = name;
    this.pressed = pressed;
    this.value = 0.0f;
  }

  public InputEvent(Actor actor, String name, float value) {
    this.actor = actor;
    this.event = name;
    this.value = value;
    this.pressed = false;
  }

  /**
   * TODO: Find a better way of sending InputEvents
   * to the Event system.
   */
  public void process() {
    Events.processEvent(this);
  }

}
