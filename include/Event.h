#ifndef __EVENT_H__
#define __EVENT_H__

#include <Qt>

#define CALL_EVENT_CALLBACK(object, function) ((object).*(function))

namespace Event {
  /**
   * Enumeration of available EVENT types for input handling,
   * scoped so as to be able to write simple code like
   * Event::QUIT
   */
  enum Events {
    QUIT
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
    ESCAPE = Qt::Key_Escape,
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
    virtual void call() = 0;
};

template<typename Object_T, typename Callback_T>
class EventCallback : public EventCallbackBase {
  public:

    EventCallback(Object_T* object, Callback_T callback) 
      : mObject(object), mCallback(callback)
    {
    }

    virtual void call() {
      CALL_EVENT_CALLBACK(*mObject, mCallback)();
    }

  private:
    Object_T* mObject;
    Callback_T mCallback;
};



#endif // __EVENT_H__
