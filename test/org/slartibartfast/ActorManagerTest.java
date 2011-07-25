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
public class ActorManagerTest {

  ActorManager manager;

  @Before
  public void before() {
    manager = new ActorManager();
    manager.setRootNode(new Node("root"));
  }

  /**
   * .setRootNode
   */
  @Test
  public void canBeGivenARootSceneNode() {
    Node node = new Node("root");
    manager.setRootNode(node);
    assertEquals(node, manager.getRootNode());
  }

  /**
   * .create
   */

  @Test
  public void createReturnsAnActor() {
    Actor a = manager.create();
    assertNotNull(a);
  }

  @Test
  public void canCreateActorAndGiveInitialLocation() {
    Vector3f location = new Vector3f(2.0f, 3.0f, 4.0f);
    Actor a = manager.create(location);
    PhysicalBehavior b = a.getBehavior(PhysicalBehavior.class);

    assertEquals(location, b.getLocation());
  }

  @Test
  public void createAddsNodeToSceneGraph() {
    Node root = new Node("root");
    manager.setRootNode(root);

    Actor a = manager.create();
    PhysicalBehavior b = a.getBehavior(PhysicalBehavior.class);
    Node actorNode = b.getNode();

    assertEquals(root, actorNode.getParent());
  }

  @Test
  public void createAddsPhysicalToActor() {
    Actor a = manager.create();
    assertTrue(a.hasBehavior(PhysicalBehavior.class));
  }
}
