#include "managers/TransformManager.h"
#include "components/TransformComponent.h"

#include <OgreSceneManager.h>

namespace managers {

  TransformManager::TransformManager(Ogre::SceneManager* manager)
    : mSceneManager(manager)
  {
    msInstance = this;
  }

  void TransformManager::initialize(TransformComponent* component) {
    // Figure out where to add the SceneNode for this component.
    // We'll want to look at the Actor's parent, if one exists,
    // otherwise it's pulled off of the root scene node
    component->_sceneNode = mSceneManager->getRootSceneNode()->createChildSceneNode(); 
    component->_sceneNode->setPosition(component->position);
  }

  void TransformManager::remove(TransformComponent* component) {
  }

  void TransformManager::update() {
    // Update all Actors, copy their current position, rotation, and scale
    // into their scene node
    //_sceneNode.setPosition(component->position);
    //_sceneNode.setScale(component->scale);
  }

  MANAGER_IMPLEMENTATION(Transform)
}
