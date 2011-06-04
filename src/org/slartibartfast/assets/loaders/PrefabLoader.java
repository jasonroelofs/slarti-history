package org.slartibartfast.assets.loaders;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.IOException;
import org.slartibartfast.prefabs.Hallway;
import org.slartibartfast.prefabs.Panel;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

/**
 *
 * @author roelofs
 */
public class PrefabLoader implements AssetLoader {
  
  private Yaml yaml;
  
  public PrefabLoader() {
    Constructor con = new Constructor();
    con.addTypeDescription(new TypeDescription(Hallway.class, "!hallway"));
    con.addTypeDescription(new TypeDescription(Panel.class, "!panel"));
    
    yaml = new Yaml(con);
  }
  
  @Override
  public Object load(AssetInfo assetInfo) throws IOException {
    return yaml.load(assetInfo.openStream());
  }
  
}
