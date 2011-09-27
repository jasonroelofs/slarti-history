package org.slartibartfast;

import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import org.slartibartfast.events.InputSystem;
import org.junit.Before;
import org.junit.Test;
import org.slartibartfast.behaviors.FollowingBehavior;
import org.slartibartfast.behaviors.InputBehavior;
import org.slartibartfast.events.UserKeyMapping;
import org.slartibartfast.events.UserMouseMapping;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EditorGameStateTest {

  private EditorGameState editorState;
  private InputSystem inputSystem;
  private SceneGraph sceneGraph;
  private Actor player;
  private Actor camera;
  private UserSettings userSettings;

  @Before
  public void setup() {
    inputSystem = mock(InputSystem.class);
    sceneGraph = new SceneGraph(new Node("root"));

    player = Factories.createActor();
    player.useBehavior(new InputBehavior("testing"));

    camera = Factories.createActor();
    camera.useBehavior(new FollowingBehavior(player, Vector3f.ZERO));

    userSettings = new UserSettings();

    editorState = new EditorGameState(
            inputSystem, sceneGraph, player, camera, userSettings);
  }

  @Test
  public void constructedWithNeededSystems() {
    assertNotNull(editorState);
    assertEquals(inputSystem, editorState.getInputSystem());
    assertEquals(sceneGraph, editorState.getSceneGraph());
  }

  @Test
  public void startsDisabled() {
    assertFalse(editorState.isEditing());
  }

  @Test
  public void canEnableAndDisableEditorMode() {
    editorState.startEditing();
    assertTrue(editorState.isEditing());

    editorState.doneEditing();
    assertFalse(editorState.isEditing());
  }

  @Test
  public void hooksIntoInputSystem() {
    editorState.startEditing();

    verify(inputSystem).showMouseCursor();
    verify(inputSystem).registerInputListener(isA(ConstructEditor.class),
            any(UserKeyMapping.class), any(UserMouseMapping.class));

    editorState.doneEditing();

    verify(inputSystem).hideMouseCursor();
    verify(inputSystem).unregisterInputListener(isA(ConstructEditor.class));
  }
}
