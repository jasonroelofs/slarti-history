#ifndef __GAME_H__
#define __GAME_H__

#include <Ogre.h>

#include <OISInputManager.h>

#include "InputDispatcher.h"
#include "managers/InputManager.h"
#include "managers/CameraManager.h"
#include "Actor.h"
#include "Event.h"
#include "Level.h"
#include "SolarSystem.h"
#include "components/ShipComponent.h"

#include "ui/UIManager.h"

/**
 * The starting point for the entire game.
 * This class sets up Ogre, initializes input routines
 * and gets things rolling
 */
class Game : 
  public Ogre::FrameListener, 
  public Ogre::WindowEventListener
{
  public:
    Game(void);
    ~Game();

  public:
    /**
     * Build a new level
     */
    void newLevel(InputEvent event);

    /**
     * Shut down the game
     */
    void stop(InputEvent event);

    /**
     * Initialise the game logic
     */
    void go();
    bool setup(); 

    void toggleWireframe(InputEvent event);

    /////////////////////////////
    // Ogre::FrameListener
    /////////////////////////////
    virtual bool frameRenderingQueued(const Ogre::FrameEvent& evt);

    /////////////////////////////
    // Ogre::WindowEventListener
    /////////////////////////////
    
    /**
     * When the window is resized we need to make sure
     * to adjust the mouse clipping area for OIS
     */
    virtual void windowResized(Ogre::RenderWindow* rw);

    /**
     * Clear out all input information when the window closes
     */
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

    managers::InputManager* mInputManager;
    Ogre::SceneNode* mOgreHeadNode;

    InputDispatcher* mInputDispatcher;

    ui::UIManager* mUIManager;

    components::ShipComponent* mShip;

    Actor* mActor;

    // Our current 'level'
    Level* mLevel;
    
    // The current Solar System
    SolarSystem* mSolarSystem;

    //OIS Input devices
    OIS::InputManager* mOISInputManager;
    OIS::Mouse*    mMouse;
    OIS::Keyboard* mKeyboard;
};

#endif // __GAME_H__
