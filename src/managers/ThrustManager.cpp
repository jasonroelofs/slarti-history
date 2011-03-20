#include "managers/ThrustManager.h"
#include "components/ThrustComponent.h"
#include "components/TransformComponent.h"
#include "Actor.h"

#include <OgreVector3.h>
#include <OgreQuaternion.h>

namespace managers {

  ThrustManager::ThrustManager()
  {
    msInstance = this;
  }

  void ThrustManager::initialize(ThrustComponent* component) {
    mComponents.push_back(component);
  }

  void ThrustManager::remove(ThrustComponent* component) {
  }

  void ThrustManager::update(float timeSinceLastFrame) {
    ThrustComponent* component; 
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
      }

      if(component->overdrive) {
        if(component->velocity > component->_maxOverdrive) {
          component->velocity = component->_maxOverdrive;
        }
      } else {
        if(component->velocity > component->maxVelocity) {
          component->velocity = component->maxVelocity;
        }
      }

      direction = transform->rotation * -Ogre::Vector3::UNIT_Z;
      right = transform->rotation * Ogre::Vector3::UNIT_X;
      velocityDir = direction * component->velocity;

      transform->position += velocityDir;
    }
  }

  MANAGER_IMPLEMENTATION(Thrust)
}
