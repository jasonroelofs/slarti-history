#ifndef __INPUT_MANAGER_H__
#define __INPUT_MANAGER_H__

#include <map>

#include "Event.h"

/**
 * This class handles all manner of input dispatch throughout
 * the game system. Events are given to it via Game and Qt's 
 * input event system and dispatched out according to installed
 * handlers.
 *
 * There can be more than one input manager, but it's preferred
 * that only one be active at a time.
 */
class InputManager 
{
  public:
    InputManager();
    ~InputManager();

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
    void injectKeyDown(KeyboardEvent event);
    void injectKeyUp(KeyboardEvent event);

    void injectMouseDown();
    void injectMouseUp();

    void injectMouseMoved();

    void injectMouseDoubleClick();

    void injectMouseWheel();

  private:
    // Map Event Types to Callbacks
    typedef std::map<int, EventCallbackBase*> EventMapping_T;

    // Map Keys to Event Types
    typedef std::map<int, int> KeyToEventMapping_T;

    EventMapping_T mEventMappings;

    KeyToEventMapping_T mKeyToEventMappings;
};

#endif // __INPUT_MANAGER_H__
