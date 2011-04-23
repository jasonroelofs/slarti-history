#include "PrefabManager.h"
#include "Prefab.h"
#include "Panel.h"

#include <OgreSceneManager.h>
#include <OgreResourceGroupManager.h>
#include <OgreLogManager.h>
#include <OgreArchive.h>

#include "procedural/ProceduralBoxGenerator.h"

#include <fstream>
#include <iostream>
using namespace std;

PrefabManager::PrefabManager(Ogre::SceneManager* manager) 
  : mSceneManager(manager)
{
  msInstance = this;

  Procedural::BoxGenerator().
    setSizeX(1.0f).
    setSizeY(0.5f).
    setSizeZ(1.0f).
    setNumSegX(1).
    setNumSegZ(1).
    realizeMesh("panel");
}

void PrefabManager::loadAllPrefabs() 
{
  Ogre::FileInfoListPtr fonts = Ogre::ResourceGroupManager::getSingleton().findResourceFileInfo("Prefabs", "*.yml");
  Ogre::String archive, filename;

  Ogre::FileInfoList::iterator start = fonts->begin();
  for(; start < fonts->end(); start++) {
    archive = (*start).archive->getName();
    filename = (*start).filename;

    parsePrefabsIn(archive + "/" + filename);
  }
}

Prefab* PrefabManager::getPrefab(const std::string& prefabName)
{
  PrefabMap::iterator it = mLoadedPrefabs.find(prefabName); 
  if(it != mLoadedPrefabs.end()) {
    return it->second;
  } else {
    return NULL;
  }
}

void PrefabManager::parsePrefabsIn(const std::string& filename) 
{
  Ogre::LogManager::getSingleton().logMessage("Loading prefabs in " + filename);

  std::ifstream in(filename.c_str());

  YAML::Parser parser(in);

  YAML::Node doc;
  parser.GetNextDocument(doc);
  std::string name;
  Prefab* prefab;

  try {
    for(YAML::Iterator it = doc.begin(); it != doc.end(); it++) {
      it.first() >> name;
      prefab = parsePrefab(name, it.second());
      cout << "Parsed prefab named " << name << " it's at " << prefab << endl;
      mLoadedPrefabs.insert(std::pair<std::string, Prefab*>(name, prefab));
    }
  } catch(YAML::Exception& ex) {
    Ogre::LogManager::getSingleton().stream() << "Error parsing prefabs: " << ex.what();
  }

  Ogre::LogManager::getSingleton().logMessage("Successfully loaded prefabs");
}

Prefab* PrefabManager::parsePrefab(const std::string& name, const YAML::Node& node) 
{
  Ogre::LogManager::getSingleton().logMessage("Parsing prefab " + name);

  Prefab* prefab = new Prefab();

  prefab->name = name;
  node["height"] >> prefab->height;
  node["width"] >> prefab->width;
  node["depth"] >> prefab->depth;

  const YAML::Node& panels = node["panels"];

  for(unsigned i = 0; i < panels.size(); i++) 
  {
    prefab->panels.push_back(parsePanel(panels[i]));
  }

  return prefab;
}

Panel* PrefabManager::parsePanel(const YAML::Node& node)
{
  Panel* panel = new Panel();
  Ogre::Vector3 position, rotation;

  node["position"]["x"] >> position.x;
  node["position"]["y"] >> position.y;
  node["position"]["z"] >> position.z;

  node["rotation"]["pitch"] >> rotation.x;
  node["rotation"]["yaw"] >> rotation.y;
  node["rotation"]["roll"] >> rotation.z;

  node["type"] >> panel->type;

  panel->position = position;
  panel->rotation = 
    Ogre::Quaternion(Ogre::Degree(rotation.y), Ogre::Vector3::UNIT_Y) *
    Ogre::Quaternion(Ogre::Degree(rotation.z), Ogre::Vector3::UNIT_Z) *
    Ogre::Quaternion(Ogre::Degree(rotation.x), Ogre::Vector3::UNIT_X);

  panel->meshName = "panel";
  panel->materialName = panel->type;

  Ogre::LogManager::getSingleton().stream() << "New panel build: " << panel->position << " - " << panel->rotation << " - " << panel->type;

  return panel;
}

PrefabManager* PrefabManager::msInstance = 0;
PrefabManager* PrefabManager::getInstance() {
  assert(msInstance && "You have to set msInstance in the Manager's constructor");
  return msInstance;
}
