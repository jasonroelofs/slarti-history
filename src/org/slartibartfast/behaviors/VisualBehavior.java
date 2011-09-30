package org.slartibartfast.behaviors;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.util.TangentBinormalGenerator;
import org.slartibartfast.Actor;
import org.slartibartfast.Behavior;
import org.slartibartfast.GeometryFactory;
import org.slartibartfast.MaterialFactory;

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

  @Override
  public void initialize() {
    Geometry newSpatial = (Geometry) GeometryFactory.get().load(modelPath);
    TangentBinormalGenerator.generate(newSpatial.getMesh(), true);

    Material mat = MaterialFactory.get().load(materialPath);
    newSpatial.setMaterial(mat);

    actor.getNode().attachChild(newSpatial);

    super.initialize();
  }
}
