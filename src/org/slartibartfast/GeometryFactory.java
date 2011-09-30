package org.slartibartfast;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.util.TangentBinormalGenerator;

/**
 * This factory handles loading of all constructs needed for
 * the scope and area in which it's created.
 *
 * @see Construct
 */
public class GeometryFactory {

  private AssetManager assetManager;

  private static GeometryFactory instance;

  @SuppressWarnings("LeakingThisInConstructor")
  public GeometryFactory(AssetManager manager) {
    this.assetManager = manager;
    instance = this;
  }

  public static GeometryFactory get() {
    return instance;
  }

  // For ease of testing mainly
  public static void set(GeometryFactory factory) {
    instance = factory;
  }

  /**
   * Load the requested Mesh, returning a Geometry of that mesh
   */
  public Geometry load(String path) {
    return (Geometry) assetManager.loadModel(path);
  }

  /**
   * Given a construct, build up a Node tree containing geometric
   * information for this construct.
   * @param construct The construct to build geometry data for
   * @return Node The root node of the new tree to attach to the scene
   */
  public Node buildGeometryFor(Construct construct) {
    Node parentNode = new Node(construct.getName() + "_parent");
    int sectionNum = 0;
    Vector3f startPoint, endPoint, distance, center;
    String material;
    Geometry geo;

    for(Part part : construct.getParts()) {
      startPoint = Construct.gridToLocal(part.getStartPoint());
      endPoint = Construct.gridToLocal(part.getEndPoint());
      material = part.getMaterial();

      distance = endPoint.subtract(startPoint);
      center = startPoint.add(distance.divide(2f));

      geo = new Geometry(parentNode.getName() + "_part_" + sectionNum,
              new Box(distance.x / 2, distance.y / 2, distance.z / 2));
      geo.setLocalTranslation(center);

      TangentBinormalGenerator.generate(geo.getMesh(), true);
      geo.setMaterial(assetManager.loadMaterial("Materials/RockyTeapot.j3m"));

      geo.setUserData("part", part);
      part.setGeometry(geo);

      parentNode.attachChild(geo);

      sectionNum++;
    }

    return parentNode;
  }
}
