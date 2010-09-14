#ifndef __INPUT_DISPATCHER_H__
#define __INPUT_DISPATCHER_H__

#include <OISEvents.h>
#include <OISKeyboard.h>
#include <OISMouse.h>

#include "Event.h"
#include "InputManager.h"

/**
 * This class handles dispatching of all input events.
 * It's given a 'current' input manager which receives
 * all input events at the time. This class handles the Key -> Event
 * mapping as well, letting all InputManagers and every class
 * mapping events to only have to worry about Events.
 *
 * The current input manager is managed by Game
 */
class InputDispatcher :
  public OIS::KeyListener, 
  public OIS::MouseListener
{
  public:
    InputDispatcher();
    ~InputDispatcher() { }

    /**
     * Set the current input manager
     */
    void setCurrentInputManager(InputManager* im);

    // OIS::KeyListener
    virtual bool keyPressed( const OIS::KeyEvent &arg );
    virtual bool keyReleased( const OIS::KeyEvent &arg );
    // OIS::MouseListener
    virtual bool mouseMoved( const OIS::MouseEvent &arg );
    virtual bool mousePressed( const OIS::MouseEvent &arg, OIS::MouseButtonID id );
    virtual bool mouseReleased( const OIS::MouseEvent &arg, OIS::MouseButtonID id );

  protected:
    /**
     * Find the event in mKeyToEventMappings that matches
     * the key that's just been pressed or released.
     */
    int findEventFor(int key);

    /**
     * Given an InputEvent, add to it the event type the InputEvent
     * maps to
     */
    InputEvent setEventFor(InputEvent evt);

  private:
    InputManager* mCurrentInputManager;

    // Map Keys to Event Types
    // Types are supposed to map to:
    //  < (Key::Keys or Key::Buttons) , Event:Events >
    typedef std::map<int, int> KeyToEventMapping_T;
    KeyToEventMapping_T mKeyToEventMappings;
};

#endif // __INPUT_DISPATCHER_H__
