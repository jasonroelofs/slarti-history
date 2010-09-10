#ifndef __EVENT_H__
#define __EVENT_H__

#include <Qt>

#include "KeyboardEvent.h"

#define CALL_EVENT_CALLBACK(object, function) ((object).*(function))

namespace Event {
  /**
   * Enumeration of available EVENT types for input handling,
   * scoped so as to be able to write simple code like
   * Event::QUIT
   */
  enum Events {
    MoveBack,
    MoveForward,
    MoveLeft,
    MoveRight,

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

    Left = Qt::Key_Left,
    Right = Qt::Key_Right,
    Up = Qt::Key_Up,
    Down = Qt::Key_Down,

    Q = Qt::Key_Q
  };
}

class EventCallbackBase {
  public:
    EventCallbackBase() {}
    virtual ~EventCallbackBase() { }

    /**
     * Run this event, executing the callback
     */
    virtual void call(KeyboardEvent event) = 0;
};

template<typename Object_T, typename Callback_T>
class EventCallback : public EventCallbackBase {
  public:

    EventCallback(Object_T* object, Callback_T callback) 
      : mObject(object), mCallback(callback)
    {
    }

    virtual void call(KeyboardEvent event) {
      CALL_EVENT_CALLBACK(*mObject, mCallback)(event);
    }

  private:
    Object_T* mObject;
    Callback_T mCallback;
};



#endif // __EVENT_H__
