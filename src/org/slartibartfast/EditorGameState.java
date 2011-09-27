package org.slartibartfast;

import com.jme3.math.Vector3f;
import org.slartibartfast.behaviors.FollowingBehavior;
import org.slartibartfast.behaviors.InputBehavior;
import org.slartibartfast.behaviors.TransformBehavior;
import org.slartibartfast.events.Events;
import org.slartibartfast.events.IInputListener;
import org.slartibartfast.events.InputEvent;
import org.slartibartfast.events.InputSystem;
import org.slartibartfast.events.UserKeyMapping;
import org.slartibartfast.events.UserMouseMapping;

/**
 * This app state manages the Editor interface and underlying
 * Construct editing systems. The app moves into this state when the
 * player has chosen to edit a given construct or blueprint.
 */
public class EditorGameState implements IInputListener {

  private boolean enabled;
  private InputSystem inputSystem;
  private SceneGraph sceneGraph;
  private Actor player;
  private Actor camera;
  private UserSettings userSettings;

  private ConstructEditor constructEditor;
  private final UserKeyMapping keyMapping;
  private final UserMouseMapping mouseMapping;

  // The following are behaviors removed when editing begins
  // so they can be re-added on editor close
  private FollowingBehavior oldFollow;
  private InputBehavior playerInput;

  private Actor editorActor;

  private UserKeyMapping fpsKeyMapping;
  private UserMouseMapping fpsMouseMapping;
  private boolean mouseHeld;
  private TransformBehavior editorTransform;


  public EditorGameState(InputSystem input, SceneGraph scene, Actor player, Actor camera, UserSettings userSettings) {
    enabled = false;
    inputSystem = input;
    sceneGraph = scene;

    this.player = player;
    this.camera = camera;
    this.userSettings = userSettings;

    // Pre-pull all key and mouse bindings for the Editor and save
    keyMapping = userSettings.getKeyMap("editor");
    mouseMapping = userSettings.getMouseMap("editor");

    // Camera movement mappings for the Editor
    fpsKeyMapping = userSettings.getKeyMap("fpsMovement");
    fpsMouseMapping = userSettings.getMouseMap("fpsMovement");

    // Actor that represents the user in Editing mode
    editorActor = sceneGraph.createActor();
    editorTransform = editorActor.getBehavior(TransformBehavior.class);

    mouseHeld = false;
  }

  public boolean isEditing() {
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
  public void startEditing() {
    constructEditor = new ConstructEditor();

    inputSystem.showMouseCursor();
    inputSystem.registerInputListener(
            constructEditor, keyMapping, mouseMapping);

    inputSystem.registerInputListener(this, fpsKeyMapping, fpsMouseMapping);

    /**
     * Need's the Player and Camera actors
     * This method needs to take a Construct
     *  - Construct needs pointer to Node tree
     *  - Part needs pointer to Geometry node
     * Tell camera to stop following player
     * Tell camera to move to position to view construct
     * Hook up new mappings based on some new editor scope
     *  - These mappings should be built on creation
     *
     * Go go go!
     *
     * Get ConstructEditor working w/ construct passed in
     * ConstructEditor is the input listener?
     */

    // Disconenct camera
    oldFollow = camera.removeBehavior(FollowingBehavior.class);

    // Stop Input events on Player
    playerInput = player.removeBehavior(InputBehavior.class);

    // Set the editor at the player's location and tell the camera to follow it
    // instead
    TransformBehavior playerTransform =
            player.getBehavior(TransformBehavior.class);

    editorTransform.copyFrom(playerTransform);

    camera.useBehavior(new FollowingBehavior(editorActor, Vector3f.ZERO));

    enabled = true;
  }

  /**
   * Shut down for now and let the normal play mode resume
   */
  public void doneEditing() {
    inputSystem.hideMouseCursor();
    inputSystem.unregisterInputListener(constructEditor);
    inputSystem.unregisterInputListener(this);

    constructEditor.shutdown();
    constructEditor = null;

    // No longer following EditorActor
    camera.removeBehavior(FollowingBehavior.class);

    // Re-instate saved behaviors
    camera.useBehavior(oldFollow);
    player.useBehavior(playerInput);

    enabled = false;
  }

  /**
   * IInputListener hook
   * @param event
   */
  @Override
  public void handleInputEvent(InputEvent event) {
    if(event.is(Events.RotateCamera)) {
      if(event.isPress()) {
        inputSystem.hideMouseCursor();
        mouseHeld = true;
      } else if (event.isRelease()) {
        inputSystem.showMouseCursor();
        mouseHeld = false;
      }
    }

    if(!mouseHeld &&
            (event.is(Events.TurnLeft) || event.is(Events.TurnRight)
            || event.is(Events.PitchDown) || event.is(Events.PitchUp))) {
      // Do nothing, camera rotation is locked
    } else {
      // Forward off event as necessary
      Events.processEvent(editorActor, event);
    }

    /**
     * Need to handle mouse-click => ray pick to get part
     * Add selected material overlay to Part's Geometry node
     * Keep track of currently selected Part(s)
     * On mouse movement after click / hold, resize / move part
     * Make sure part's Node info and part's local info stay sync'd
     * Hook into datastore to save stuff on changes.
     *
     * Later: Undo Redo support
     *
     * Most of this should be handled by the ConstructEditor
     */
  }


}
