package org.slartibartfast;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.util.TangentBinormalGenerator;
import java.util.HashMap;
import org.slartibartfast.dataProviders.DataResults;

/**
 * This factory handles loading of all constructs needed for
 * the scope and area in which it's created.
 *
 * @see Construct
 */
public class ConstructFactory {

  private ConstructDataProvider dataProvider;
  private AssetManager assetManager;

  public ConstructFactory(ConstructDataProvider dataProvider, AssetManager manager) {
    this.assetManager = manager;
    this.dataProvider = dataProvider;
  }

  /**
   * Given a name of a construct, get a Construct with all information
   * built and ready to be added to the scene via Construct.attachTo.
   * @param name
   * @return Construct to add to the scene
   */
  public Construct getConstruct(String name) {
    return buildConstructByName(name);
  }

  private Construct buildConstructByName(String name) {
    ConstructData data = dataProvider.getConstructDataFor(name);
    Node constructNode = new Node(name + "_construct");
    Construct construct = new Construct(name, constructNode);
    Node current;
    int sectionNum = 0;
    Vector3f startPoint, endPoint;
    String material;
    Geometry geo;

    for(HashMap<String, Object> map : data.parts) {
      current = new Node(constructNode.getName() + "_section_" + sectionNum);

      startPoint = Construct.gridToLocal(
              DataResults.parseVector(map.get("start_point"))
      );
      endPoint = Construct.gridToLocal(
              DataResults.parseVector(map.get("end_point"))
      );
      material = (String)map.get("material");

      geo = new Geometry("box_" + sectionNum, new Box(startPoint, endPoint));

      //Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
      //geo.setMaterial(mat);

      //mat.getAdditionalRenderState().setWireframe(true);

      // TODO Material usage here
      TangentBinormalGenerator.generate(geo.getMesh(), true);
      geo.setMaterial(assetManager.loadMaterial("Materials/RockyTeapot.j3m"));

      current.attachChild(geo);

      System.out.println("New node loaded with name " + geo.getName());

      System.out.println("Node start point: " + startPoint);
      System.out.println("Node end point: " + endPoint);

      System.out.println("RAW Node start point: " + DataResults.parseVector(map.get("start_point")));
      System.out.println("RAW Node end point: " + DataResults.parseVector(map.get("end_point")));


      constructNode.attachChild(current);

      sectionNum++;
    }

    System.out.println("Base node is at " + constructNode.getWorldTranslation());

    return construct;
  }
}
