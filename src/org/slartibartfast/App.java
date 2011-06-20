package org.slartibartfast;

import org.slartibartfast.assets.loaders.PrefabLoader;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Box;
import jme3tools.optimize.GeometryBatchFactory;
import org.slartibartfast.prefabs.Hallway;
import org.slartibartfast.prefabs.Panel;

/**
 *
 * @author roelofs
 */
public class App extends SimpleApplication {

  @Override
  public void simpleInitApp() {
    
//    initResourceManagement();
//    
//    Hallway hallway = (Hallway)assetManager.loadAsset("Prefabs/hallway.yml");
//    System.out.println("Hallway found: " + hallway.toString());
//    
//    
//    Vector3f hallwayPosition = new Vector3f(0, 0, 0);
//    Node hallwayNode = new Node("hallway");
//    
//    for(Panel panel : hallway.getPanels()) {
//      buildBoxAt(hallwayNode, hallwayPosition.add(panel.getPosition()));
//    }
//    
//    Node optimized = GeometryBatchFactory.optimize(hallwayNode);
//    
//    DirectionalLight sun = new DirectionalLight();
//    sun.setDirection(new Vector3f(1, 0, -2).normalizeLocal());
//    sun.setColor(ColorRGBA.White);
//    
//    AmbientLight ambient = new AmbientLight();
//    ambient.setColor(ColorRGBA.Gray);
//    
//    getRootNode().addLight(sun);
//    getRootNode().addLight(ambient);
//    getRootNode().attachChild(optimized);
    
    getCamera().setLocation(Vector3f.ZERO);
    
    float y = -2.0f;
    
    Node station = new Node("station");
    
    // Build Floor
    for(float x = -5.0f; x <= 5.0f; x += 1.0f) {
      for(float z = -5.0f; z <= 5.0f; z += 1.0f) {
        buildBoxAt(station, new Vector3f(x, y, z));
      }
    }
    
    // And ceiling
    y = 2.0f;
    for(float x = -5.0f; x <= 5.0f; x += 1.0f) {
      for(float z = -5.0f; z <= 5.0f; z += 1.0f) {
        buildBoxAt(station, new Vector3f(x, y, z));
      }
    }
    
    // 4 walls
    
    for(y = -1.0f; y <= 1.0f; y += 1.0f) {
      for(float x = -5.0f; x <= 5.0f; x += 1.0f) {
        buildBoxAt(station, new Vector3f(x, y, -6.0f));
      }
    }
    
    for(y = -1.0f; y <= 1.0f; y += 1.0f) {
      for(float x = -5.0f; x <= 5.0f; x += 1.0f) {
        buildBoxAt(station, new Vector3f(x, y, 6.0f));
      }
    }
    
   for(y = -1.0f; y <= 1.0f; y += 1.0f) {
      for(float z = -5.0f; z <= 5.0f; z += 1.0f) {
        buildBoxAt(station, new Vector3f(6.0f, y, z));
      }
    }
   
   for(y = -1.0f; y <= 1.0f; y += 1.0f) {
      for(float z = -5.0f; z <= 5.0f; z += 1.0f) {
        buildBoxAt(station, new Vector3f(-6.0f, y, z));
      }
    }

   
    Node optimized = GeometryBatchFactory.optimize(station);
    getRootNode().attachChild(optimized);
  }
  
  private void buildBoxAt(Node node, Vector3f position) {
    Box box = new Box(position, 0.5f, 0.5f, 0.5f);
    Geometry boxGeo = new Geometry("wall", box);
    Material boxMat = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
    boxMat.setColor("Color", ColorRGBA.Blue);
    boxMat.getAdditionalRenderState().setWireframe(true);
    boxGeo.setMaterial(boxMat);
    //boxGeo.setLocalTranslation(position);
    
    node.attachChild(boxGeo);
  }

  private void initResourceManagement() {
    getAssetManager().registerLoader(PrefabLoader.class.getName(), "yaml", "yml");
  }  
  
  public static void main(String[] args) {
    App app = new App();
    app.start();
  }

}
