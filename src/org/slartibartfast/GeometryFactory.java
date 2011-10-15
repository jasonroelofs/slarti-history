package org.slartibartfast;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
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
   *
   * TODO: Possibly move creation logic into Part? Or keep the JME specific
   * stuff here, haven't decided. Part does need to know how to resize itself.
   *
   * @param construct The construct to build geometry data for
   * @return Node The root node of the new tree to attach to the scene
   */
  public Node buildGeometryFor(Construct construct) {
    Node parentNode = new Node(construct.getName() + "_parent");
    int sectionNum = 0;
    Vector3f startPoint, endPoint, distance, center;
    String materialName;
    Material partMat;
    Geometry geo;

    for(Part part : construct.getParts()) {
      startPoint = Grid.toWorldSpace(part.getStartPoint());
      endPoint = Grid.toWorldSpace(part.getEndPoint());
      materialName = part.getMaterial();

      distance = endPoint.subtract(startPoint);
      center = startPoint.add(distance.divide(2f));

      geo = new Geometry(parentNode.getName() + "_part_" + sectionNum,
              new Box(distance.x / 2, distance.y / 2, distance.z / 2));
      geo.setLocalTranslation(center);

      TangentBinormalGenerator.generate(geo.getMesh(), true);
      partMat = new Material(assetManager, "Materials/Part.j3md");
      partMat.setTexture("Texture", assetManager.loadTexture("Textures/playing.png"));

      geo.setMaterial(partMat);

      geo.setUserData("part", part);
      part.setGeometry(geo);

      parentNode.attachChild(geo);

      sectionNum++;
    }

    return parentNode;
  }

  /*
   * Given a geometry node, build a selection box to wrap the
   * selected node.
   */
  public Geometry buildSelectionBoxAround(Geometry geometry) {
    BoundingBox box = (BoundingBox) geometry.getWorldBound();
    Geometry selectionGeo = new Geometry();

    float x = box.getXExtent();
    float y = box.getYExtent();
    float z = box.getZExtent();

    Mesh newMesh = geometry.getMesh().clone();

    selectionGeo.setMesh(newMesh);
    selectionGeo.setMaterial(assetManager.loadMaterial("Materials/Selected.j3m"));

    return selectionGeo;
  }
}
