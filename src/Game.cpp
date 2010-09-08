#include "Game.h"
#include <Application.h>
#include <QMouseEvent>
#include <QWidget>

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

  mApplication->mainWidget()->setMouseTracking(true);
}

void Game::update(void)
{
}

void Game::shutdown(void)
{
  log("SHUTTING DOWN");
}

/**
 * KeyPress Handling (once I figure out the freakin window issue)
 *
 * Will keep a hash of key states around, keyed on the key, and set to
 * true / false. The following events will update that hash, and during
 * #update will act on the current state of the events
 */

void Game::onKeyPress(QKeyEvent* event)
{
  log("ON KEY PRESS EVENT!");
}

void Game::onKeyRelease(QKeyEvent* event)
{
  log("ON KEY RELEASE EVENT!");
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
  mCamera->yaw(Ogre::Degree(-event->x() * 0.0015f));
  mCamera->pitch(Ogre::Degree(-event->y() * 0.0015f));
}

void Game::onWheel(QWheelEvent* event)
{
  log("ON MOUSE WHEEL EVENT!");
}
