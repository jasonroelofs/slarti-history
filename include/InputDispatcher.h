#ifndef __INPUT_DISPATCHER_H__
#define __INPUT_DISPATCHER_H__

#include <OISEvents.h>
#include <OISKeyboard.h>
#include <OISMouse.h>

#include "InputManager.h"

/**
 * This class handles dispatching of all input events.
 * It's given a 'current' input manager which receives
 * all input events at the time.
 *
 * The current input manager is managed by Game
 */
class InputDispatcher :
  public OIS::KeyListener, 
  public OIS::MouseListener
{
  public:
    InputDispatcher() :
      mCurrentInputManager(0)
    {}

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

  private:
    InputManager* mCurrentInputManager;
};

#endif // __INPUT_DISPATCHER_H__
