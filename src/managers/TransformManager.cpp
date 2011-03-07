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
    mComponents.push_back(component);

    // Figure out where to add the SceneNode for this component.
    // We'll want to look at the Actor's parent, if one exists,
    // otherwise it's pulled off of the root scene node
    component->_sceneNode = mSceneManager->getRootSceneNode()->createChildSceneNode(); 
    component->_sceneNode->setPosition(component->position);
  }

  void TransformManager::remove(TransformComponent* component) {
  }

  void TransformManager::update() {
    TransformComponent* component; 
    ComponentIterator it = mComponents.begin(); 
    ComponentIterator end = mComponents.end(); 

    for(; it < end; it++) { 
       component = *it;

      Ogre::SceneNode* node = component->_sceneNode;
      Ogre::Vector3 pos = component->position;
      int speed = 1;

      if (component->movingForward) {
        pos.z -= speed;
      }
      if (component->movingBack) {
        pos.z += speed;
      }
      if (component->movingRight) {
        pos.x += speed;
      }
      if (component->movingLeft) {
        pos.x -= speed;
      }

      component->position = pos;
      component->_sceneNode->setPosition(component->position);
    }
  }


  MANAGER_IMPLEMENTATION(Transform)
}
