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
      std::pair<int, int>(Key::Down, Event::MoveBack) );
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

void InputManager::injectKeyDown(InputEvent event)
{
  event.isDown = true;
  runMappingForEvent(event, findKeyFor(event.key));
}

void InputManager::injectKeyUp(InputEvent event)
{
  event.isDown = false;
  runMappingForEvent(event, findKeyFor(event.key));
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
  runMappingForEvent(event, Event::MouseMoved);
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

void InputManager::runMappingForEvent(InputEvent event, int key) {
  if(key < 0) {
    return;
  }
  
  if(mEventMappings.count(key) > 0) {
    mEventMappings[key]->call(event); 
  }
}

int InputManager::findKeyFor(int key) {
  if(mKeyToEventMappings.count(key) > 0) {
    return mKeyToEventMappings[key];
  } else {
    return -1;
  }
}
