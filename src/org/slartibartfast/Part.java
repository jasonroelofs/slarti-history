package org.slartibartfast;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import java.io.IOException;
import lombok.Data;

/**
 * A Part is simply a single part of a Construct, whether that be a
 * wall, a floor, a decoration, anything
 *
 *
 * The Savable interface is only used because we want to make use of
 * set/getUserData on Spatial, which requires either a native type
 * or an object that implements Savable
 */
@Data
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
