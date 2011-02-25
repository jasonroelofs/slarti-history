#include "Star.h"
#include <OgreSceneManager.h>
#include <OgreEntity.h>

Star::Star() {

}

void Star::attachTo(Ogre::SceneNode* node) {
  mSceneNode = node;
  Ogre::Entity* sphere = mSceneNode->getCreator()->createEntity("sphere.mesh");
  mSceneNode->attachObject(sphere);
}
