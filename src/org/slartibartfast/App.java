package org.slartibartfast;

import com.jme3.app.SimpleApplication;

/**
 * 
 * @author roelofs
 */
public class App extends SimpleApplication {
  
  @Override
  public void simpleInitApp() {
    // Init world / resources
    // Init Camera
    // Init player
  }
  
  @Override
  public void simpleUpdate(float tpf) {
  }
  
  public static void main(String[] args) {
    App app = new App();
    app.start();
  }

}
