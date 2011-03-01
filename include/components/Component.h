#ifndef __COMPONENT_H__
#define __COMPONENT_H__

class Actor;

namespace components {
  /**
   * Base class of all components.
   */
  class Component {

    public:
      /**
       * Keep track of the actor this componet
       * has been added to. Managed by the component
       * specific manager
       */
      Actor* _actor;

  };
}

#endif // __COMPONENT_H__
