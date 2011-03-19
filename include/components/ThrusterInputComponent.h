#ifndef __THRUSTER_INPUT_COMPONENT_H__
#define __THRUSTER_INPUT_COMPONENT_H__

#include "managers/InputManager.h"
#include "components/InputComponent.h"
#include "components/TransformComponent.h"
#include "components/ThrustComponent.h"
#include "Event.h"

namespace components {

  /**
   * Hook up thrust to the input system
   */
  class ThrusterInputComponent : public InputComponent {

    public:

      ThrusterInputComponent(ThrustComponent* thruster) 
        : InputComponent(),
          _thruster(thruster)
      {
      }

      virtual void mapEvents(managers::InputManager* manager) {
        manager->map(Event::MoveLeft, this, &ThrusterInputComponent::moveLeft);
        manager->map(Event::MoveRight, this, &ThrusterInputComponent::moveRight);
        manager->map(Event::Accelerate, this, &ThrusterInputComponent::accelerate);
        manager->map(Event::Decelerate, this, &ThrusterInputComponent::decelerate);

        manager->map(Event::RotateLeft, this, &ThrusterInputComponent::rotateLeft);
        manager->map(Event::RotateRight, this, &ThrusterInputComponent::rotateRight);
      }

      void accelerate(InputEvent e) {
        _thruster->accelerate(e.isDown);
      }

      void decelerate(InputEvent e) {
        _thruster->decelerate(e.isDown);
      }

      void moveLeft(InputEvent e) {
        _actor->transform->movingLeft = e.isDown;
      }

      void moveRight(InputEvent e) {
        _actor->transform->movingRight = e.isDown;
      }

      void rotateLeft(InputEvent e) {
        _actor->transform->rollingLeft = e.isDown;        
      }

      void rotateRight(InputEvent e) {
        _actor->transform->rollingRight = e.isDown;        
      }

      components::ThrustComponent* _thruster;

  };

}

#endif // __THRUSTER_INPUT_COMPONENT_H__
