package org.slartibartfast;

/**
 * This factory handles loading of all constructs needed for
 * the scope and area in which it's created.
 */
public class ConstructFactory {

  public ConstructFactory() {

  }

  public Construct getConstruct(String name) {
    return new Construct();
  }
}
