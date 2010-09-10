#ifndef __GAME_H__
#define __GAME_H__

#include <GameLogic.h>
#include <Ogre.h>

#include <QTime>

#include "InputManager.h"
#include "CameraManager.h"
#include "Event.h"

class Game : public QtOgre::GameLogic
{
  public:
    Game(void);

  public:
    /**
     * Shut down the game
     */
    void stop(KeyboardEvent event);

    /**
     * Initialise the game logic
     */
    virtual void initialise(void);

    /**
     * Callback: Update the game
     */
    virtual void update(void);

    /**
     * Callback: game is shutting down, clean up resources
     */
    virtual void shutdown(void);

    virtual void onKeyPress(QKeyEvent* event);
    virtual void onKeyRelease(QKeyEvent* event);

    virtual void onMousePress(QMouseEvent* event);
    virtual void onMouseRelease(QMouseEvent* event);
    virtual void onMouseDoubleClick(QMouseEvent* event);
    virtual void onMouseMove(QMouseEvent* event);

    virtual void onWheel(QWheelEvent* event);

    void log(const Ogre::String& message) {
      Ogre::LogManager::getSingleton().logMessage(message);
    }

  protected:
    Ogre::Root* mRoot;
    Ogre::SceneManager* mSceneManager;
    Ogre::Camera* mCamera;

    Ogre::SceneNode* mOgreHeadNode;

    QTime mTime;
    unsigned int mTimeOfLastFrame;

    InputManager* mInputManager;

    CameraManager* mCameraManager;
};

#endif // __GAME_H__
