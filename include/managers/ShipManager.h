#ifndef __SHIP_MANAGER_H__
#define __SHIP_MANAGER_H__

#include "managers/ComponentManager.h"

namespace components {
  class ShipComponent;
}

namespace managers {
  using namespace components;

  class ShipManager {
    public:
      ShipManager();

      void initialize(ShipComponent* component);

      void remove(ShipComponent* component);

      void update(float timeSinceLastFrame);

    MANAGER_DEFINITION(Ship)
  };
}

#endif // __SHIP_MANAGER_H__
