#ifndef __TRANSFORM_MANAGER_H__
#define __TRANSFORM_MANAGER_H__

#include "managers/ComponentManager.h"

namespace Ogre {
  class SceneManager;
}

namespace components {
  class TransformComponent;
}

namespace managers {
  using namespace components;

  /**
   * Manager for all Transform components, and thus
   * all Actors. Deals with translating data from Actors
   * to Ogre::SceneNodes and back.
   *
   * May just make this the top level Actor management class.
   */
  class TransformManager {
    public:

      /**
       * Needs to know about the current scene manager
       */
      TransformManager(Ogre::SceneManager* manager);

      /**
       * We've got a new component, initialize
       * it with Ogre and the system as a whole.
       */
      void initialize(TransformComponent* component);

      /**
       * Update all actors
       */
      void update();

    protected:

      /**
       * Current active scene manager
       */
      Ogre::SceneManager* mSceneManager;


    MANAGER_DEFINITION(Transform)
  };

}
#endif // __TRANSFORM_MANAGER_H__

