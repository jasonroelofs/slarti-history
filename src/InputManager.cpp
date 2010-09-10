#include "InputManager.h"

InputManager::InputManager()
{
  // TODO Make this map configurable / built from a file
  mKeyToEventMappings.insert( 
      std::pair<int, int>(Key::Q, Event::Quit) );
  mKeyToEventMappings.insert( 
      std::pair<int, int>(Key::Escape, Event::Quit) );

  mKeyToEventMappings.insert( 
      std::pair<int, int>(Key::Left, Event::MoveLeft) );
  mKeyToEventMappings.insert( 
      std::pair<int, int>(Key::Right, Event::MoveRight) );
  mKeyToEventMappings.insert( 
      std::pair<int, int>(Key::Up, Event::MoveForward) );
  mKeyToEventMappings.insert( 
      std::pair<int, int>(Key::Up, Event::MoveBack) );
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
  event.isDown = true;
  mEventMappings[ mKeyToEventMappings[event.key] ]->call(event);
}

void InputManager::injectKeyUp(KeyboardEvent event)
{
  event.isDown = false;
  mEventMappings[ mKeyToEventMappings[event.key] ]->call(event);
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
