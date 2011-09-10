package org.slartibartfast.behaviors;

import com.jme3.renderer.Camera;
import com.jme3.scene.control.CameraControl;
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
            camera.getWidth() / camera.getHeight(),
            0.1f, 10000.0f);
  }

  /**
   * Set up a CameraControl on this node to copy spatial
   * information from this node back up to the camera on each
   * frame
   */
  @Override
  public void initialize() {
    getActor().getNode().addControl(
            new CameraControl(camera,
                    CameraControl.ControlDirection.SpatialToCamera));
    super.initialize();
  }
}
