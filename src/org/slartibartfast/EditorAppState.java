package org.slartibartfast;

import com.jme3.app.state.AbstractAppState;

/**
 * This app state manages the Editor interface and underlying
 * Construct editing systems. The app moves into this state when the
 * player has chosen to edit a given construct or blueprint.
 */
public class EditorAppState extends AbstractAppState {

  public EditorAppState() {
    setEnabled(false);
  }
  
}
