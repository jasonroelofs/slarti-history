package org.slartibartfast.prefabs;

import java.util.List;
import org.slartibartfast.assets.Panel;

/**
 *
 * @author roelofs
 */
public class Hallway {
    // Dimensions in Blocks
    private int height;
    private int width;
    private int depth;
    private List<Panel> panels;

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * @return the panels
     */
    public List<Panel> getPanels() {
        return panels;
    }

    /**
     * @param panels the panels to set
     */
    public void setPanels(List<Panel> panels) {
        this.panels = panels;
    }
    
    
}
