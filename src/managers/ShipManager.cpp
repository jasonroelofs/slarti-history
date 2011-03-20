#include "managers/ShipManager.h"
#include "components/ShipComponent.h"
#include "components/TransformComponent.h"
#include "Actor.h"

#include <OgreVector3.h>
#include <OgreQuaternion.h>

namespace managers {

  ShipManager::ShipManager()
  {
    msInstance = this;
  }

  void ShipManager::initialize(ShipComponent* component) {
    mComponents.push_back(component);
  }

  void ShipManager::remove(ShipComponent* component) {
  }

  void ShipManager::update(float timeSinceLastFrame) {
    ShipComponent* component; 
    TransformComponent* transform;
    ComponentIterator it = mComponents.begin(); 
    ComponentIterator end = mComponents.end(); 

    Ogre::Vector3 direction, right, velocityDir;

    for(; it < end; it++) { 
      component = *it;
      transform = component->_actor->transform;

      if(component->overdrive) {
        if(component->_overdriveTimer >= component->_overdriveTime) {
          component->acceleration = component->_overdriveAccel;
        } else {
          component->_overdriveTimer += timeSinceLastFrame;
        }
      }

      component->velocity += component->acceleration * timeSinceLastFrame;

      if(component->velocity < 0.001) {
        component->velocity = 0.0;
        component->acceleration = 0.0;
      }

      if(component->overdrive) {
        if(component->velocity > component->_maxOverdrive) {
          component->velocity = component->_maxOverdrive;
        }
      }

      direction = transform->rotation * -Ogre::Vector3::UNIT_Z;
      right = transform->rotation * Ogre::Vector3::UNIT_X;
      velocityDir = direction * component->velocity;

      transform->position += velocityDir;
    }
  }

  MANAGER_IMPLEMENTATION(Ship)
}
