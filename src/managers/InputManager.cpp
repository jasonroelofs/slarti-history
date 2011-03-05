#include "managers/InputManager.h"

namespace managers {

  InputManager::InputManager()
  {
    msInstance = this;
  }

  void InputManager::initialize(InputComponent* component) {
  }

  void InputManager::remove(InputComponent* component) {

    /*
    EventMapping_T::iterator it;

    for(it = mEventMappings.begin(); it != mEventMappings.end(); it++) {
      if(it->second) {
        delete it->second;
      }
    }

    mEventMappings.clear();
    */
  }

  void InputManager::update() {
  }

  void InputManager::injectKeyDown(InputEvent event)
  {
    event.isDown = true;
    runMappingForEvent(event);
  }

  void InputManager::injectKeyUp(InputEvent event)
  {
    event.isDown = false;
    runMappingForEvent(event);
  }

  void InputManager::injectMouseDown()
  {
  }

  void InputManager::injectMouseUp()
  {
  }

  /**
   * MouseMove is a special case. There is no key to bind
   * to, just look for events waiting on MouseMoved
   */
  void InputManager::injectMouseMoved(InputEvent event)
  {
    runMappingForEvent(event);
  }

  void InputManager::injectMouseDoubleClick()
  {
  }

  void InputManager::injectMouseWheel()
  {
  }

  /**
   * Find an event handler and run it for the given key.
   * We do this because std::map::operator[] will end up creating
   * an entry if one doesn't exist, and we want to make very sure
   * that unmapped keys are ignored.
   */
  void InputManager::runMappingForEvent(InputEvent event) {
    if(mEventMappings.count(event.event) > 0) {
      mEventMappings[event.event]->call(event); 
    }
  }

  MANAGER_IMPLEMENTATION(Input)
}
