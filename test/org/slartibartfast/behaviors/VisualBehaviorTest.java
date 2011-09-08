package org.slartibartfast.behaviors;

//import org.slartibartfast.Actor;
//import com.jme3.material.MaterialDef;
//import com.jme3.asset.AssetKey;
//import com.jme3.scene.Node;
//import com.jme3.asset.AssetManager;
import org.junit.Test;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;

public class VisualBehaviorTest {

  public VisualBehaviorTest() {
  }

  @Test
  public void canBeConstructedWithModelAndMaterial() {
    VisualBehavior b = new VisualBehavior("model", "material");
  }

  /**
   * FIXME
   * This test is getting really unweildy, with lots of mocking
   * and otherwise checking the implementation.
   *
   * Going to stop trying to test this for now.
   */
//  @Test
//  public void initializesItselfWithSpatial() {
//    VisualBehavior b = new VisualBehavior("model", "material");
//
//    AssetManager managerMock = mock(AssetManager.class);
//    Node newNode = new Node("spatial Node");
//    when(managerMock.loadModel(anyString())).thenReturn(newNode);
//    when(managerMock.loadAsset(new AssetKey("material"))).thenReturn(mock(MaterialDef.class));
//
//    Actor actor = mock(Actor.class);
//
//    b.initialize(actor, managerMock);
//
//    verify(managerMock).loadModel("model");
//    verify(managerMock).loadAsset(new AssetKey("material"));
//
//    verify(actorMock).get
//
//    assertNotNull(newNode.getParent());
//  }
}
