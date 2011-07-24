package org.slartibartfast;

import com.jme3.app.Application;

/**
 *
 * @author roelofs
 */
public class App extends Application {

  private ActorManager actorManager;
  private InputSystem inputSystem;

  @Override
  public void initialize() {
    super.initialize();

    actorManager = new ActorManager();
    inputSystem = new InputSystem(getInputManager());
    inputSystem.setInputReceiver(actorManager);

    // Init world / resources
    // Init Camera
    // Camera cam = new Camera(getCamera());
    //Actor camera = ActorManager.create();
    //camera.useBehavior(Camera);

    /**
     *  Init the player.
     * - Hook up to the camera
     * - Hook up to input
     * - Something ensures orientation is sync'd to camera
     * - Set starting location and orientation
     */
    Actor player = ActorManager.create();

    initInput();
  }

  @Override
  public void update() {
    super.update();
  }

  private void initInput() {


  }



  public static void main(String[] args) {
    App app = new App();
    app.start();
  }

}
