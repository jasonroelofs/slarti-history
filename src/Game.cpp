#include "Game.h"
#include "Event.h"

#if OGRE_PLATFORM == OGRE_PLATFORM_APPLE
#include <macUtils.h>
#endif

Game::Game() 
  : mShutdown(false)
{
}

Game::~Game() 
{
  //Remove ourself as a Window listener
  Ogre::WindowEventUtilities::removeWindowEventListener(mWindow, this);
  windowClosed(mWindow);
  delete mRoot;
}

void Game::go()
{
  if(!setup()) {
    return;
  }

  mRoot->startRendering();

  // destroyScene();
}

bool Game::setup() {
  Ogre::String resourcesCfg, pluginsCfg;

#if OGRE_PLATFORM == OGRE_PLATFORM_APPLE
  resourcesCfg = Ogre::macBundlePath() + "/Contents/Resources/resources.cfg";
  pluginsCfg = Ogre::macBundlePath() + "/Contents/Resources/plugins.cfg";
#else
  resourcesCfg = "resources.cfg";
  pluginsCfg = "plugins.cfg";
#endif

  mRoot = new Ogre::Root(pluginsCfg);

  //----------------------
  // Load Resources Config
  //----------------------
  {
    // Load resource paths from config file
    Ogre::ConfigFile cf;
    cf.load(resourcesCfg);

    // Go through all sections & settings in the file
    Ogre::ConfigFile::SectionIterator seci = cf.getSectionIterator();

    Ogre::String secName, typeName, archName;
    while (seci.hasMoreElements())
    {
      secName = seci.peekNextKey();
      Ogre::ConfigFile::SettingsMultiMap *settings = seci.getNext();
      Ogre::ConfigFile::SettingsMultiMap::iterator i;
      for (i = settings->begin(); i != settings->end(); ++i)
      {
        typeName = i->first;
        archName = i->second;
        Ogre::ResourceGroupManager::getSingleton().addResourceLocation(
            archName, typeName, secName);
      }
    }
  }

  //----------------------
  // Show Config Dialog
  //----------------------
  if(mRoot->showConfigDialog()) {
    mWindow = mRoot->initialise(true, "Slartibartfast");
  } else {
    return false;
  }

  //----------------------
  // Scene Manager Setup  
  //----------------------
  {
    mSceneManager = mRoot->createSceneManager(Ogre::ST_GENERIC);

    mCamera = mSceneManager->createCamera("PlayerCam");

    mCamera->setPosition(Ogre::Vector3(0, 0, 80));
    mCamera->lookAt(Ogre::Vector3(0, 0, -300));
    mCamera->setNearClipDistance(5);
  }


  //----------------------
  // Viewport Setup
  //----------------------
  {
    Ogre::Viewport* vp = mWindow->addViewport(mCamera);
    vp->setBackgroundColour(Ogre::ColourValue(0, 0, 0));

    mCamera->setAspectRatio(
        Ogre::Real(vp->getActualWidth()) / Ogre::Real(vp->getActualHeight()));
  }


  //----------------------
  // Initialise Resources
  //----------------------
  {
    Ogre::TextureManager::getSingleton().setDefaultNumMipmaps(5);
    Ogre::ResourceGroupManager::getSingleton().initialiseAllResourceGroups();
  }

  //----------------------
  // Creating the scene
  //----------------------
  {
    Ogre::Entity* ogreHead = mSceneManager->createEntity("Head", "ogrehead.mesh");
    mOgreHeadNode = mSceneManager->getRootSceneNode()->createChildSceneNode();
    mOgreHeadNode->attachObject(ogreHead);

    mSceneManager->setAmbientLight(Ogre::ColourValue(0.5, 0.5, 0.5));

    Ogre::Light* l = mSceneManager->createLight("MainLight");
    l->setPosition(20, 80, 50);
  }

  //----------------------
  // Initialize OIS
  //----------------------
  {
    OIS::ParamList pl;
    size_t windowHnd = 0;
    std::ostringstream windowHndStr;

    mWindow->getCustomAttribute("WINDOW", &windowHnd);
    windowHndStr << windowHnd;
    pl.insert(std::make_pair(std::string("WINDOW"), windowHndStr.str()));

    mOISInputManager = OIS::InputManager::createInputSystem( pl );

    mKeyboard = static_cast<OIS::Keyboard*>(mOISInputManager->createInputObject( OIS::OISKeyboard, true ));
    mMouse = static_cast<OIS::Mouse*>(mOISInputManager->createInputObject( OIS::OISMouse, true ));
  }

  //----------------------
  // Set up listeners
  //----------------------
  {
    mRoot->addFrameListener(this);

    //Set initial mouse clipping size
    windowResized(mWindow);

    //Register as a Window listener
    Ogre::WindowEventUtilities::addWindowEventListener(mWindow, this);

    // Set up our input manager and hook up a few keys for ourselves
    mInputManager = new InputManager();

    // Get our dispatcher up and running
    mInputDispatcher = new InputDispatcher();

    mMouse->setEventCallback(mInputDispatcher);
    mKeyboard->setEventCallback(mInputDispatcher);

    // Hook up our input manager as the current
    mInputDispatcher->setCurrentInputManager(mInputManager);

    // Get our camera situated
    mCameraManager = new CameraManager(mCamera, mInputManager);

    // Hook up some top level events
    mInputManager->map(Event::Quit, this, &Game::stop);
  }

  return true;
}

/**
 * Our handler to shut the game down.
 * Simply tells the Application to shut down, which will then
 * call ::shutdown, where we will clean up all resources
 */
void Game::stop(InputEvent event) {
  mShutdown = true;
}

bool Game::frameRenderingQueued(const Ogre::FrameEvent& evt) {

  // Check shutdown state
  if(mWindow->isClosed() || mShutdown) {
    return false;
  }

  // Capture input
  mKeyboard->capture();
  mMouse->capture();

  mCameraManager->update(evt.timeSinceLastFrame);

  return true;
}

//Adjust mouse clipping area
void Game::windowResized(Ogre::RenderWindow* rw)
{
    unsigned int width, height, depth;
    int left, top;
    rw->getMetrics(width, height, depth, left, top);

    const OIS::MouseState &ms = mMouse->getMouseState();
    ms.width = width;
    ms.height = height;
}

//Unattach OIS before window shutdown (very important under Linux)
void Game::windowClosed(Ogre::RenderWindow* rw)
{
    //Only close for window that created OIS (the main window in these demos)
    if( rw == mWindow )
    {
        if( mOISInputManager )
        {
            mOISInputManager->destroyInputObject( mMouse );
            mOISInputManager->destroyInputObject( mKeyboard );

            OIS::InputManager::destroyInputSystem(mOISInputManager);
            mOISInputManager = 0;
        }
    }
}
