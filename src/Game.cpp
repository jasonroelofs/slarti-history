#include <Application.h>
#include <QMouseEvent>
#include <QWidget>
#include <QCursor>

#include "Game.h"
#include "Event.h"
#include "QtEventConverter.h"

Game::Game(void)
  : GameLogic()
{
}

void Game::initialise(void)
{
  // Initialize Ogre
  mRoot = Ogre::Root::getSingletonPtr();
  mSceneManager = mRoot->createSceneManager(Ogre::ST_GENERIC);

  mCamera = mSceneManager->createCamera("PlayerCam");

  mCamera->setPosition(Ogre::Vector3(0, 0, 80));
  mCamera->lookAt(Ogre::Vector3(0, 0, -300));
  mCamera->setNearClipDistance(5);

  Ogre::RenderWindow* window = mApplication->ogreRenderWindow();

  Ogre::Viewport* vp = window->addViewport(mCamera);
  vp->setBackgroundColour(Ogre::ColourValue(0, 0, 0));

  mCamera->setAspectRatio(
      Ogre::Real(vp->getActualWidth()) / Ogre::Real(vp->getActualHeight()));

  Ogre::TextureManager::getSingleton().setDefaultNumMipmaps(5);

  // Creating the scene
  Ogre::Entity* ogreHead = mSceneManager->createEntity("Head", "ogrehead.mesh");
  mOgreHeadNode = mSceneManager->getRootSceneNode()->createChildSceneNode();
  mOgreHeadNode->attachObject(ogreHead);

  mSceneManager->setAmbientLight(Ogre::ColourValue(0.5, 0.5, 0.5));

  Ogre::Light* l = mSceneManager->createLight("MainLight");
  l->setPosition(20, 80, 50);

  /**
   * Set up our initial mouse cursor handling
   */
  mApplication->mainWidget()->setMouseTracking(true);
  mApplication->mainWidget()->setCursor( QCursor(Qt::BlankCursor) );
  mApplication->mainWidget()->grabMouse();

  // Set up our input manager and hook up a few keys for ourselves
  mInputManager = new InputManager();

  // Get our camera situated
  mCameraManager = new CameraManager(mCamera, mInputManager);

  // Hook up some top level events
  mInputManager->map(Event::Quit, this, &Game::stop);

  // Set up our clock so we can keep track of time between frames
  mTime.start();
  mTimeOfLastFrame = mTime.elapsed();
}

/**
 * Our handler to shut the game down.
 * Simply tells the Application to shut down, which will then
 * call ::shutdown, where we will clean up all resources
 */
void Game::stop(InputEvent event) {
  mApplication->shutdown();  
}

/**
 * GameLogic Callbacks
 */

void Game::update(void)
{
  unsigned int timeSinceLastFrame = mTime.elapsed() - mTimeOfLastFrame;

  mCameraManager->update(timeSinceLastFrame / 1000.0f);

  mTimeOfLastFrame = mTime.elapsed();
}

/**
 * This method is called via Application::shutdown.
 * To close the game out, please use ::stop instead.
 */
void Game::shutdown(void)
{
  log("SHUTTING DOWN");
  mApplication->mainWidget()->releaseMouse();
}

/**
 * KeyPress Handling. The following are Qt events we use
 * to keep an internal mapped state of the input system
 */

void Game::onKeyPress(QKeyEvent* event)
{
  log("ON KEY PRESS EVENT!");
  mInputManager->injectKeyDown(QtEventConverter::convert(event));
}

void Game::onKeyRelease(QKeyEvent* event)
{
  log("ON KEY RELEASE EVENT!");
  mInputManager->injectKeyUp(QtEventConverter::convert(event));
}

void Game::onMousePress(QMouseEvent* event)
{
  log("ON MOUSE PRESS EVENT!");
}

void Game::onMouseRelease(QMouseEvent* event)
{
  log("ON MOUSE RELEASE EVENT!");
}

void Game::onMouseDoubleClick(QMouseEvent* event)
{
  log("ON MOUSE DOUBLE CLICK EVENT!");
}

void Game::onMouseMove(QMouseEvent* event)
{
  log("ON MOUSE MOVE EVENT!");
  mInputManager->injectMouseMoved(QtEventConverter::convert(event));
  //mCamera->yaw(Ogre::Degree(-event->x() * 0.0015f));
  //mCamera->pitch(Ogre::Degree(-event->y() * 0.0015f));
}

void Game::onWheel(QWheelEvent* event)
{
  log("ON MOUSE WHEEL EVENT!");
}
