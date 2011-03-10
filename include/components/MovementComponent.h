#ifndef __MOVEMENT_COMPONENT_H__
#define __MOVEMENT_COMPONENT_H__

#include "managers/InputManager.h"
#include "components/InputComponent.h"
#include "components/TransformComponent.h"
#include "Event.h"

namespace components {

  /**
   * Input component that handles forward/back and strafing
   * movement mapping
   */
  class MovementComponent : public InputComponent {

    public:

      virtual void mapEvents(managers::InputManager* manager) {
        manager->map(Event::MoveLeft, this, &MovementComponent::moveLeft);
        manager->map(Event::MoveRight, this, &MovementComponent::moveRight);
        manager->map(Event::MoveForward, this, &MovementComponent::moveForward);
        manager->map(Event::MoveBack, this, &MovementComponent::moveBack);

        manager->map(Event::RotateLeft, this, &MovementComponent::rotateLeft);
        manager->map(Event::RotateRight, this, &MovementComponent::rotateRight);
      }

      void moveLeft(InputEvent e) {
        _actor->transform->movingLeft = e.isDown;
      }

      void moveRight(InputEvent e) {
        _actor->transform->movingRight = e.isDown;
      }

      void moveForward(InputEvent e) {
        _actor->transform->movingForward = e.isDown;
      }

      void moveBack(InputEvent e) {
        _actor->transform->movingBack = e.isDown;
      }

      void rotateLeft(InputEvent e) {
        _actor->transform->rollingLeft = e.isDown;        
      }

      void rotateRight(InputEvent e) {
        _actor->transform->rollingRight = e.isDown;        
      }

  };

}

#endif // __MOVEMENT_COMPONENT_H__
