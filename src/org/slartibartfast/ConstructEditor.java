package org.slartibartfast;

import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import org.slartibartfast.events.Events;
import org.slartibartfast.events.InputListener;
import org.slartibartfast.events.InputEvent;
import org.slartibartfast.events.InputSystem;

public class ConstructEditor implements InputListener {

  private final SceneGraph sceneGraph;
  private final Actor camera;
  private Material selectedMaterial;

  private Geometry selected;
  private Material oldMaterial;

  public ConstructEditor(Actor camera, SceneGraph graph) {
    this.sceneGraph = graph;
    this.camera = camera;

    selectedMaterial = MaterialFactory.get().load("Materials/Selected.j3m");
  }

  /**
   * TODO Testing this interation handling
   * @param event
   * @param inputSystem
   */
  @Override
  public void handleInputEvent(InputEvent event, InputSystem inputSystem) {
    if(event.is(Events.Select) && event.isRelease()) {
      // Do a ray cast at current mouse location to find Part
      // in question
      Geometry found = sceneGraph.getClosestNode(
              camera,
              inputSystem.getCurrentMouseCoords());

      // Do something if we find said Part
      System.out.println("ConstructEditor.inputEvent: " + event.event);
      if(found != null) {
        System.out.println("Found node? " + found.getName());
        select(found);
      } else {
        deselect();
      }
    }


    /**
     * Need to handle mouse-click => ray pick to get part
     * Add selected material overlay to Part's Geometry node
     * Keep track of currently selected Part(s)
     * On mouse movement after click / hold, resize / move part
     * Make sure part's Node info and part's local info stay sync'd
     * Hook into datastore to save stuff on changes.
     *
     * Later: Undo Redo support
     */
  }

  public void select(Geometry node) {
    deselect();
    selected = node;
    oldMaterial = selected.getMaterial();
    node.setMaterial(selectedMaterial);

    node.getMesh().scaleTextureCoordinates(new Vector2f(4, 4));
  }

  public void deselect() {
    if(selected != null) {

      selected.getMesh().scaleTextureCoordinates(new Vector2f(0.25f, 0.25f));

      selected.setMaterial(oldMaterial);
      selected = null;
      oldMaterial = null;
    }
  }

  /**
   * Give this object a chance to clean itself up before
   * editing mode is completely removed.
   * Once this method is called this object should be GC-able.
   */
  public void shutdown() {

  }

  /**
   * Responsibilities:
   *
   * Takes a construct to edit
   * Listens to input events (something higher up does this?)
   *
   * Knows what part is selected
   *  - CRUD parts
   *
   * Initiate save of changes to Construct
   *
   * Undo / Redo of these changes
   *
   * Preview-mode of construct?
   *
   * Later:
   *
   * Handling of Blueprint constructs
   */
}
