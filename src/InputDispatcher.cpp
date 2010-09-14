#include "InputDispatcher.h"

InputDispatcher::InputDispatcher()
  : mCurrentInputManager(0)
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

  mKeyToEventMappings.insert( 
      std::pair<int, int>(Key::S, Event::MoveLeft) );
  mKeyToEventMappings.insert( 
      std::pair<int, int>(Key::F, Event::MoveRight) );
  mKeyToEventMappings.insert( 
      std::pair<int, int>(Key::E, Event::MoveForward) );
  mKeyToEventMappings.insert( 
      std::pair<int, int>(Key::D, Event::MoveBack) );
}

void InputDispatcher::setCurrentInputManager(InputManager* im) {
  mCurrentInputManager = im;
}

bool InputDispatcher::keyPressed( const OIS::KeyEvent &arg ) {
  if(mCurrentInputManager) {
    mCurrentInputManager->injectKeyDown(setEventFor(Event::convert(arg)));
  }
  return true;
}

bool InputDispatcher::keyReleased( const OIS::KeyEvent &arg ) {
  if(mCurrentInputManager) {
    mCurrentInputManager->injectKeyUp(setEventFor(Event::convert(arg)));
  }
  return true;
}

bool InputDispatcher::mouseMoved( const OIS::MouseEvent &arg ) {
  if(mCurrentInputManager) {
    InputEvent e = Event::convert(arg);
    e.event = Event::MouseMoved;

    mCurrentInputManager->injectMouseMoved(e);
  }
  return true;
}

bool InputDispatcher::mousePressed( const OIS::MouseEvent &arg, OIS::MouseButtonID id ) {
  return true;
}

bool InputDispatcher::mouseReleased( const OIS::MouseEvent &arg, OIS::MouseButtonID id ) {
  return true;
}

int InputDispatcher::findEventFor(int key) {
  if(mKeyToEventMappings.count(key) > 0) {
    return mKeyToEventMappings[key];
  } else {
    return Event::None;
  }
}

InputEvent InputDispatcher::setEventFor(InputEvent evt) {
  evt.event = findEventFor(evt.key);
  return evt;
}
