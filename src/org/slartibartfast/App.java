package org.slartibartfast;

import org.slartibartfast.events.InputSystem;
import org.slartibartfast.behaviors.InputBehavior;
import org.slartibartfast.behaviors.PointLightBehavior;
import org.slartibartfast.behaviors.VisualBehavior;
import org.slartibartfast.behaviors.DirectionalLightBehavior;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import org.slartibartfast.behaviors.CameraBehavior;
import org.slartibartfast.behaviors.ConstructBehavior;
import org.slartibartfast.behaviors.PhysicsBehavior;
import org.slartibartfast.behaviors.TransformBehavior;
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
  private ConstructDataProvider constructDataProvider;

  private BulletAppState bulletAppState;

  @Override
  public void simpleInitApp() {
    getFlyByCamera().setEnabled(false);

    bulletAppState = new BulletAppState();
    bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
    getStateManager().attach(bulletAppState);

    // Debug the shapes
    //bulletAppState.getPhysicsSpace().enableDebug(assetManager);

    sceneManager = new SceneGraph(getRootNode());
    sceneManager.setAssetManager(getAssetManager());

    inputSystem = new InputSystem(getInputManager());

    dataProvider = new SQLiteDataProvider();
    UserSettings userSettings = new UserSettings(dataProvider);

    constructDataProvider = new ConstructDataProvider();

    // TODO This is starting to feel verbose
    behaviorController = new BehaviorController();
    behaviorController.setAssetManager(assetManager);
    behaviorController.setInputSystem(inputSystem);
    behaviorController.setUserSettings(userSettings);
    behaviorController.setDataProvider(dataProvider);
    behaviorController.setConstructFactory(
            new ConstructFactory(
                    constructDataProvider,
                    assetManager));

    behaviorController.setPhysicsSpace(bulletAppState.getPhysicsSpace());

    sceneManager.setBehaviorController(behaviorController);

    /**
     * Load up station definition from sqlite
     */
    Actor station = sceneManager.createActor();
    station.useBehavior(new ConstructBehavior("default"));
    station.useBehavior(new PhysicsBehavior(0));

    /**
     * Setting up the camera
     *
     * Move at 5 units / second
     * Set FOV to 70 degrees
     * Hook up to FPS keybindings (flying)
     */
    Actor camera = sceneManager.createActor(new Vector3f(10f, 0, 6f));
    camera.useBehavior(new InputBehavior("fpsMovement"));

    TransformBehavior physB = camera.getBehavior(TransformBehavior.class);
    physB.setSpeed(5);
    physB.setTurnSpeed(90);
    physB.moveRelativeToRotation(true);


    CameraBehavior camB = new CameraBehavior(getCamera());
    // TODO Figure out how we can do lookAt before useBehavior is
    // called. If this line is put after lookAt() then this crashes
    // with an NPE as actor isn't set yet
    camera.useBehavior(camB);
    camB.setFOV(70);
    camB.lookAt(new Vector3f(7f, 0, -15f));


    Actor teapot = createTeapot(new Vector3f(10.0f, 0.0f, 0.0f));
    createTeapot(new Vector3f(9.0f, 0.0f, 0.0f));
    createTeapot(new Vector3f(11.0f, 0.0f, 0.0f));
    createTeapot(new Vector3f(10.0f, -1.0f, 0.0f));
    Actor teapot2 = createTeapot(new Vector3f(10.0f, 1.0f, 0.0f));

    teapot.useBehavior(new InputBehavior("lightMover"));
    teapot.getBehavior(TransformBehavior.class).setSpeed(2.0f);

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
    teapot.useBehavior(new PhysicsBehavior(1));

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
    constructDataProvider.shutdown();
    super.stop(waitFor);
  }

  public static void main(String[] args) {
    App app = new App();
    app.start();
  }
}
