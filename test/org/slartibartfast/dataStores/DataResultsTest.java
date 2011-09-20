package org.slartibartfast.dataStores;

import com.jme3.math.Vector3f;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataResultsTest {

  public DataResultsTest() {
  }

  @Test
  public void canParseStringVectorFormat() {
    assertEquals(new Vector3f(1, 2, 3), DataResults.parseVector("v3:1,2,3"));
    assertEquals(new Vector3f(3, 2, 1), DataResults.parseVector("v3:3,2,1"));
    assertEquals(new Vector3f(1, 1, 1), DataResults.parseVector("v3:1,1,1"));
  }
}
