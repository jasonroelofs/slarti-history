#ifndef __PREFAB_MANAGER_H__
#define __PREFAB_MANAGER_H__

#include "yaml-cpp/yaml.h"

#include <string>
#include <map>

class Prefab;
class Panel;

class PrefabManager 
{
  public: 
    PrefabManager();

    /**
     * Initialization, run through all defined prefabs and load
     * them up into the system.
     */
    void loadAllPrefabs();

    static PrefabManager* getInstance();

  protected:
    void parsePrefabsIn(const std::string& filename);

    Prefab* parsePrefab(const std::string& name, const YAML::Node& node);

    Panel* parsePanel(const YAML::Node& node);

  protected:
    std::map<std::string, Prefab*> mLoadedPrefabs;

    static PrefabManager* msInstance;
};

#endif // __PREFAB_MANAGER_H__
