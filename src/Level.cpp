#include "Level.h"

#include <OgreSceneNode.h>
#include <OgreEntity.h>

Level::Level(Ogre::SceneManager* manager)
  : mSceneManager(manager)
{
}

void Level::generate() {
  Ogre::SceneNode* root = mSceneManager->getRootSceneNode();
  Ogre::SceneNode* worldNode = root->createChildSceneNode("level");

  Ogre::Entity* box = mSceneManager->createEntity("boxlevel", Ogre::SceneManager::PT_CUBE);

  worldNode->scale(10, 10, 10);
  worldNode->attachObject(box);
}
