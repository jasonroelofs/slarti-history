package org.slartibartfast;

import org.slartibartfast.assets.loaders.PrefabLoader;
import com.jme3.app.SimpleApplication;
import org.slartibartfast.prefabs.Hallway;

/**
 *
 * @author roelofs
 */
public class App extends SimpleApplication {

  @Override
  public void simpleInitApp() {
    
    initResourceManagement();
    
    Hallway hallway = (Hallway)assetManager.loadAsset("Prefabs/hallway.yml");
    System.out.println("Hallway found: " + hallway.toString());
  }
  

  private void initResourceManagement() {
    getAssetManager().registerLoader(PrefabLoader.class.getName(), "yaml", "yml");
  }  
  
  public static void main(String[] args) {
    App app = new App();
    app.start();
  }

}
