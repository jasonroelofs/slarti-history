package org.slartibartfast.behaviors;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import org.slartibartfast.Actor;
import org.slartibartfast.Behavior;

/**
 * This behavior deals with the visual representation of the Actor. Use
 * this behavior to add a modelPath and material.
 */
public class VisualBehavior extends Behavior {
  private String modelPath;
  private String materialPath;

  public VisualBehavior(String modelPath, String materialPath) {
    this.modelPath = modelPath;
    this.materialPath = materialPath;
  }

  public void initialize(Actor actor, AssetManager assetManager) {
    Spatial newSpatial = assetManager.loadModel(modelPath);
    Material mat = new Material(assetManager, materialPath);
    newSpatial.setMaterial(mat);

    actor.get(Node.class, "node").attachChild(newSpatial);

    initialized = true;
  }
}
