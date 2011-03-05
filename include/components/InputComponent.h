#ifndef __INPUT_COMPONENT_H__
#define __INPUT_COMPONENT_H__

#include "components/Component.h"
#include "managers/InputManager.h"

namespace components {
  /**
   * Any actor with this component will immediately start
   * receiving input events.
   */
  class InputComponent : public Component {

    public:
      /**
       * Initialize a new Input component
       */
      InputComponent() 
      {
        REGISTER_WITH(InputManager)
      }

      ~InputComponent() {
        UNREGISTER_WITH(InputManager)
      }
  };
}
#endif // __INPUT_COMPONENT_H__
