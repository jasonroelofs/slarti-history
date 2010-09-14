#include "Event.h"

namespace Event {
  /**
   * Convert OIS input events to our own InputEvent
   */
  InputEvent convert(const OIS::KeyEvent& arg) {
    InputEvent event(arg.key, Event::KeyboardEvent);
    return event;
  }

  InputEvent convert(const OIS::MouseEvent& arg) {
    InputEvent event(0, Event::MouseEvent);
    event.xDiff = arg.state.X.rel;
    event.yDiff = arg.state.Y.rel;
    
    return event;
  }
}
