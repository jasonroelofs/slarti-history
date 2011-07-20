package org.slartibartfast;

import com.jme3.app.Application;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import org.slartibartfast.behaviors.PhysicalBehavior;

/**
 *
 * @author roelofs
 */
public class App extends Application {

  private Actor player;

  @Override
  public void initialize() {
    super.initialize();
    // Init world / resources
    // Init Camera
    // Camera cam = new Camera(getCamera());
    //Actor camera = ActorFactory.create();
    //camera.useBehavior(Camera);

    /**
     *  Init the player.
     * - Hook up to the camera
     * - Hook up to input
     * - Something ensures orientation is sync'd to camera
     * - Set starting location and orientation
     */
    player = ActorFactory.create();

    initInput();
  }

  @Override
  public void update() {
    super.update();
  }

  private void initInput() {
    inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_E));
    inputManager.addMapping("Backward", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_S));
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_F));

    inputManager.addListener(actionListener, new String[]{"Forward", "Backward"});

  }

  private ActionListener actionListener = new ActionListener() {

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
      if(name.equals("Forward")) {
        //player.getBehavior(PhysicalBehavior.class).moveForward();
      }
      else if(name.equals("Backward")) {

      }
      else if(name.equals("Left")) {

      }
      else if(name.equals("Right")) {

      }
    }

  };


  public static void main(String[] args) {
    App app = new App();
    app.start();
  }

}
