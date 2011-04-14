#include "PrefabManager.h"
#include "Prefab.h"
#include "Panel.h"

#include <OgreResourceGroupManager.h>
#include <OgreLogManager.h>
#include <OgreArchive.h>

#include <fstream>

PrefabManager::PrefabManager() 
{
  msInstance = this;
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

void PrefabManager::parsePrefabsIn(const std::string& filename) 
{
  Ogre::LogManager::getSingleton().logMessage("Loading prefabs in " + filename);

  std::ifstream in(filename.c_str());

  YAML::Parser parser(in);

  YAML::Node doc;
  parser.GetNextDocument(doc);
  std::string name;

  try {
    for(YAML::Iterator it = doc.begin(); it != doc.end(); it++) {
      it.first() >> name;
      mLoadedPrefabs.insert(std::pair<std::string, Prefab*>(name, parsePrefab(name, it.second())));
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
  Ogre::Vector3 location, rotation;

  node["location"]["x"] >> location.x;
  node["location"]["y"] >> location.y;
  node["location"]["z"] >> location.z;

  node["rotation"]["pitch"] >> rotation.x;
  node["rotation"]["yaw"] >> rotation.y;
  node["rotation"]["roll"] >> rotation.z;

  node["type"] >> panel->type;

  panel->rotation = rotation;
  panel->location = location;

  Ogre::LogManager::getSingleton().stream() << "New panel build: " << panel->location << " - " << panel->rotation << " - " << panel->type;

  return panel;
}

PrefabManager* PrefabManager::msInstance = 0;
PrefabManager* PrefabManager::getInstance() {
  assert(msInstance && "You have to set msInstance in the Manager's constructor");
  return msInstance;
}
