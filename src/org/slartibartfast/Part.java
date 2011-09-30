package org.slartibartfast;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import java.io.IOException;

/**
 * A Part is simply a single part of a Construct, whether that be a
 * wall, a floor, a decoration, anything
 *
 *
 * The Savable interface is only used because we want to make use of
 * set/getUserData on Spatial, which requires either a native type
 * or an object that implements Savable
 */
public class Part implements Savable {

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

  /**
   * Link to the specific Geometry node that visually represents
   * this Part
   */
  private Geometry geometry;

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


  /**
   * @return the geometry
   */
  public Geometry getGeometry() {
    return geometry;
  }

  /**
   * @param geometry the geometry to set
   */
  public void setGeometry(Geometry geometry) {
    this.geometry = geometry;
  }

  // Savable
  @Override
  public void write(JmeExporter ex) throws IOException {
    // do nothing
  }

  // Savable
  @Override
  public void read(JmeImporter im) throws IOException {
    // do nothing
  }


}
