#ifndef __COMPONENT_H__
#define __COMPONENT_H__

class Actor;

#define REGISTER_WITH(Manager) \
  managers::Manager::getInstance()->_registerComponent(this);

#define UNREGISTER_WITH(Manager) \
  managers::Manager::getInstance()->_unregisterComponent(this);

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
