package org.slartibartfast;

import org.slartibartfast.behaviors.PlayerPhysicsBehavior;
import org.slartibartfast.events.InputSystem;
import org.slartibartfast.behaviors.ConstructBehavior;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import org.slartibartfast.behaviors.PointLightBehavior;
import com.jme3.math.Vector3f;
import org.slartibartfast.behaviors.InputBehavior;
import org.junit.Before;
import org.junit.Test;
import org.slartibartfast.behaviors.DirectionalLightBehavior;
import org.slartibartfast.behaviors.PhysicsBehavior;
import org.slartibartfast.behaviors.TransformBehavior;
import org.slartibartfast.behaviors.VisualBehavior;
import org.slartibartfast.dataProviders.IDataProvider;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BehaviorControllerTest {

  class TestBehavior extends Behavior {
    public float delta = 0.0f;

    @Override
    public void perform(float delta) {
      this.delta = delta;
    }
  }

  BehaviorController controller;

  @Before
  public void setup() {
    controller = new BehaviorController();
  }

  @Test
  public void initializesBehaviorsItReceives() {
    TransformBehavior b1 = new TransformBehavior();
    TransformBehavior b2 = new TransformBehavior();

    controller.registerBehavior(b1);
    controller.registerBehavior(b2);

    assertTrue(b1.isInitialized());
    assertTrue(b2.isInitialized());
  }

  @Test
  public void registeredBehaviorsGetUpdated() {
    TestBehavior b = new TestBehavior();
    controller.registerBehavior(b);

    controller.update(1.0f);

    assertEquals(1.0f, b.delta, 0.01f);
  }

  @Test
  public void unregisteredBehaviorsStopUpdating() {
    TestBehavior b1 = new TestBehavior();
    TestBehavior b2 = new TestBehavior();

    controller.registerBehavior(b1);
    controller.registerBehavior(b2);

    controller.update(1.0f);

    controller.unregisterBehavior(b1);

    controller.update(2.0f);

    assertEquals(1.0f, b1.delta, 0.01f);
    assertEquals(2.0f, b2.delta, 0.01f);
  }

  /**
   * Specific Behavior Initialization
   *
   * For any behavior that needs special initialization
   * add a test and appropriate handler
   */

  /**
   * Input
   */

  class TestInputBehavior extends InputBehavior {
    public InputSystem system;
    public UserSettings settings;

    public TestInputBehavior(String scope) {
      super(scope);
    }

    @Override
    public void initialize(InputSystem system, UserSettings settings) {
      this.system = system;
      this.settings = settings;
    }
  }

  @Test
  public void handlesInputBehaviors() {
    TestInputBehavior b = new TestInputBehavior("scope");
    InputSystem input = Factories.createInputSystem();
    UserSettings settings = mock(UserSettings.class);

    controller.setInputSystem(input);
    controller.setUserSettings(settings);

    controller.registerBehavior(b);

    assertEquals(input, b.system);
    assertEquals(settings, b.settings);
  }

  /**
   * Directional Light
   */

  class TestDirLightBehavior extends DirectionalLightBehavior {
    public TestDirLightBehavior(Vector3f dir) {
      super(dir);
    }

    @Override
    public void initialize() {
      initialized = true;
    }
  }

  @Test
  public void handlesDirectionalLightBehaviors() {
    TestDirLightBehavior b = new TestDirLightBehavior(Vector3f.ZERO);

    controller.registerBehavior(b);

    assertTrue(b.isInitialized());
  }

  /**
   * Point Light
   */

  class TestPointLightBehavior extends PointLightBehavior {
    public TestPointLightBehavior(float radius) {
      super(radius);
    }

    @Override
    public void initialize() {
      initialized = true;
    }
  }

  @Test
  public void handlesPointLightBehaviors() {
    TestPointLightBehavior b = new TestPointLightBehavior(1.0f);

    controller.registerBehavior(b);

    assertTrue(b.isInitialized());
  }

  /**
   * Visual
   */
  class TestVisualBehavior extends VisualBehavior {
    public AssetManager manager;

    public TestVisualBehavior(String a, String b) {
      super(a, b);
    }

    public void initialize(AssetManager manager) {
      this.manager = manager;
    }
  }

  @Test
  public void handlesVisualBehaviors() {
    TestVisualBehavior b = new TestVisualBehavior("a", "b");
    AssetManager manager = mock(AssetManager.class);

    controller.setAssetManager(manager);

    controller.registerBehavior(b);

    assertEquals(manager, b.manager);
  }

  /**
   * Constructs
   */
  class TestConstructBehavior extends ConstructBehavior {
    public ConstructFactory factory;

    public TestConstructBehavior(String constructName) {
      super(constructName);
    }

    public void initialize(ConstructFactory f) {
      this.factory = f;
    }
  }

  @Test
  public void handlesConstructBehaviors() {
    TestConstructBehavior b = new TestConstructBehavior("conName");
    ConstructFactory factory = mock(ConstructFactory.class);

    controller.setConstructFactory(factory);

    controller.registerBehavior(b);

    assertEquals(factory, b.factory);
  }

  /**
   * Physics
   */
  class TestPhysicsBehavior extends PhysicsBehavior {
    public PhysicsSpace space;

    public TestPhysicsBehavior(float mass) {
      super(mass);
    }

    @Override
    public void initialize(PhysicsSpace space) {
      this.space = space;
    }
  }

  @Test
  public void handlesPhysicsBehaviors() {
    TestPhysicsBehavior b = new TestPhysicsBehavior(3.0f);
    PhysicsSpace space = mock(PhysicsSpace.class);

    controller.setPhysicsSpace(space);

    controller.registerBehavior(b);

    assertEquals(space, b.space);
  }

    /**
   * Physics
   */
  class TestPlayerPhysicsBehavior extends PlayerPhysicsBehavior {
    public PhysicsSpace space;

    public TestPlayerPhysicsBehavior() {
      super();
    }

    @Override
    public void initialize(PhysicsSpace space) {
      this.space = space;
    }
  }

  @Test
  public void handlesPlayerPhysicsBehaviors() {
    TestPlayerPhysicsBehavior b = new TestPlayerPhysicsBehavior();
    PhysicsSpace space = mock(PhysicsSpace.class);

    controller.setPhysicsSpace(space);

    controller.registerBehavior(b);

    assertEquals(space, b.space);
  }
}