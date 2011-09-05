package org.slartibartfast.behaviors;

import com.jme3.renderer.Camera;
import org.slartibartfast.Behavior;

public class CameraBehavior extends Behavior {

  /**
   * The JME camera this behavior is using
   */
  private final Camera camera;

  public CameraBehavior(Camera camera) {
    this.camera = camera;
  }

  /**
   * @return the camera
   */
  public Camera getCamera() {
    return camera;
  }

  /**
   * Set a new FOV on this camera
   * @param fov New Field of View in Degrees
   */
  public void setFOV(int degrees) {
    camera.setFrustumPerspective(degrees,
            camera.getHeight() / camera.getWidth(),
            1.0f, 10000.0f);
  }

  @Override
  public void perform(float delta) {

  }

  public void initialize() {
    super.initialize();
  }


}
