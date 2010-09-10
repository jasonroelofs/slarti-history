#ifndef __KEYBOARD_EVENT_H__
#define __KEYBOARD_EVENT_H__

/**
 * Our own keyboard event handler class.
 */
class KeyboardEvent {
  public:
    KeyboardEvent(int key) : key(key) { }

    // Keycode of the key hit for this event
    int key;

    // Is this key event a key down event?
    int isDown;
};

#endif // __KEYBOARD_EVENT_H__
