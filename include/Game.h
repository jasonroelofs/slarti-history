#ifndef __GAME_H__
#define __GAME_H__

#include <Ogre.h>

#include <OISEvents.h>
#include <OISInputManager.h>
#include <OISKeyboard.h>
#include <OISMouse.h>

#include "InputManager.h"
#include "CameraManager.h"
#include "Event.h"

/**
 * The starting point for the entire game.
 * This class sets up Ogre, initializes input routines
 * and gets things rolling
 */
class Game : 
  public Ogre::FrameListener, 
  public Ogre::WindowEventListener,
  public OIS::KeyListener, 
  public OIS::MouseListener
{
  public:
    Game(void);
    ~Game();

  public:
    /**
     * Shut down the game
     */
    void stop(InputEvent event);

    /**
     * Initialise the game logic
     */
    void go();
    bool setup(); 

    // Ogre::FrameListener
    virtual bool frameRenderingQueued(const Ogre::FrameEvent& evt);

    // OIS::KeyListener
    virtual bool keyPressed( const OIS::KeyEvent &arg );
    virtual bool keyReleased( const OIS::KeyEvent &arg );
    // OIS::MouseListener
    virtual bool mouseMoved( const OIS::MouseEvent &arg );
    virtual bool mousePressed( const OIS::MouseEvent &arg, OIS::MouseButtonID id );
    virtual bool mouseReleased( const OIS::MouseEvent &arg, OIS::MouseButtonID id );

    // Ogre::WindowEventListener
    //Adjust mouse clipping area
    virtual void windowResized(Ogre::RenderWindow* rw);
    //Unattach OIS before window shutdown (very important under Linux)
    virtual void windowClosed(Ogre::RenderWindow* rw);

    void log(const Ogre::String& message) {
      Ogre::LogManager::getSingleton().logMessage(message);
    }

  protected:
    bool mShutdown;

    Ogre::Root* mRoot;
    Ogre::SceneManager* mSceneManager;
    Ogre::Camera* mCamera;

    Ogre::RenderWindow* mWindow;

    Ogre::SceneNode* mOgreHeadNode;

    InputManager* mInputManager;

    CameraManager* mCameraManager;

    //OIS Input devices
    OIS::InputManager* mOISInputManager;
    OIS::Mouse*    mMouse;
    OIS::Keyboard* mKeyboard;
};

#endif // __GAME_H__
