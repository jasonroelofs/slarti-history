#ifndef __MESH_COMPONENT_H__
#define __MESH_COMPONENT_H__

#include "components/Component.h"
#include "managers/MeshManager.h"

#include <string>

namespace Ogre {
  class Entity;
}

namespace components {

  /**
   * Component that defines information related to being
   * a camera viewing the scene
   */
  class MeshComponent : public Component {

    public:
      /**
       * Initialize a new Mesh with the name of the mesh
       * we want to load.
       */
      MeshComponent(std::string meshName, std::string materialName = "") 
        : _meshName(meshName),
          _materialName(materialName)
      {
      }

      /** Internal */

      /**
       * Name of the mesh we need to load and display
       */
      std::string _meshName;

      /**
       * What material to use on this mesh
       */
      std::string _materialName;

      /**
       * Entity instance of this mesh
       */
      Ogre::Entity* _entity;

      REGISTRATION_WITH(MeshManager)
  };

}
#endif // __MESH_COMPONENT_H__