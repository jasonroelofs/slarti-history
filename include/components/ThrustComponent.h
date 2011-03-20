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
          acceleration(0)
      { }

      int maxVelocity;

      float velocity;

      float acceleration;

      void accelerate(bool state) {
        acceleration = state ? 1 : 0;
      }

      void decelerate(bool state) {
        acceleration = state ? -1 : 0;
      }

      REGISTRATION_WITH(ThrustManager)
  };

}

#endif // __THRUST_COMPONENT_H__
