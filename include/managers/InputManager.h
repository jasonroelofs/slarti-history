#ifndef __INPUT_MANAGER_H__
#define __INPUT_MANAGER_H__

#include "managers/ComponentManager.h"
#include "Event.h"

#include <map>

namespace components {
  class InputComponent;
}

namespace managers {
  using namespace components;

  /**
   * Manager for all Input components. Input events get
   * sent to this Manager who then knows how to delegate
   * the events to actions on the components and actors involved.
   */
  class InputManager {
    public:

      /**
       * Initialize our manager
       */
      InputManager();

      /**
       * We've got a new component, initialize
       * it with Ogre and the system as a whole.
       */
      void initialize(InputComponent* component);

      /**
       * When a component is removed from the system, 
       * allow the manager to clean up before destruction
       */
      void remove(InputComponent* component);

      /**
       * Update all actors
       */
      void update();

      /**
       * Map an Event to an object and a method to call on that object.
       */
      template<typename Object_T, typename Callback_T>
      void map(int event, Object_T* object, Callback_T callback)
      {
        EventCallbackBase* eventCallback = 
          new EventCallback<Object_T, Callback_T>(object, callback);

        mEventMappings.insert( 
            std::pair<int, EventCallbackBase*>(event, eventCallback) );
      }

      /**
       * Inject methods are meant to only be called from Game.
       * They are used to inform ourselves of new keyboard or mouse interactions
       */

      /**
       * The following map to a single event, but set the down parameter
       * of the callback according to the event we've received from Qt
       */
      void injectKeyDown(InputEvent event);
      void injectKeyUp(InputEvent event);

      void injectMouseDown();
      void injectMouseUp();

      void injectMouseMoved(InputEvent event);

      void injectMouseDoubleClick();

      void injectMouseWheel();

    protected:

      void runMappingForEvent(InputEvent event);

    private:
      // Map Event Types to Callbacks
      typedef std::map<int, EventCallbackBase*> EventMapping_T;

      EventMapping_T mEventMappings;


    MANAGER_DEFINITION(Input)
  };

}
#endif // __INPUT_MANAGER_H__

