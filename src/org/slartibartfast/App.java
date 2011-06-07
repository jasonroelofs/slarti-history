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
    
    initResourceManagement();
    
    Hallway hallway = (Hallway)assetManager.loadAsset("Prefabs/hallway.yml");
    System.out.println("Hallway found: " + hallway.toString());
    
    
    Vector3f hallwayPosition = new Vector3f(0, 0, 0);
    Node hallwayNode = new Node("hallway");
    
    for(Panel panel : hallway.getPanels()) {
      buildBoxAt(hallwayNode, hallwayPosition.add(panel.getPosition()));
    }
    
    Node optimized = GeometryBatchFactory.optimize(hallwayNode);
    
    DirectionalLight sun = new DirectionalLight();
    sun.setDirection(new Vector3f(1, 0, -2).normalizeLocal());
    sun.setColor(ColorRGBA.White);
    
    AmbientLight ambient = new AmbientLight();
    ambient.setColor(ColorRGBA.Gray);
    
    getRootNode().addLight(sun);
    getRootNode().addLight(ambient);
    getRootNode().attachChild(optimized);
  }
  
  private void buildBoxAt(Node node, Vector3f position) {
    Box box = new Box(position, 0.1f, 0.1f, 0.1f);
    Geometry boxGeo = new Geometry("wall", box);
    Material boxMat = new Material(getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
    //boxMat.setColor("Color", ColorRGBA.Blue);
    boxMat.setFloat("Shininess", 0.5f);
    boxGeo.setMaterial(boxMat);
    
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
