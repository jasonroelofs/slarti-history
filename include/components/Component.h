#ifndef __COMPONENT_H__
#define __COMPONENT_H__

class Actor;

/**
 * All components need to include this macro. It's called
 * by Actor in addComponent as the last step in adding a component
 * to an Actor. This will register the new component with it's
 * perspective manager
 */
#define REGISTRATION_WITH(Manager) \
  public: \
    void _register() { \
      managers::Manager::getInstance()->_registerComponent(this); \
    } \
    void _unregister() { \
      managers::Manager::getInstance()->_unregisterComponent(this); \
    }

#define FACTORY_COMPONENT \
  public: \
    void _register() { } \
    void _unregister() { }

namespace components {
  /**
   * Base class of all components.
   */
  class Component {

    public:
      Component() : isFactory(false) { }
      virtual ~Component() { }

      /**
       * Keep track of the actor this componet
       * has been added to. Managed by the component
       * specific manager
       */
      Actor* _actor;

      /**
       * Called by addComponent, use this to run any initialization
       * that requires knowing the parent actor.
       */
      virtual void initialize() { }

      /**
       * Self register with the perspective manager.
       * Use REGISTRATION_WITH instead of implementing this method
       * directly
       */
      virtual void _register() = 0;

      /**
       * Self unregister with the perspective manager.
       * Use REGISTRATION_WITH instead of implementing this method
       * directly
       */
      virtual void _unregister() = 0;

      /**
       * Is this component a factory? In that, does it build
       * other components and should be discarded instead of kept around?
       */
      bool isFactory;
  };
}

#endif // __COMPONENT_H__
