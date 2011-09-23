package org.slartibartfast;

import org.slartibartfast.events.InputSystem;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EditorGameStateTest {

  private EditorGameState editorState;
  private InputSystem inputSystem;
  private SceneGraph sceneGraph;

  @Before
  public void setup() {
    inputSystem = mock(InputSystem.class);
    sceneGraph = mock(SceneGraph.class);

    editorState = new EditorGameState(inputSystem, sceneGraph);
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
    verify(inputSystem).registerInputListener(editorState, null, null);

    editorState.doneEditing();

    verify(inputSystem).hideMouseCursor();
    verify(inputSystem).unregisterInputListener(editorState);
  }
}
