#ifndef __EVENT_H__
#define __EVENT_H__

#include <Qt>

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
    MoveBack,
    MoveForward,
    MoveLeft,
    MoveRight,

    MouseMoved,

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
    Escape = Qt::Key_Escape,

    Down = Qt::Key_Down,
    Left = Qt::Key_Left,
    Right = Qt::Key_Right,
    Up = Qt::Key_Up,

    Q = Qt::Key_Q
  };

  /**
   * Our map of mouse buttons
   */
  enum Buttons {
    None = Qt::NoButton,
    LeftMouse = Qt::LeftButton,
    RightMouse = Qt::RightButton,
    MiddleMouse = Qt::MidButton
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

    // Is this key event a key down event?
    int isDown;

    // Current position of the mouse, relative to the window
    int x, y;

    // Current global position of the mouse
    int globalX, globalY;
};

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
