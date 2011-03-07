#ifndef __MOVEMENT_COMPONENT_H__
#define __MOVEMENT_COMPONENT_H__

#include "managers/InputManager.h"
#include "components/InputComponent.h"
#include "Event.h"

#include <iostream>
using namespace std;

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
      }

      void moveLeft(InputEvent e) {
        cout << "moving left!" << endl;
      }

      void moveRight(InputEvent e) {
        cout << "moving right!" << endl;
      }

      void moveForward(InputEvent e) {
        cout << "moving forward!" << endl;
      }

      void moveBack(InputEvent e) {
        cout << "moving back!" << endl;
      }

  };

}

#endif // __MOVEMENT_COMPONENT_H__
