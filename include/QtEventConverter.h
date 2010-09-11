#ifndef __Qt_EVENT_CONVERTER_H__
#define __Qt_EVENT_CONVERTER_H__

#include <QKeyEvent>

#include "Event.h"

/**
 * Static class with a few helper methods to convert information
 * gleaned from Qt into a format used in the engine
 */
class QtEventConverter {
  public:

    /**
     * Given a Key Event from Qt, convert it into
     * our own KeyboardEvent.
     */
    static InputEvent convert(QKeyEvent* event) {
      return InputEvent(event->key(), Event::KeyboardEvent);
    }

};

#endif // __Qt_EVENT_CONVERTER_H__
