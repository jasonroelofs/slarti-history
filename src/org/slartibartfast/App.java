package org.slartibartfast;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author roelofs
 */
public class App extends SimpleApplication {

  private ActorManager actorManager;
  private InputSystem inputSystem;

  @Override
  public void simpleInitApp() {
    actorManager = new ActorManager();
    inputSystem = new InputSystem(getInputManager());
    inputSystem.setInputReceiver(actorManager);

    /**
     *  Init the player.
     * - Hook up to the camera
     * - Hook up to input
     * - Something ensures orientation is sync'd to camera
     * - Set starting location and orientation
     */
    Actor player = actorManager.create();

    Actor camera = actorManager.create();

    //CameraBehavior cam = new CameraBehavior(getCamera());
    //camera.useBehavior(new CameraBehavior(getCamera()));

    /**
     * Use JME tutorial to give us something to look at
     */
    Spatial teapot = getAssetManager().loadModel(
            "Models/Teapot/Teapot.obj");
    Material mat = new Material(
            getAssetManager(),
            "Common/MatDefs/Misc/ShowNormals.j3md");
    teapot.setMaterial(mat);

    getRootNode().attachChild(teapot);

    DirectionalLight sun = new DirectionalLight();
    sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
    getRootNode().addLight(sun);

    System.out.println("Camera is currently at " + getCamera().getLocation());
    System.out.println("Camera is pointing at " + getCamera().getDirection());
    System.out.println("And the teapot is currently at " + teapot.getLocalTransform());
  }

  public static void main(String[] args) {
    App app = new App();
    app.start();
  }

}
