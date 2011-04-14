#include "Game.h"
#include "Event.h"
#include "Actor.h"
#include "Utils.h"

#include "managers/TransformManager.h"
#include "managers/CameraManager.h"
#include "managers/InputManager.h"
#include "managers/MeshManager.h"
#include "managers/ShipManager.h"

#include "components/CameraComponent.h"
#include "components/ShipComponent.h"
#include "components/ShipInputComponent.h"
#include "components/MouseLookComponent.h"
#include "components/MovementComponent.h"
#include "components/MeshComponent.h"

#include "procedural/ProceduralRoot.h"
#include "procedural/ProceduralBoxGenerator.h"

#include "PrefabManager.h"

#include <cstdio>
#include <ctime>

#include <iostream>
using namespace std;

#if OGRE_PLATFORM == OGRE_PLATFORM_APPLE
#include <macUtils.h>
#endif

Game::Game() 
  : mShutdown(false)
{
  // Initialize the random number generator
  srand( time(NULL) );
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
  // Scene Manager and Camera Setup  
  //----------------------
  {
    mSceneManager = mRoot->createSceneManager(Ogre::ST_EXTERIOR_CLOSE, "SceneManager");

    new managers::TransformManager(mSceneManager);
    new managers::CameraManager(mSceneManager);
    new managers::MeshManager(mSceneManager);
    new managers::ShipManager();

    Procedural::Root::getInstance()->sceneManager = mSceneManager;

    mInputManager = new managers::InputManager();

    // Prototype for setting up an actor with components.
    // Want to move this stuff into managers more, or factories

    mActor = new Actor(Ogre::Vector3(10, 2, 0));
    mActor->addComponent(new components::CameraComponent(mWindow));

    /*
    mShip = new components::ShipComponent();
    mActor->addComponent(mShip);
    mActor->addComponent(new components::ShipInputComponent(mShip));
    */

    mActor->addComponent(new components::MouseLookComponent());
    mActor->addComponent(new components::MovementComponent());
  }


  //----------------------
  // Initialise Resources
  //----------------------
  {
    Ogre::TextureManager::getSingleton().setDefaultNumMipmaps(5);
    Ogre::ResourceGroupManager::getSingleton().initialiseAllResourceGroups();

	  //mRoot->addMovableObjectFactory(new SurfacePatchRenderableFactory);
  }

  //----------------------
  // Creating the scene
  //----------------------
  {
    //Actor* ogreHead = new Actor();
    //ogreHead->addComponent(new components::MeshComponent("ogrehead.mesh"));

    mSceneManager->setSkyBox(true, "SpaceSkyBox", 5000);

    // TODO: This should be an Actor + LightComponent
    //Ogre::Light* l = mSceneManager->createLight("MainLight");
    //l->setPosition(Ogre::Vector3(20, 80, 50.0f)); //mCamera->getPosition());
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

    // Get our dispatcher up and running
    mInputDispatcher = new InputDispatcher();

    mMouse->setEventCallback(mInputDispatcher);
    mKeyboard->setEventCallback(mInputDispatcher);

    // Hook up our input manager as the current
    mInputDispatcher->setCurrentInputManager(mInputManager);

    // Hook up some top level events
    mInputManager->map(Event::Quit, this, &Game::stop);

    // Polygon rendering : Move to another Input component, add to Camera
    mInputManager->map(Event::ToggleWireframe, this, &Game::toggleWireframe);
  }

  //---------------------------
  // Initialize the Interface
  //---------------------------
  {
    mUIManager = new ui::UIManager(mWindow);

    mSceneManager->addRenderQueueListener(mUIManager);
  }

  //--------------------------
  // Load up custom resources
  //--------------------------
  {
    new PrefabManager();
    PrefabManager::getInstance()->loadAllPrefabs(); 
  }

  // Initialize a Level, generate, and render
  /*
  {
    mLevel = new Level(mSceneManager);
    mLevel->generate();

    // Hook up an event to allow us to rebuild with a simple keystroke
    mInputManager->map(Event::RebuildLevel, this, &Game::newLevel);
  }
  */

  // Planet rendering
  {
    //mSolarSystem = new SolarSystem();
    //mSolarSystem->generate();

    // Hook up an event to allow us to rebuild with a simple keystroke
    //mInputManager->map(Event::RebuildLevel, this, &Game::newLevel);
  }

  // Station building
  {
    Procedural::BoxGenerator().
      setSizeX(50.0f).
      setSizeY(0.5f).
      setSizeZ(50.0f).
      setNumSegX(50).
      setNumSegZ(50).
      realizeMesh("panel");

    Procedural::BoxGenerator().
      setSizeX(1.0f).
      setSizeY(0.5f).
      setSizeZ(1.0f).
      setNumSegX(1).
      setNumSegZ(1).
      realizeMesh("singlepanel");

    Ogre::SceneNode* station = mSceneManager->getRootSceneNode()->createChildSceneNode();

    // Floor
    Ogre::Entity* floor = mSceneManager->createEntity("panel");
    floor->setMaterialName("Station/Metal");

    Ogre::SceneNode* floorNode = station->createChildSceneNode();
    floorNode->attachObject(floor);

    // Ceiling
    Ogre::Entity* ceiling = mSceneManager->createEntity("panel");
    ceiling->setMaterialName("Station/Metal");

    Ogre::SceneNode* ceilingNode = station->createChildSceneNode();
    ceilingNode->setPosition(0, 10, 0);
    ceilingNode->attachObject(ceiling);

    // East Wall
    Ogre::Entity* eastWall = mSceneManager->createEntity("panel");
    eastWall->setMaterialName("Station/Metal");

    Ogre::SceneNode* eastWallNode = station->createChildSceneNode();
    eastWallNode->setPosition(-25, 0, 0);
    eastWallNode->roll(Ogre::Degree(90));
    eastWallNode->attachObject(eastWall);

    // West Wall
    Ogre::Entity* westWall = mSceneManager->createEntity("panel");
    westWall->setMaterialName("Station/Metal");

    Ogre::SceneNode* westWallNode = station->createChildSceneNode();
    westWallNode->setPosition(25, 0, 0);
    westWallNode->roll(Ogre::Degree(90));
    westWallNode->attachObject(westWall);
  }

  // Build our Galaxy
  // Build our various game states (only Play right now, later Menu and possibly Loading)
  //   - Send self and Galaxy to Play State
  // 
  // PlayState builds new input manager, hooks up for player interaction
  //   - calls Game.useInputManager, which forwards to InputDispatcher
  //
  // Game tells PlayState to initialize
  //
  // Game starts rendering loop
  //
  // Each frame, 
  //   - Possibly send update to Galaxy, before everything else
  //   - Send update to all states
  //
  // PlayState sets up PlayerController, gets hooked up with input interactions, camera, etc
  //
  // PlayState sets user in starting sector (from Galaxy) and sets player free
  //
  // Where does the connection between where the user is and rendering the galaxy fit in? 
  // Learn about PCZSM. Zones can be Sectors, inside planets, levels in planets, or even more fine grained
  // like rooms in levels

  return true;
}

void Game::newLevel(InputEvent event) {
  // Process on key-up only
  if(!event.isDown) {
    mSolarSystem->generate();
  }
}

// Need to move this to CameraManager?
void Game::toggleWireframe(InputEvent event) {
  if(event.isDown) {
    Ogre::PolygonMode pm;

    mCamera = mSceneManager->getCamera("PlayerCam");

    switch (mCamera->getPolygonMode())
    {
    case Ogre::PM_SOLID:
      pm = Ogre::PM_WIREFRAME;
      break;
    default:
      pm = Ogre::PM_SOLID;
    }

    mCamera->setPolygonMode(pm);
  }
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

  // Update our managers in order
  managers::ShipManager::getInstance()->update(evt.timeSinceLastFrame);
  managers::MeshManager::getInstance()->update(evt.timeSinceLastFrame);

  managers::TransformManager::getInstance()->update(evt.timeSinceLastFrame);
  managers::CameraManager::getInstance()->update(evt.timeSinceLastFrame);

  // Potentially look at updating the UI at a slower rate than raw FPS
  //mUIManager->update(evt.timeSinceLastFrame);

  mUIManager->updatePosition(mActor->transform->position);
  //mUIManager->updateVelocity(mShip->velocity);
  //mUIManager->updateAcceleration(mShip->acceleration);
  //mUIManager->updateCruise(mShip->cruiseCharge());

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
