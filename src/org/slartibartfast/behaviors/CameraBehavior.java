package org.slartibartfast.behaviors;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
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
            1.0f, 10000.0f);
  }

  /**
   * Tell the camera to look at a location in World space.
   * Because the camera is set to get it's location / rotation information
   * from this Actor's node, we look at, get the rotation, then
   * set the new rotation on this actor's node. Otherwise the rotation
   * update will be lost come next frame.
   * @param location The world location to look at
   */
  public void lookAt(Vector3f location) {
    camera.lookAt(location, Vector3f.UNIT_Y);
    actor.getBehavior(PhysicalBehavior.class).setRotation(
            camera.getRotation());
  }

  /**
   * Copy node location and rotation into camera.
   * Done directly here instead of through a CameraControl because
   * I couldn't get lookAt() working otherwise.
   * @param delta
   */
  @Override
  public void perform(float delta) {
    PhysicalBehavior phys = actor.getBehavior(PhysicalBehavior.class);
    camera.setLocation(phys.getLocation());
    camera.setRotation(phys.getRotation());
  }

}
