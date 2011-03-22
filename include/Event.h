#ifndef __EVENT_H__
#define __EVENT_H__

#include <OISKeyboard.h>
#include <OISMouse.h>

#define CALL_EVENT_CALLBACK(object, function) ((object).*(function))

/********************************************
 * Event related definitions
 ********************************************/

namespace Event {
  /**
   * Enumeration of available Event types for input handling,
   * scoped so as to be able to write simple code like
   * Event::Quit
   */
  enum Events {
    Accelerate,
    Decelerate,
    Cruise,

    None,

    MoveBack,
    MoveForward,
    MoveLeft,
    MoveRight,

    RotateRight,
    RotateLeft,

    MouseMoved,

    RebuildLevel,

    ToggleWireframe,

    Quit
  };

  enum EventTypes {
    KeyboardEvent,
    MouseEvent
  };
}

namespace Key {
  /**
   * Our custom key binding enumeration.
   * For simplicity's sake, they are equal to Qt values.
   * I much prefer to build an API that lets one say
   * Key::Q over Key_Q
   */
  enum Keys {
    Escape = OIS::KC_ESCAPE,

    Down = OIS::KC_DOWN,
    Left = OIS::KC_LEFT,
    Right = OIS::KC_RIGHT,
    Up = OIS::KC_UP,

    A = OIS::KC_A,
    B = OIS::KC_B,
    C = OIS::KC_C,
    D = OIS::KC_D,
    E = OIS::KC_E,
    F = OIS::KC_F,
    G = OIS::KC_G,
    H = OIS::KC_H,
    I = OIS::KC_I,
    J = OIS::KC_J,
    K = OIS::KC_K,
    L = OIS::KC_L,
    M = OIS::KC_M,
    N = OIS::KC_N,
    O = OIS::KC_O,
    P = OIS::KC_P,
    Q = OIS::KC_Q,
    R = OIS::KC_R,
    S = OIS::KC_S,
    T = OIS::KC_T,
    U = OIS::KC_U,
    V = OIS::KC_V,
    W = OIS::KC_W,
    X = OIS::KC_X,
    Y = OIS::KC_Y,
    Z = OIS::KC_Z
  };

  /**
   * Our map of mouse buttons
   */
  enum Buttons {
    LeftMouse = OIS::MB_Left,
    RightMouse = OIS::MB_Right,
    MiddleMouse = OIS::MB_Middle,
  };
}

/********************************************
 * Internal Input Event Classes
 ********************************************/

/**
 * All input events get converted into this
 * class. It knows if it's keyboard or mouse
 * via type.
 */
class InputEvent {
  public:
    InputEvent(int key, int type) 
      : type(type),
        key(key)
    { }

    // What type of event is this? 
    // Will be one of Event::KeyboardEvent or
    // Event::MouseEvent
    int type;

    // Keycode of the key hit for this event.
    // For MouseEvents, this will be an OR map of
    // LeftButton / RightButton / MiddleButton
    int key;

    // What Event type (from Event::Events) does this input event map to?
    int event;

    // Is this key event a key down event?
    int isDown;

    // How has the mouse changed since the last event?
    int xDiff, yDiff;
};

namespace Event {
  /**
   * Convert OIS keyboard input events to our own InputEvent
   */
  InputEvent convert(const OIS::KeyEvent& arg); 

  /**
   * Convert OIS mouse movement  events to our own InputEvent
   */
  InputEvent convert(const OIS::MouseEvent& arg);
}

/********************************************
 * Event Callback Handlers
 ********************************************/

class EventCallbackBase {
  public:
    EventCallbackBase() {}
    virtual ~EventCallbackBase() { }

    /**
     * Run this event, executing the callback
     */
    virtual void call(InputEvent event) = 0;
};

template<typename Object_T, typename Callback_T>
class EventCallback : public EventCallbackBase {
  public:

    EventCallback(Object_T* object, Callback_T callback) 
      : mObject(object), mCallback(callback)
    {
    }

    virtual void call(InputEvent event) {
      CALL_EVENT_CALLBACK(*mObject, mCallback)(event);
    }

  private:
    Object_T* mObject;
    Callback_T mCallback;
};


#endif // __EVENT_H__
