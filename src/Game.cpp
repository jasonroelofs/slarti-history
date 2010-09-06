#include "Game.h"
#include <Application.h>

Game::Game(void)
  : GameLogic()
{
}

void Game::initialise(void)
{
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
}

void Game::update(void)
{
  mOgreHeadNode->roll(Ogre::Degree(5));
}

void Game::shutdown(void)
{
}

void Game::onKeyPress(QKeyEvent* event)
{
}

void Game::onKeyRelease(QKeyEvent* event)
{
}

void Game::onMousePress(QMouseEvent* event)
{
}

void Game::onMouseRelease(QMouseEvent* event)
{
}

void Game::onMouseDoubleClick(QMouseEvent* event)
{
}

void Game::onMouseMove(QMouseEvent* event)
{
}

void Game::onWheel(QWheelEvent* event)
{
}
