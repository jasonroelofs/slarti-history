package org.slartibartfast;

import org.slartibartfast.behaviors.VisualBehavior;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import org.slartibartfast.behaviors.PhysicalBehavior;

/**
 *
 * @author roelofs
 */
public class App extends SimpleApplication {

  private SceneGraph actorManager;
  private InputSystem inputSystem;

  @Override
  public void simpleInitApp() {
    actorManager = new SceneGraph(getRootNode());

    inputSystem = new InputSystem(getInputManager());
    inputSystem.setInputReceiver(actorManager);

    /**
     *  Init the player.
     * - Hook up to the camera
     * - Hook up to input
     * - Something ensures orientation is sync'd to camera
     * - Set starting location and orientation
     */
    //Actor player = actorManager.createActor();

    //Actor camera = actorManager.createActor();

    //CameraBehavior cam = new CameraBehavior(getCamera());
    //cam.follow(player);
    //camera.useBehavior(cam);

    Actor teapot1 = actorManager.createActor(new Vector3f(0.0f, 0.0f, -1.0f));
    teapot1.useBehavior(new VisualBehavior(
            "Models/Teapot/Teapot.obj",
            "Common/MatDefs/Misc/ShowNormals.j3md"));

    /**
     * Use JME tutorial to give us something to look at
     */
//    Spatial teapot = getAssetManager().loadModel(
//            "Models/Teapot/Teapot.obj");
//    Material mat = new Material(
//            getAssetManager(),
//            "Common/MatDefs/Misc/ShowNormals.j3md");
//    teapot.setMaterial(mat);
//
//    getRootNode().attachChild(teapot);

    DirectionalLight sun = new DirectionalLight();
    sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
    getRootNode().addLight(sun);

    System.out.println("Camera is currently at " +
            getCamera().getLocation());
    System.out.println("Camera is pointing at " +
            getCamera().getDirection());
    System.out.println("And the teapot is currently at " +
            teapot1.getBehavior(PhysicalBehavior.class).getLocation());
  }

  public static void main(String[] args) {
    App app = new App();
    app.start();
  }

}
