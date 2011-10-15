package org.slartibartfast;

import java.util.LinkedList;
import java.util.List;

/**
 * This class handles editing and manipulating of parts and their Geography.
 */
public class PartManipulator {

  /**
   * List of currently selected parts
   */
  private List<Part> selected;

  public PartManipulator() {
    selected = new LinkedList<Part>();
  }

  /**
   * Select the given part
   * @param part
   */
  public void select(Part part) {
    selected.add(part);

    useSelectedMaterialOn(part);
  }

  /**
   * Deselect the given part.
   * @param part
   */
  public void deselect(Part part) {
    selected.remove(part);
  }

  /**
   * Clear all parts from the selected list
   */
  public void deselectAll() {
    for(Part p : selected) {
      removeSelectedMaterial(p);
    }

    selected.clear();
  }


  private void useSelectedMaterialOn(Part part) {
    part.getGeometry().getMaterial().setTexture(
        "Texture", MaterialFactory.get().loadTexture("Textures/grid.png"));
  }

  private void removeSelectedMaterial(Part part) {
    part.getGeometry().getMaterial().setTexture(
        "Texture", MaterialFactory.get().loadTexture("Textures/playing.png"));
  }
}
