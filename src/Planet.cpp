#include "Planet.h"
#include <OgreSceneManager.h>
#include <OgreEntity.h>

Planet::Planet() {

}

void Planet::attachTo(Ogre::SceneNode* node) {
  mSceneNode = node;
  Ogre::Entity* sphere = mSceneNode->getCreator()->createEntity("sphere.mesh");
  sphere->setMaterialName("dirt");
  mSceneNode->attachObject(sphere);
}
