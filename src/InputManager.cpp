#include "InputManager.h"

InputManager::InputManager()
{
  // TODO Make this map configurable / built from a file
  mKeyToEventMappings.insert( 
      std::pair<int, int>(Key::Q, Event::Quit) );
  mKeyToEventMappings.insert( 
      std::pair<int, int>(Key::Escape, Event::Quit) );
}

InputManager::~InputManager()
{
  EventMapping_T::iterator it;

  for(it = mEventMappings.begin(); it != mEventMappings.end(); it++) {
    if(it->second) {
      delete it->second;
    }
  }

  mEventMappings.clear();
}

void InputManager::injectKeyDown(KeyboardEvent event)
{
  mEventMappings[ mKeyToEventMappings[event.key] ]->call();
}

void InputManager::injectKeyUp(KeyboardEvent event)
{
}

void InputManager::injectMouseDown()
{
}

void InputManager::injectMouseUp()
{
}

void InputManager::injectMouseMoved()
{
}

void InputManager::injectMouseDoubleClick()
{
}

void InputManager::injectMouseWheel()
{
}
