package org.slartibartfast;

import com.jme3.math.Vector3f;

/**
 * A Part is simply a single part of a Construct, whether that be a
 * wall, a floor, a decoration, anything
 * @author roelofs
 */
public class Part {

  /**
   * Local-space grid location of this part's start point, relative
   * to parent construct center
   */
  private Vector3f startPoint;

  /**
   * Local-space grid location of this part's end point, relative
   * to parent construct center
   */
  private Vector3f endPoint;

  private String material;

  public Part(Vector3f startPoint, Vector3f endPoint, String material) {
    this.startPoint = startPoint;
    this.endPoint = endPoint;
    this.material = material;
  }

  /**
   * @return the startPoint
   */
  public Vector3f getStartPoint() {
    return startPoint;
  }

  /**
   * @param startPoint the startPoint to set
   */
  public void setStartPoint(Vector3f startPoint) {
    this.startPoint = startPoint;
  }

  /**
   * @return the endPoint
   */
  public Vector3f getEndPoint() {
    return endPoint;
  }

  /**
   * @param endPoint the endPoint to set
   */
  public void setEndPoint(Vector3f endPoint) {
    this.endPoint = endPoint;
  }

  /**
   * @return the material
   */
  public String getMaterial() {
    return material;
  }

  /**
   * @param material the material to set
   */
  public void setMaterial(String material) {
    this.material = material;
  }

}
