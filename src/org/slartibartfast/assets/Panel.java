package org.slartibartfast.assets;

import com.jme3.math.Vector3f;

/**
 *
 * @author roelofs
 */
public class Panel {
    private Vector3f position;
    private Vector3f rotation;
    private String type;

    /**
     * @return the position
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * @return the rotation
     */
    public Vector3f getRotation() {
        return rotation;
    }

    /**
     * @param rotation the rotation to set
     */
    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
