#ifndef __THRUST_MANAGER_H__
#define __THRUST_MANAGER_H__

#include "managers/ComponentManager.h"

namespace components {
  class ThrustComponent;
}

namespace managers {
  using namespace components;

  class ThrustManager {
    public:
      ThrustManager();

      void initialize(ThrustComponent* component);

      void remove(ThrustComponent* component);

      void update(float timeSinceLastFrame);

    MANAGER_DEFINITION(Thrust)
  };
}

#endif // __THRUST_MANAGER_H__
