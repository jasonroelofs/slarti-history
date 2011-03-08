#ifndef __MESH_MANAGER_H__
#define __MESH_MANAGER_H__

#include "managers/ComponentManager.h"

namespace Ogre {
  class SceneManager; 
}

namespace components {
  class MeshComponent;
}

namespace managers {
  using namespace components;

  /**
   * Manager class that handles all MeshComponents.
   * Meshs are .mesh files loaded by Ogre and used to give a visual
   * representation of an Actor.
   */
  class MeshManager {

    public:

      /**
       * Initialize ourselves with the scene manager of choice
       */
      MeshManager(Ogre::SceneManager* manager);

      /**
       * We've got a new Mesh component, initialize
       * it with Ogre and the system as a whole.
       */
      void initialize(MeshComponent* camera);

      /**
       * When a component is removed from the system, 
       * allow the manager to clean up before destruction
       */
      void remove(MeshComponent* component);

      /**
       * Update all known Meshs
       */
      void update(float timeSinceLastFrame);

    protected:

      /**
       * Current active scene manager
       */
      Ogre::SceneManager* mSceneManager;


    MANAGER_DEFINITION(Mesh)
  };
}
#endif // __MESH_MANAGER_H__
