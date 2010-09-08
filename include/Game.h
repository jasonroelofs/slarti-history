#ifndef __GAME_H__
#define __GAME_H__

#include <GameLogic.h>

#include <Ogre.h>

class Game : public QtOgre::GameLogic
{
  public:
    Game(void);

  public:
    /**
     * Initialise the game logic
     */
    virtual void initialise(void);
    /**
     * Update the game
     */
    virtual void update(void);
    /**
     * Shut down the game
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
};

#endif // __GAME_H__
