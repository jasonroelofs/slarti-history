package org.slartibartfast;

import org.slartibartfast.behaviors.VisualBehavior;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import org.slartibartfast.behaviors.DirectionalLightBehavior;
import org.slartibartfast.behaviors.PhysicalBehavior;

/**
 *
 * @author roelofs
 */
public class App extends SimpleApplication {

  private SceneGraph sceneManager;
  private InputSystem inputSystem;

  @Override
  public void simpleInitApp() {
    sceneManager = new SceneGraph(getRootNode());
    sceneManager.setAssetManager(getAssetManager());

    inputSystem = new InputSystem(getInputManager());
    inputSystem.setInputReceiver(sceneManager);

    /**
     *  Init the player.
     * - Hook up to the camera
     * - Hook up to input
     * - Something ensures orientation is sync'd to camera
     * - Set starting location and orientation
     */
    //Actor player = sceneManager.createActor();

    //Actor camera = sceneManager.createActor();

    //CameraBehavior cam = new CameraBehavior(getCamera());
    //cam.follow(player);
    //camera.useBehavior(cam);

    createTeapot(new Vector3f(0.0f, 0.0f, -1.0f));
    createTeapot(new Vector3f(-1.0f, 0.0f, -1.0f));
    createTeapot(new Vector3f(1.0f, 0.0f, -1.0f));
    createTeapot(new Vector3f(0.0f, -1.0f, -1.0f));
    createTeapot(new Vector3f(0.0f, 1.0f, -1.0f));

    Actor sun = sceneManager.createActor();
    sun.useBehavior(new DirectionalLightBehavior(
            new Vector3f(-0.1f, -0.7f, -1.0f)));

    System.out.println("Camera is currently at " +
            getCamera().getLocation());
    System.out.println("Camera is pointing at " +
            getCamera().getDirection());
  }

  private void createTeapot(Vector3f position) {
    Actor teapot1 = sceneManager.createActor(position);
    teapot1.useBehavior(new VisualBehavior(
            "Models/Teapot/Teapot.obj",
            "Common/MatDefs/Misc/ShowNormals.j3md"));

  }

  @Override
  public void simpleUpdate(float delta) {
    inputSystem.update(delta);
    sceneManager.update(delta);
  }

  public static void main(String[] args) {
    App app = new App();
    app.start();
  }

}
