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

  void TransformManager::update(float timeSinceLastFrame) {
    TransformComponent* component; 
    ComponentIterator it = mComponents.begin(); 
    ComponentIterator end = mComponents.end(); 

    for(; it < end; it++) { 
       component = *it;

      Ogre::SceneNode* node = component->_sceneNode;
      Ogre::Vector3 pos = component->position;
      int speed = 1;

      if(component->moveRelativeToRotation) {
        Ogre::Vector3 direction = component->rotation * -Ogre::Vector3::UNIT_Z;
        Ogre::Vector3 right = component->rotation * Ogre::Vector3::UNIT_X;
        Ogre::Vector3 moveDir = Ogre::Vector3::ZERO;

        if(component->movingForward) {
          moveDir += direction;
        }
        if(component->movingBack) {
          moveDir -= direction;
        }
        if(component->movingRight) {
          moveDir += right;
        }
        if(component->movingLeft) {
          moveDir -= right;
        }

        pos += moveDir * speed;
      } else {
        if(component->movingForward) {
          pos.z -= speed;
        }
        if(component->movingBack) {
          pos.z += speed;
        }
        if(component->movingRight) {
          pos.x += speed;
        }
        if(component->movingLeft) {
          pos.x -= speed;
        }
      }

      component->position = pos;

      component->_sceneNode->setPosition(component->position);
      component->_sceneNode->setOrientation(component->rotation);
    }
  }


  MANAGER_IMPLEMENTATION(Transform)
}
