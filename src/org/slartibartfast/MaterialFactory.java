package org.slartibartfast;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;

/**
 * This factory class is a singleton that deals with any communication
 * with the JME subsystem for loading any Material-related asset.
 */
public class MaterialFactory {

  private final AssetManager assetManager;

  private static MaterialFactory instance;

  @SuppressWarnings("LeakingThisInConstructor")
  public MaterialFactory(AssetManager assetManager) {
    this.assetManager = assetManager;
    instance = this;
  }

  public static MaterialFactory get() {
    return instance;
  }

  // For ease of testing mainly
  public static void set(MaterialFactory factory) {
    instance = factory;
  }

  /**
   * Load a material from the given path and return a Material
   */
  public Material load(String path) {
    return assetManager.loadMaterial(path);
  }


}
