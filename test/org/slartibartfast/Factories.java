package org.slartibartfast;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

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

}
