package org.slartibartfast.behaviors;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.util.TangentBinormalGenerator;
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

  public void initialize(AssetManager assetManager) {
    Geometry newSpatial = (Geometry) assetManager.loadModel(modelPath);
    TangentBinormalGenerator.generate(newSpatial.getMesh(), true);

    Material mat = assetManager.loadMaterial(materialPath);
    newSpatial.setMaterial(mat);

    actor.getNode().attachChild(newSpatial);

    initialize();
  }
}
