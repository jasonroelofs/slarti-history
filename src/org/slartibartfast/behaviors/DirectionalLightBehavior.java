package org.slartibartfast.behaviors;

import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Any actor given this behavior gives off a uniform directional
 * in a given direction.
 *
 * Commonly used as a Sun, parallel rays with no obvious source,
 * or a source that is very far away.
 */
public class DirectionalLightBehavior extends LightBehavior {

  /**
   * The direction the light is traveling
   */
  private final Vector3f direction;

  public DirectionalLightBehavior(Vector3f direction) {
    this.direction = direction;
  }

  @Override
  public void initialize() {
    DirectionalLight light = new DirectionalLight();
    light.setDirection(direction);
    light.setColor(ColorRGBA.White);
    actor.get(Node.class, "node").getParent().addLight(light);

    super.initialize();
  }
}
