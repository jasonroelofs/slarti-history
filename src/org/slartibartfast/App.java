package org.slartibartfast;

import org.slartibartfast.events.InputSystem;
import org.slartibartfast.behaviors.InputBehavior;
import org.slartibartfast.behaviors.DirectionalLightBehavior;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import org.slartibartfast.behaviors.CameraBehavior;
import org.slartibartfast.behaviors.ConstructBehavior;
import org.slartibartfast.behaviors.FollowingBehavior;
import org.slartibartfast.behaviors.TransformBehavior;
import org.slartibartfast.dataStores.DataStoreManager;
import org.slartibartfast.dataStores.DataRepository;

/**
 * The central runner. This class sets everything up
 * and then gives control to JME.
 *
 * TODO: Move most of this logic into a GameState
 */
public class App extends SimpleApplication {

  private SceneGraph sceneManager;
  private InputSystem inputSystem;
  private BehaviorController behaviorController;

  private DataStoreManager dataStoreManager;

  @Override
  public void simpleInitApp() {
    getFlyByCamera().setEnabled(false);

    sceneManager = new SceneGraph(getRootNode());

    inputSystem = new InputSystem(getInputManager());

    dataStoreManager = new DataStoreManager();

    DataRepository<UserSettings> settingsStore =
            dataStoreManager.getDataStoreFor(UserSettings.class);
    UserSettings userSettings = settingsStore.load();

    DataRepository<Construct> constructStore = dataStoreManager.
            getDataStoreFor(Construct.class);
    Construct defaultConstruct = constructStore.load("default");

    /**
     * Initialize the few singletons we have in the system
     * These are only singletons because they are the classes that bridge
     * into JME's systems, and as such there will only ever be one "JME"
     * running, so we only need one bridge running.
     */
    new GeometryFactory(assetManager);
    new MaterialFactory(assetManager);


    behaviorController = new BehaviorController();
    behaviorController.setInputSystem(inputSystem);
    behaviorController.setUserSettings(userSettings);

    sceneManager.setBehaviorController(behaviorController);

    /**
     * The player
     */
    Actor player = sceneManager.createActor(new Vector3f(10f, 0, 6f));
    player.useBehavior(new InputBehavior("fpsMovement"));

    TransformBehavior physB = player.getBehavior(TransformBehavior.class);
    physB.setSpeed(5);
    physB.setTurnSpeed(90);
    physB.moveRelativeToRotation(true);
    // lookAt Vector3f.ZERO does *strange* things
    physB.lookAt(new Vector3f(0, 0, -10f));

    /**
     * Our camera acts as the player's ... heart? by default
     */
    Actor camera = sceneManager.createActor();
    camera.useBehavior(new FollowingBehavior(player, Vector3f.ZERO));
    camera.useBehavior(new CameraBehavior(getCamera()));
    camera.getBehavior(CameraBehavior.class).setFOV(70);

    /**
     * Give us some light in the scene
     */
    Actor sun = sceneManager.createActor();
    sun.useBehavior(new DirectionalLightBehavior(
           new Vector3f(-0.1f, -0.7f, -1.0f)));

    Actor sun2 = sceneManager.createActor();
    sun2.useBehavior(new DirectionalLightBehavior(
           new Vector3f(0.1f, 0.7f, 1.0f)));

    /**
     * Load up station definition from sqlite
     */
    Actor station = sceneManager.createActor();
    station.useBehavior(new ConstructBehavior(defaultConstruct));
  }

  @Override
  public void simpleUpdate(float delta) {
    sceneManager.update(delta);
  }

  @Override
  public void stop(boolean waitFor) {
    dataStoreManager.shutdown();
    super.stop(waitFor);
  }

  public static void main(String[] args) {
    App app = new App();
    app.start();
  }
}
