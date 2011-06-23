package org.slartibartfast;

import org.slartibartfast.assets.loaders.PrefabLoader;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;
import jme3tools.optimize.GeometryBatchFactory;
import org.slartibartfast.prefabs.Hallway;
import org.slartibartfast.prefabs.Panel;

/**
 *
 * @author roelofs
 */
public class App extends SimpleApplication {
  
  private PointLight light1;
  private PointLight light2;
  private PointLight light3;
  private PointLight light4;
  private PointLight light5;

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
//    y = 1.0f;
//    for(float x = -5.0f; x <= 5.0f; x += 1.0f) {
//      for(float z = -5.0f; z <= 5.0f; z += 1.0f) {
//        buildBoxAt(station, new Vector3f(x, y, z));
//      }
//    }
    
    // 4 walls
    
    for(y = -1.0f; y < 1.0f; y += 1.0f) {
      for(float x = -5.0f; x <= 5.0f; x += 1.0f) {
        buildBoxAt(station, new Vector3f(x, y, -6.0f));
      }
    }
    
    for(y = -1.0f; y < 1.0f; y += 1.0f) {
      for(float x = -5.0f; x <= 5.0f; x += 1.0f) {
        buildBoxAt(station, new Vector3f(x, y, 6.0f));
      }
    }
    
   for(y = -1.0f; y < 1.0f; y += 1.0f) {
      for(float z = -5.0f; z <= 5.0f; z += 1.0f) {
        buildBoxAt(station, new Vector3f(6.0f, y, z));
      }
    }
   
   for(y = -1.0f; y < 1.0f; y += 1.0f) {
      for(float z = -5.0f; z <= 5.0f; z += 1.0f) {
        buildBoxAt(station, new Vector3f(-6.0f, y, z));
      }
    }

   
    Node optimized = GeometryBatchFactory.optimize(station);
    TangentBinormalGenerator.generate(optimized);
    getRootNode().attachChild(optimized);
    
    // Add Lights
    light1 = addLightAt(new Vector3f(-4f, 0f, -4f));
    light2 = addLightAt(new Vector3f(4f, 0f, -4f));
    light3 = addLightAt(new Vector3f(4f, 0f, 4f));
    light4 = addLightAt(new Vector3f(-4f, 0f, 4f));
    light5 = addLightAt(Vector3f.ZERO);
  }
  
  private PointLight addLightAt(Vector3f position) {
    PointLight light;
    light = new PointLight();
    light.setPosition(position);
    light.setRadius(5.0f);
    
    Geometry lightGeo = new Geometry("sphere", new Box(position, 0.1f, 0.1f, 0.1f));
    Material geoMat = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
    lightGeo.setMaterial(geoMat);
    getRootNode().attachChild(lightGeo);
    
    getRootNode().addLight(light);
    
    return light;
  }
  
  private void buildBoxAt(Node node, Vector3f position) {
    Box box = new Box(position, 0.5f, 0.5f, 0.5f);
    Geometry boxGeo = new Geometry("wall", box);
    
    Material boxMat = new Material(getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
    //boxMat.setColor("Color", ColorRGBA.Blue);
    //boxMat.getAdditionalRenderState().setWireframe(true);
    boxMat.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Terrain/Pond/Pond.png"));
    //boxMat.setTexture("NormalMap", assetManager.loadTexture("Textures/Terrain/Pond/Pond_normal.png"));
    boxMat.setFloat("Shininess", 2f);
    boxMat.setBoolean("UseMaterialColors", true);
    boxMat.setColor("Ambient", ColorRGBA.Yellow);
    boxMat.setColor("Diffuse", ColorRGBA.Red);
    boxMat.setColor("Specular", ColorRGBA.Blue);
    
    boxGeo.setMaterial(boxMat);
    
    node.attachChild(boxGeo);
  }
  private float timeElapsed = 0.0f;
  @Override
  public void simpleUpdate(float tpf) {
    float omega = 2f * 2 * 3.1415f;
    
    float xDiff = 0.1f * FastMath.sin2(omega * timeElapsed);
    
    Vector3f oldPos = light1.getPosition().clone();
    
    System.out.println("change this frame: " + xDiff);
    System.out.println("Second counter is: " + timeElapsed);
    
    oldPos.x = oldPos.x + xDiff;
    
    light1.setPosition(oldPos);
    
    timeElapsed += tpf;
  }

  private void initResourceManagement() {
    getAssetManager().registerLoader(PrefabLoader.class.getName(), "yaml", "yml");
  }  
  
  public static void main(String[] args) {
    App app = new App();
    app.start();
  }

}
