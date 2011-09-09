package org.slartibartfast.behaviors;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.control.CameraControl;
import com.jme3.renderer.Camera;
import org.junit.Before;
import org.junit.Test;
import org.slartibartfast.Factories;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CameraBehaviorTest {

  public CameraBehaviorTest() {
  }

  private Camera cameraMock;
  private CameraBehavior behavior;

  @Before
  public void setupCamera() {
    cameraMock = mock(Camera.class);
    behavior = new CameraBehavior(cameraMock);
    behavior.setActor(Factories.createActor());
  }

  @Test
  public void canBeConstructedWithCamera() {
    assertEquals(cameraMock, behavior.getCamera());
  }

  @Test
  public void canSetCameraFOV() {
    when(cameraMock.getHeight()).thenReturn(new Integer(768));
    when(cameraMock.getWidth()).thenReturn(new Integer(1024));

    behavior.setFOV(70);

    verify(cameraMock).setFrustumPerspective(70, 1024 / 768, 1.0f, 10000.0f);
  }

  @Test
  public void canLookAtALocation() {
    TransformBehavior physB =
            behavior.getActor().getBehavior(TransformBehavior.class);
    physB.setLocation(new Vector3f(0, 0, -10f));
    behavior.lookAt(Vector3f.ZERO);

    assertEquals(physB.getRotation(), behavior.getCamera().getRotation());
  }

}
