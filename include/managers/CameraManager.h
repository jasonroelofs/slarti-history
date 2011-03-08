#ifndef __CAMERA_MANAGER_H__
#define __CAMERA_MANAGER_H__

#include "managers/ComponentManager.h"

namespace Ogre {
  class SceneManager; 
}

namespace components {
  class CameraComponent;
}

namespace managers {
  using namespace components;

  /**
   * Manager class that handles all CameraComponents.
   * This class will setup, hook up, and update all Cameras
   * with the Ogre subsystem.
   */
  class CameraManager {

    public:

      /**
       * Initialize ourselves with the scene manager of choice
       */
      CameraManager(Ogre::SceneManager* manager);

      /**
       * We've got a new Camera component, initialize
       * it with Ogre and the system as a whole.
       */
      void initialize(CameraComponent* camera);

      /**
       * When a component is removed from the system, 
       * allow the manager to clean up before destruction
       */
      void remove(CameraComponent* component);

      /**
       * Update all known Cameras
       */
      void update(float timeSinceLastFrame);

    protected:

      /**
       * Current active scene manager
       */
      Ogre::SceneManager* mSceneManager;


    MANAGER_DEFINITION(Camera)
  };
}
#endif // __CAMERA_MANAGER_H__
