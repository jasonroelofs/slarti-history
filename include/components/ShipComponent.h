#ifndef __SHIP_COMPONENT_H__
#define __SHIP_COMPONENT_H__

#include "Component.h"
#include "managers/ShipManager.h"

namespace components {

  /**
   * This component adds Velocity and Acceleration handling
   * to it's actor. This will always move it's actor according
   * to that actor's current rotation. Also this is purely forward
   * and reverse acceleration.
   */
  class ShipComponent : public Component {
    public: 
      enum States {
        Flying = 0,
        Cruising = 1
      };

      // Indicies here relate to the states above
      const static int accelRates[2];
      const static int accelTo[2];

    public:
      ShipComponent() 
        : velocity(0),
          acceleration(0),
          _cruiseTime(5),
          _cruiseTimer(0)
      { 
        flying();
      }

      States currentState;

      float velocity;

      float acceleration;
      float _accelerateTo;

      // Timer variables for the cruise charge period
      float _cruiseTime;
      float _cruiseTimer;

      void accelerate(bool state) {
        if(currentState == Flying) {
          acceleration = state ? accelRates[Flying] : 0;
        }
      }

      void decelerate(bool state) {
        if(currentState == Cruising) {
          endCruise();
        } else {
          acceleration = state ? -accelRates[Flying] : 0;
        }
      }

      void toggleCruise() {
        if(currentState == Flying) {
          startCruise();
        } else {
          endCruise();
        }
      }

      void startCruise() {
        _cruiseTimer = 0;
        acceleration = accelRates[Cruising];
        cruising();
      }

      void endCruise() {
        acceleration = -accelRates[Cruising];
        _accelerateTo = accelTo[Flying];
      }

      void flying() {
        currentState = Flying;
        _accelerateTo = accelTo[Flying];
      }

      void cruising() {
        currentState = Cruising;
        _accelerateTo = accelTo[Cruising];
      }

      void velocityMet() {
        if(currentState == Cruising && acceleration < 0) {
          flying();
        }

        velocity = _accelerateTo;
        acceleration = 0;
      }

      int cruiseCharge() {
        return (_cruiseTimer / _cruiseTime) * 100;
      }

      REGISTRATION_WITH(ShipManager)
  };

}

#endif // __SHIP_COMPONENT_H__
