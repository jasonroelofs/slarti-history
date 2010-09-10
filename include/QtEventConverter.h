#ifndef __Qt_EVENT_CONVERTER_H__
#define __Qt_EVENT_CONVERTER_H__

#include <QKeyEvent>

#include "KeyboardEvent.h"

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
    static KeyboardEvent convert(QKeyEvent* event) {
      return KeyboardEvent(event->key());
    }

};

#endif // __Qt_EVENT_CONVERTER_H__
