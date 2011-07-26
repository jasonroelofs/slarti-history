package org.slartibartfast;

import com.jme3.scene.Node;
import org.junit.Before;
import com.jme3.math.Vector3f;
import org.slartibartfast.behaviors.PhysicalBehavior;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author roelofs
 */
public class SceneGraphTest {

  SceneGraph graph;

  @Before
  public void before() {
    graph = new SceneGraph(new Node("root"));
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
  public void canCreateActorAndGiveInitialLocation() {
    Vector3f location = new Vector3f(2.0f, 3.0f, 4.0f);
    Actor a = graph.createActor(location);
    PhysicalBehavior b = a.getBehavior(PhysicalBehavior.class);

    assertEquals(location, b.getLocation());
  }

  @Test
  public void createAddsNodeToSceneGraph() {
    Node root = new Node("root");
    graph.setRootNode(root);

    Actor a = graph.createActor();
    PhysicalBehavior b = a.getBehavior(PhysicalBehavior.class);
    Node actorNode = b.getNode();

    assertEquals(root, actorNode.getParent());
  }

  @Test
  public void createAddsPhysicalToActor() {
    Actor a = graph.createActor();
    assertTrue(a.hasBehavior(PhysicalBehavior.class));
  }
}
