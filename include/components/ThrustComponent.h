#ifndef __THRUST_COMPONENT_H__
#define __THRUST_COMPONENT_H__

#include "Component.h"
#include "managers/ThrustManager.h"

namespace components {

  /**
   * This component adds Velocity and Acceleration handling
   * to it's actor. This will always move it's actor according
   * to that actor's current rotation. Also this is purely forward
   * and backward acceleration.
   */
  class ThrustComponent : public Component {
    public:
      ThrustComponent() 
        : maxVelocity(10),
          velocity(0),
          acceleration(0),
          overdrive(false),
          _maxOverdrive(1000),
          _overdriveAccel(500),
          _overdriveTime(5),
          _overdriveTimer(0)
      { }

      int maxVelocity;

      float velocity;

      float acceleration;

      bool overdrive;

      int _maxOverdrive;
      int _overdriveAccel;
      int _overdriveTime;
      float _overdriveTimer;

      void accelerate(bool state) {
        acceleration = state ? 1 : 0;
      }

      void decelerate(bool state) {
        if(overdrive) {
          endOverdrive();
        } else {
          acceleration = state ? -1 : 0;
        }
      }

      void toggleOverdrive() {
        if(!overdrive) {
          overdrive = true;
          _overdriveTimer = 0;
        } else {
          endOverdrive();
        }
      }

      void endOverdrive() {
        overdrive = false;
        acceleration = _maxOverdrive * -0.5;
      }

      REGISTRATION_WITH(ThrustManager)
  };

}

#endif // __THRUST_COMPONENT_H__
