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
}

/********************************************
 * Internal Input Event Classes
 ********************************************/

/**
 * Base class for all input event classes.
 * Don't use this class directly
 */
class InputEvent { };

/**
 * Our own keyboard event handler class.
 */
class KeyboardEvent : public InputEvent {
  public:
    KeyboardEvent(int key) : key(key) { }

    // Keycode of the key hit for this event
    int key;

    // Is this key event a key down event?
    int isDown;
};

/**
 * Handles mouse events
 */
class MouseEvent : public InputEvent {
  public:
    MouseEvent() {}
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
      // TODO
      // Need to pointer-ify the event stuff for this to work
      // 
      CALL_EVENT_CALLBACK(*mObject, mCallback)((KeyboardEvent)event);
    }

  private:
    Object_T* mObject;
    Callback_T mCallback;
};


#endif // __EVENT_H__
