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

      if(component->currentState == ShipComponent::Cruising && 
          (component->_cruiseTimer <= component->_cruiseTime)) {
        component->_cruiseTimer += timeSinceLastFrame;
      } else {
        component->velocity += component->acceleration * timeSinceLastFrame;

        if(component->velocity < 0.001) {
          component->velocity = 0.0;
          component->acceleration = 0.0;
        }

        if(component->currentState == ShipComponent::Cruising && component->acceleration < 0) {
          if(component->velocity < component->_accelerateTo) {
            component->velocityMet();
          }
        } else {
          if(component->velocity > component->_accelerateTo) {
            component->velocityMet();
          }
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
