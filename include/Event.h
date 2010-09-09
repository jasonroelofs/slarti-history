#ifndef __EVENT_H__
#define __EVENT_H__

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

    }

  private:
    Object_T* mObject;
    Callback_T mCallback;
};



#endif // __EVENT_H__
