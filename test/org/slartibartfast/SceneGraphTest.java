package org.slartibartfast;

import com.jme3.scene.Geometry;
import com.jme3.math.Ray;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import org.junit.Before;
import com.jme3.math.Vector3f;
import org.slartibartfast.behaviors.TransformBehavior;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SceneGraphTest {

  SceneGraph graph;
  BehaviorController behaviorController;

  @Before
  public void before() {
    behaviorController = mock(BehaviorController.class);
    graph = new SceneGraph(new Node("root"));
    graph.setBehaviorController(behaviorController);
  }

  /**
   * .setRootNode
   */
  @Test
  public void takesARootSceneNodeOnConstruction() {
    assertNotNull(graph.getRootNode());
  }

  /**
   * .createActor
   */

  @Test
  public void createReturnsAnActor() {
    Actor a = graph.createActor();
    assertNotNull(a);
  }

  @Test
  public void createGivesActorUniqueId() {
    Actor a1 = graph.createActor();
    Actor a2 = graph.createActor();
    Actor a3 = graph.createActor();

    assertTrue(a1.getId() != a2.getId());
    assertTrue(a2.getId() != a3.getId());
    assertTrue(a3.getId() != a1.getId());
  }

  @Test
  public void createGivesActorLinkToBehaviorController() {
    Actor a = graph.createActor();

    assertEquals(a.getBehaviorController(), behaviorController);
  }

  @Test
  public void canCreateActorAndGiveInitialLocation() {
    Vector3f location = new Vector3f(2.0f, 3.0f, 4.0f);
    Actor a = graph.createActor(location);
    TransformBehavior b = a.getBehavior(TransformBehavior.class);

    assertEquals(location, b.getLocation());
  }

  @Test
  public void createAddsNodeToSceneGraph() {
    Node root = new Node("root");
    graph.setRootNode(root);

    Actor a = graph.createActor();

    assertNotNull(a.getNode());
    assertEquals(root, a.getNode().getParent());
  }

  @Test
  public void createAddsPhysicalToActor() {
    Actor a = graph.createActor();
    assertTrue(a.hasBehavior(TransformBehavior.class));
  }

  @Test
  public void updateTriggersControllerUpdate() {
    graph.update(1.0f);

    verify(behaviorController).update(1.0f);
  }

//  @Test
//  public void canFindClosestNodeForPicking() {
//    Actor camera = Factories.createActor();
//    Vector2f pos = new Vector2f(1, 2);
//    Node root = mock(Node.class);
//    graph.setRootNode(root);
//
//    when(root.collideWith(any(Ray.class), any(CollisionResults.class))).thenReturn(1);
//
//    Geometry found = graph.getClosestNode(camera, pos);
//  }
}
