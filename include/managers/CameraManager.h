#ifndef __CAMERA_MANAGER_H__
#define __CAMERA_MANAGER_H__

#include "components/CameraComponent.h"

#include <vector>

namespace Ogre {
  class SceneManager; 
}

namespace managers {
  using namespace components;

  /**
   * Manager class that handles all CameraComponents.
   * This class will setup, hook up, and update all Cameras
   * with the Ogre subsystem.
   */
  class CameraManager { // : public ComponentManager<CameraComponent>

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
       * Update all known Cameras
       */
      void update();

    protected:

      /**
       * Current active scene manager
       */
      Ogre::SceneManager* mSceneManager;

      /**
       * List of all CameraComponents currently in
       * the system
       */
      std::vector<CameraComponent*> mComponents;

  };
}
#endif // __CAMERA_MANAGER_H__
