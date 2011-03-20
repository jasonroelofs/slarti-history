#include "InputDispatcher.h"

#define MAP_KEY(key, event) \
  mKeyToEventMappings.insert(std::pair<int, int>(key, event) );
  

InputDispatcher::InputDispatcher()
  : mCurrentInputManager(0)
{
  // TODO Make this map configurable / built from a file
  MAP_KEY(Key::Q, Event::Quit);
  MAP_KEY(Key::Escape, Event::Quit);

  MAP_KEY(Key::Left, Event::MoveLeft);
  MAP_KEY(Key::Right, Event::MoveRight);
  MAP_KEY(Key::Up, Event::Accelerate);
  MAP_KEY(Key::Down, Event::Decelerate);

  MAP_KEY(Key::T, Event::Overdrive);

  MAP_KEY(Key::W, Event::RotateLeft);
  MAP_KEY(Key::R, Event::RotateRight);

  MAP_KEY(Key::S, Event::MoveLeft);
  MAP_KEY(Key::F, Event::MoveRight);
  MAP_KEY(Key::E, Event::Accelerate);
  MAP_KEY(Key::D, Event::Decelerate);

  MAP_KEY(Key::L, Event::RebuildLevel);

  MAP_KEY(Key::P, Event::ToggleWireframe);
}

void InputDispatcher::setCurrentInputManager(managers::InputManager* im) {
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
