#ifndef __SHIP_INPUT_COMPONENT_H__
#define __SHIP_INPUT_COMPONENT_H__

#include "managers/InputManager.h"
#include "components/InputComponent.h"
#include "components/TransformComponent.h"
#include "components/ShipComponent.h"
#include "Event.h"

namespace components {

  /**
   * Hook up thrust to the input system
   */
  class ShipInputComponent : public InputComponent {

    public:

      ShipInputComponent(ShipComponent* thruster) 
        : InputComponent(),
          _ship(thruster)
      {
      }

      virtual void mapEvents(managers::InputManager* manager) {
        manager->map(Event::MoveLeft, this, &ShipInputComponent::moveLeft);
        manager->map(Event::MoveRight, this, &ShipInputComponent::moveRight);
        manager->map(Event::Accelerate, this, &ShipInputComponent::accelerate);
        manager->map(Event::Decelerate, this, &ShipInputComponent::decelerate);

        manager->map(Event::RotateLeft, this, &ShipInputComponent::rotateLeft);
        manager->map(Event::RotateRight, this, &ShipInputComponent::rotateRight);

        manager->map(Event::Cruise, this, &ShipInputComponent::cruise);
      }

      void accelerate(InputEvent e) {
        _ship->accelerate(e.isDown);
      }

      void decelerate(InputEvent e) {
        _ship->decelerate(e.isDown);
      }

      void cruise(InputEvent e) {
        if(e.isDown) {
          _ship->toggleCruise();
        }
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

      components::ShipComponent* _ship;

  };

}

#endif // __SHIP_INPUT_COMPONENT_H__
