#include "managers/TransformManager.h"

#include <OgreSceneManager.h>

namespace managers {

  TransformManager::TransformManager(Ogre::SceneManager* manager)
    : mSceneManager(manager)
  {
  }

  void TransformManager::initialize(TransformComponent* component) {
    // Figure out where to add the SceneNode for this component.
    // We'll want to look at the Actor's parent, if one exists,
    // otherwise it's pulled off of the root scene node
  }

  void TransformManager::update() {
    // Update all Actors, copy their current position, rotation, and scale
    // into their scene node
  }

  MANAGER_IMPLEMENTATION(Transform)
}
