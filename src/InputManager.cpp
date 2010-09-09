#include "InputManager.h"

InputManager::InputManager()
{
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

void InputManager::injectKeyDown()
{
}

void InputManager::injectKeyUp()
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
