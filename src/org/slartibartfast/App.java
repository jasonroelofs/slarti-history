package org.slartibartfast;

import org.slartibartfast.events.InputSystem;
import org.slartibartfast.behaviors.InputBehavior;
import org.slartibartfast.behaviors.DirectionalLightBehavior;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import org.slartibartfast.behaviors.CameraBehavior;
import org.slartibartfast.behaviors.ConstructBehavior;
import org.slartibartfast.behaviors.FollowingBehavior;
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

  //private GameAppState gamePlayState;
  //private EditorAppState editorState;

  @Override
  public void simpleInitApp() {
    getFlyByCamera().setEnabled(false);

    //bulletAppState = new BulletAppState();
    //bulletAppState.setThreadingType(
    //  BulletAppState.ThreadingType.PARALLEL);
    //getStateManager().attach(bulletAppState);

    // Debug the shapes
    //bulletAppState.getPhysicsSpace().enableDebug(assetManager);

    //getStateManager().attach(editorState);
    //getStateManager().attach(gamePlayState);

    sceneManager = new SceneGraph(getRootNode());

    inputSystem = new InputSystem(getInputManager());

    dataProvider = new SQLiteDataProvider();
    UserSettings userSettings = new UserSettings(dataProvider);

    constructDataProvider = new ConstructDataProvider();

    ConstructFactory constructFactory = new ConstructFactory(
                    constructDataProvider,
                    assetManager);

    // TODO This is starting to feel verbose
    behaviorController = new BehaviorController();
    behaviorController.setAssetManager(assetManager);
    behaviorController.setInputSystem(inputSystem);
    behaviorController.setUserSettings(userSettings);
    behaviorController.setDataProvider(dataProvider);

    //behaviorController.setPhysicsSpace(bulletAppState.getPhysicsSpace());

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
    Construct defaultConstruct = constructFactory.getConstruct("default");
    station.useBehavior(new ConstructBehavior(defaultConstruct));



  }

  @Override
  public void simpleUpdate(float delta) {
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
