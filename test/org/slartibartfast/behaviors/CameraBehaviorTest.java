package org.slartibartfast.behaviors;

import com.jme3.renderer.Camera;
import org.junit.Before;
import org.junit.Test;
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
  }

  @Test
  public void canBeConstructedWithCamera() {
    assertEquals(cameraMock, behavior.getCamera());
  }

  @Test
  public void canSetCameraFOV() {
    when(cameraMock.getHeight()).thenReturn(new Integer(1024));
    when(cameraMock.getWidth()).thenReturn(new Integer(768));

    behavior.setFOV(70);

    verify(cameraMock).setFrustumPerspective(70, 1024 / 768, 1.0f, 10000.0f);
  }

//  @Test
//  public void testPerform() {
//    fail("The test case is a prototype.");
//  }
//
//  @Test
//  public void testInitialize() {
//    fail("The test case is a prototype.");
//  }
}
