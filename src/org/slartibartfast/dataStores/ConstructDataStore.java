package org.slartibartfast.dataStores;

import org.slartibartfast.dataSources.IDataSource;
import java.util.HashMap;
import java.util.Map;
import org.slartibartfast.Construct;
import org.slartibartfast.Part;
import org.slartibartfast.dataProviders.DataResults;

/**
 * This data store handles the loading and saving of Constructs and
 * their parts
 */
public class ConstructDataStore implements IDataStore<Construct> {

  private final IDataSource dataSource;

  public ConstructDataStore(IDataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public IDataSource getDataSource() {
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

    for(HashMap<String, Object> map : parts) {
      construct.addPart(new Part(
              DataResults.parseVector(map.get("start_point")),
              DataResults.parseVector(map.get("end_point")),
              (String)map.get("material")
              ));
    }

    return construct;
  }
}
