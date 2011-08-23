package org.slartibartfast;

import org.slartibartfast.behaviors.InputBehavior;
import org.slartibartfast.behaviors.PointLightBehavior;
import org.slartibartfast.behaviors.VisualBehavior;
import org.slartibartfast.behaviors.DirectionalLightBehavior;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import org.slartibartfast.behaviors.ConstructBehavior;
import org.slartibartfast.dataProviders.IDataProvider;
import org.slartibartfast.dataProviders.SQLiteDataProvider;

/**
 * The central runner. This class sets everything up
 * and then gives control to JME.
 */
public class App extends SimpleApplication {

  private SceneGraph sceneManager;
  private InputSystem inputSystem;
  private BehaviorController behaviorController;
  private IDataProvider dataProvider;

  @Override
  public void simpleInitApp() {
    sceneManager = new SceneGraph(getRootNode());
    sceneManager.setAssetManager(getAssetManager());

    inputSystem = new InputSystem(getInputManager());

    dataProvider = new SQLiteDataProvider();
    UserSettings userSettings = new UserSettings(dataProvider);

    // TODO This is starting to feel verbose
    behaviorController = new BehaviorController();
    behaviorController.setAssetManager(assetManager);
    behaviorController.setInputSystem(inputSystem);
    behaviorController.setUserSettings(userSettings);
    behaviorController.setDataProvider(dataProvider);
    behaviorController.setConstructFactory(new ConstructFactory(new ConstructDataProvider()));

    sceneManager.setBehaviorController(behaviorController);

    /**
     * Load up station definition from sqlite
     */
    Actor station = sceneManager.createActor();
    station.useBehavior(new ConstructBehavior("defaultStation"));

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

    Actor teapot = createTeapot(new Vector3f(0.0f, 0.0f, -1.0f));
    createTeapot(new Vector3f(-1.0f, 0.0f, -1.0f));
    createTeapot(new Vector3f(1.0f, 0.0f, -1.0f));
    createTeapot(new Vector3f(0.0f, -1.0f, -1.0f));
    Actor teapot2 = createTeapot(new Vector3f(0.0f, 1.0f, -1.0f));

    teapot.useBehavior(new InputBehavior("lightMover"));
    teapot2.useBehavior(new InputBehavior("lightMover"));


    Actor sun = sceneManager.createActor();
    sun.useBehavior(new DirectionalLightBehavior(
           new Vector3f(-0.1f, -0.7f, -1.0f)));

    Actor green = sceneManager.createActor(new Vector3f(3.0f, 0.0f, 2.0f));
    green.useBehavior(new PointLightBehavior(6.0f, ColorRGBA.Green));

    Actor blue = sceneManager.createActor(new Vector3f(0.0f, 0.0f, 2.0f));
    blue.useBehavior(new PointLightBehavior(6.0f, ColorRGBA.Blue));

    Actor red = sceneManager.createActor(new Vector3f(-3.0f, 0.0f, 2.0f));
    red.useBehavior(new PointLightBehavior(6.0f, ColorRGBA.Red));

    System.out.println("Camera is currently at " +
            getCamera().getLocation());
    System.out.println("Camera is pointing at " +
            getCamera().getDirection());
  }

  private Actor createTeapot(Vector3f position) {
    Actor teapot = sceneManager.createActor(position);
    teapot.useBehavior(new VisualBehavior(
            "Models/Teapot/Teapot.obj",
            "Materials/RockyTeapot.j3m"));

    return teapot;
  }

  @Override
  public void simpleUpdate(float delta) {
    inputSystem.update(delta);
    sceneManager.update(delta);
  }

  @Override
  public void stop(boolean waitFor) {
    dataProvider.shutdown();
    super.stop(waitFor);
  }

  public static void main(String[] args) {
    App app = new App();
    app.start();
  }


}
