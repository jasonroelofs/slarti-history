package org.slartibartfast;

import org.slartibartfast.events.InputSystem;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import static org.mockito.Mockito.*;

public class Factories {

  public static SceneGraph createSceneGraph() {
    return new SceneGraph(new Node("root"));
  }

  public static Actor createActor() {
    return createSceneGraph().createActor();
  }

  public static Actor createActor(Vector3f position) {
    return createSceneGraph().createActor(position);
  }

  static InputSystem createInputSystem() {
    return new InputSystem(mock(InputManager.class));
  }

}
