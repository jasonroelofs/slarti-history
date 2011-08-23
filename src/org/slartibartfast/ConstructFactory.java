package org.slartibartfast;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.HashMap;
import org.slartibartfast.dataProviders.DataResults;

/**
 * This factory handles loading of all constructs needed for
 * the scope and area in which it's created.
 */
public class ConstructFactory {

  private final ConstructDataProvider dataProvider;

  public ConstructFactory(ConstructDataProvider dataProvider) {
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

    for(HashMap<String, Object> map : data.parts) {
      current = new Node(constructNode.getName() + "_section_" + sectionNum);

      startPoint = DataResults.parseVector(map.get("start_point"));
      endPoint = DataResults.parseVector(map.get("end_point"));
      material = (String)map.get("material");

      current.setLocalTranslation(startPoint);
      current.setLocalScale(endPoint.subtract(startPoint));

      // TODO Material usage here

      constructNode.attachChild(current);

      sectionNum++;
    }

    return construct;
  }
}
