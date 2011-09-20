package org.slartibartfast;

import org.slartibartfast.dataStores.DataResults;

/**
 * ConstructData is an internal representation object of a Construct
 * pulled from the database, to be used when building the graphical representation
 * of said construct
 */
public class ConstructData {

  public int id;

  public String name;

  // All constructs have a list of parts
  public DataResults parts;
}
