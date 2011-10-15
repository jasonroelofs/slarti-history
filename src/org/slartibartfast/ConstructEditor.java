package org.slartibartfast;

import com.jme3.scene.Geometry;
import org.slartibartfast.events.Events;
import org.slartibartfast.events.InputListener;
import org.slartibartfast.events.InputEvent;
import org.slartibartfast.events.InputSystem;

public class ConstructEditor implements InputListener {

  private final SceneGraph sceneGraph;
  private final Actor camera;

  private final PartManipulator partManipulator;

  // TODO Pull camera out of scene graph instead of passing it in?
  public ConstructEditor(Actor camera, SceneGraph graph) {
    this.sceneGraph = graph;
    this.camera = camera;

    this.partManipulator = new PartManipulator();
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
  }

  public void select(Geometry node) {
    deselect();

    Part part = (Part)node.getUserData("part");
    partManipulator.select(part);
  }

  public void deselect() {
    partManipulator.deselectAll();
  }

  /**
   * Give this object a chance to clean itself up before
   * editing mode is completely removed.
   * Once this method is called this object should be GC-able.
   */
  public void shutdown() {

  }

}
