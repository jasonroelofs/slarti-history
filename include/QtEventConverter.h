#ifndef __Qt_EVENT_CONVERTER_H__
#define __Qt_EVENT_CONVERTER_H__

#include <QKeyEvent>
#include <QMouseEvent>

#include "Event.h"

/**
 * Static class with a few helper methods to convert information
 * gleaned from Qt into a format used in the engine
 */
class QtEventConverter {
  public:

    /**
     * Given a Key Event from Qt, convert it into
     * our own InputEvent.
     */
    static InputEvent convert(QKeyEvent* event) {
      return InputEvent(event->key(), Event::KeyboardEvent);
    }

    /**
     * Given a Mouse Event from Qt, convert it to
     * our InputEvent with the information we need
     */
    static InputEvent convert(QMouseEvent* event) {
      InputEvent evt(event->buttons(), Event::MouseEvent);
      evt.x = event->x();
      evt.y = event->y();
      evt.globalX = event->globalX();
      evt.globalY = event->globalY();
      return evt;
    }

};

#endif // __Qt_EVENT_CONVERTER_H__
