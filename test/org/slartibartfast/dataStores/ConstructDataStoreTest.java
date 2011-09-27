package org.slartibartfast.dataStores;

import org.junit.Before;
import org.slartibartfast.dataSources.DataSource;
import org.slartibartfast.Part;
import com.jme3.math.Vector3f;
import java.util.HashMap;
import org.slartibartfast.Construct;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ConstructDataStoreTest {

  private ConstructDataStore store;
  private DataSource dataSource;

  @Before
  public void setupDataSource() {
    dataSource = mock(DataSource.class);
    store = new ConstructDataStore(dataSource);
  }

  @Test
  public void canBeConstructedWithDataSource() {
    assertNotNull(store);
    assertEquals(dataSource, store.getDataSource());
  }

  private HashMap<String, String> buildPart(
          String fromPoint, String toPoint, String material) {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("start_point", fromPoint);
    map.put("end_point", toPoint);
    map.put("material", material);
    return map;
  }

  private HashMap<String, String> buildConstruct(
          String id, String name) {
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("id", id);
    map.put("name", name);
    return map;
  }

  @Test
  public void canLoadAndReturnAConstructFromDataSource() {
    DataResults constructData = new DataResults();
    constructData.add(buildConstruct("14", "constructName"));

    DataResults parts = new DataResults();
    parts.add(buildPart("v3:0,0,0", "v3:4,4,4", "Steel"));
    parts.add(buildPart("v3:0,0,0", "v3:8,8,8", "Rock"));

    when(dataSource.query("constructs",
            "select * from constructs where name = ?",
            "constructName")).thenReturn(constructData);

    when(dataSource.query("constructs",
            "select * from parts where construct_id = ?",
            "14")).thenReturn(parts);

    Construct construct = store.load("constructName");

    assertEquals("constructName", construct.getName());
    assertEquals(2, construct.getParts().size());

    Part part1 = construct.getParts().get(0);

    assertEquals(Vector3f.ZERO, part1.getStartPoint());
    assertEquals(new Vector3f(4, 4, 4), part1.getEndPoint());
    assertEquals("Steel", part1.getMaterial());

    Part part2 = construct.getParts().get(1);

    assertEquals(Vector3f.ZERO, part2.getStartPoint());
    assertEquals(new Vector3f(8, 8, 8), part2.getEndPoint());
    assertEquals("Rock", part2.getMaterial());
  }
}