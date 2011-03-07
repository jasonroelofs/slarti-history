#ifndef __INPUT_COMPONENT_H__
#define __INPUT_COMPONENT_H__

#include "components/Component.h"
#include "managers/InputManager.h"

namespace components {
  /**
   * Any actor with this component will immediately start
   * receiving input events.
   *
   * Abstract base class of all input handler components
   */
  class InputComponent : public Component {

    public:
      /**
       * Initialize a new Input component
       */
      InputComponent() {}

      /**
       * Callback to let this component hook up the events
       * it wants to process.
       */
      virtual void mapEvents(managers::InputManager* manager) = 0;

      
      REGISTRATION_WITH(InputManager)
  };
}
#endif // __INPUT_COMPONENT_H__
