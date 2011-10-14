package org.slartibartfast.dataStores;

import org.slartibartfast.dataSources.DataSource;
import java.util.HashMap;
import org.slartibartfast.Construct;
import org.slartibartfast.Part;

/**
 * This data store handles the loading and saving of Constructs and
 * their parts
 */
public class ConstructDataStore implements DataRepository<Construct> {

  private final DataSource dataSource;

  public ConstructDataStore(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public DataSource getDataSource() {
    return dataSource;
  }

  @Override
  public Construct load(String name) {
    DataResults constructData = dataSource.query(
            "constructs",
            "select * from constructs where name = ?", name);

    String constructId = (String)constructData.get(0).get("id");

    DataResults parts = dataSource.query(
            "constructs",
            "select * from parts where construct_id = ?", constructId);
    Construct construct = new Construct(name);

    for(HashMap<String, String> map : parts) {
      construct.addPart(new Part(
              DataResults.parseVector(map.get("start_point")),
              DataResults.parseVector(map.get("end_point")),
              (String)map.get("material")
              ));
    }

    return construct;
  }

  @Override
  public Construct load() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
