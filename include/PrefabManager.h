#ifndef __PREFAB_MANAGER_H__
#define __PREFAB_MANAGER_H__

#include "yaml-cpp/yaml.h"

#include <OgreMesh.h>

#include <string>
#include <map>

class Prefab;
class Panel;

namespace Ogre {
  class SceneManager;
}

class PrefabManager 
{
  public: 
    PrefabManager(Ogre::SceneManager* manager);

    /**
     * Initialization, run through all defined prefabs and load
     * them up into the system.
     */
    void loadAllPrefabs();

    /**
     * Returns the prefab for the requested name
     */
    Prefab* getPrefab(const std::string& prefabName);

    static PrefabManager* getInstance();

  protected:
    void parsePrefabsIn(const std::string& filename);

    Prefab* parsePrefab(const std::string& name, const YAML::Node& node);

    Panel* parsePanel(const YAML::Node& node);

    void buildEntityFor(const Prefab* prefab);

  protected:
    typedef std::map<std::string, Prefab*> PrefabMap;
    PrefabMap mLoadedPrefabs;

    Ogre::SceneManager* mSceneManager;

    Ogre::MeshPtr mPanelMesh;

    static PrefabManager* msInstance;
};

#endif // __PREFAB_MANAGER_H__
