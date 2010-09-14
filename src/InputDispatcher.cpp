#include "InputDispatcher.h"

void InputDispatcher::setCurrentInputManager(InputManager* im) {
  mCurrentInputManager = im;
}

bool InputDispatcher::keyPressed( const OIS::KeyEvent &arg ) {
  if(mCurrentInputManager) {
    mCurrentInputManager->injectKeyDown(Event::convert(arg));
  }
  return true;
}

bool InputDispatcher::keyReleased( const OIS::KeyEvent &arg ) {
  if(mCurrentInputManager) {
    mCurrentInputManager->injectKeyUp(Event::convert(arg));
  }
  return true;
}

bool InputDispatcher::mouseMoved( const OIS::MouseEvent &arg ) {
  if(mCurrentInputManager) {
    mCurrentInputManager->injectMouseMoved(Event::convert(arg));
  }
  return true;
}

bool InputDispatcher::mousePressed( const OIS::MouseEvent &arg, OIS::MouseButtonID id ) {
  return true;
}

bool InputDispatcher::mouseReleased( const OIS::MouseEvent &arg, OIS::MouseButtonID id ) {
  return true;
}

