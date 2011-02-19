#include "Planet.h"

Planet::Planet() {

}

void Planet::attachTo(Ogre::SceneNode* node) {
  mSceneNode = node;
  //mSceneNode->attachObject(getMoveableObject());
}
