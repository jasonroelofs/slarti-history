package org.slartibartfast;

import org.slartibartfast.events.IInputListener;
import org.slartibartfast.events.InputEvent;
import org.slartibartfast.events.InputSystem;

/**
 * This app state manages the Editor interface and underlying
 * Construct editing systems. The app moves into this state when the
 * player has chosen to edit a given construct or blueprint.
 */
public class EditorGameState implements IInputListener {

  private boolean enabled;
  private final InputSystem inputSystem;
  private final SceneGraph sceneGraph;

  public EditorGameState(InputSystem input, SceneGraph scene) {
    enabled = false;
    inputSystem = input;
    sceneGraph = scene;
  }

  public boolean isEnabled() {
    return enabled;
  }

  /**
   * @return the inputSystem
   */
  public InputSystem getInputSystem() {
    return inputSystem;
  }

  /**
   * @return the sceneGraph
   */
  public SceneGraph getSceneGraph() {
    return sceneGraph;
  }

  /**
   * Inform this object to hook up what's needed to start working
   * in Editor Mode for the current Construct
   */
  public void enable() {
    inputSystem.showMouseCursor();
    inputSystem.registerInputListener(this, null, null);

    enabled = true;
  }

  /**
   * Shut down for now and let the normal play mode resume
   */
  public void disable() {
    inputSystem.hideMouseCursor();
    inputSystem.unregisterInputListener(this);

    enabled = false;
  }

  /**
   * IInputListener hook
   * @param event
   */
  @Override
  public void handleInputEvent(InputEvent event) {
    throw new UnsupportedOperationException("Not supported yet.");
  }


}
