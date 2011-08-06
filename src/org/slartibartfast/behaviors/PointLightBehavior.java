package org.slartibartfast.behaviors;

import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

/**
 * Makes the actor act as a point light.
 */
public class PointLightBehavior extends LightBehavior {

  private float radius;
  private ColorRGBA color = ColorRGBA.White;

  private PointLight light;

  /**
   * Construct this behavior with a radius for the light influence
   * @param radius The radius of light
   */
  public PointLightBehavior(float radius) {
    this.radius = radius;
  }

  public PointLightBehavior(float radius, ColorRGBA color) {
    this(radius);
    this.color = color;
  }

  @Override
  public void perform(float delta) {
    PhysicalBehavior b = actor.getBehavior(PhysicalBehavior.class);
    light.setPosition(b.getLocation());
  }

  @Override
  public void initialize() {
    light = new PointLight();
    light.setRadius(radius);
    light.setColor(color);
    actor.get(Node.class, "node").getParent().addLight(light);

    super.initialize();
  }

  public PointLight getLight() {
    return light;
  }
}
