#ifndef __TRANSFORM_COMPONENT_H__
#define __TRANSFORM_COMPONENT_H__

#include "components/Component.h"
#include "managers/TransformManager.h"

#include <OgreVector3.h>
#include <OgreQuaternion.h>

namespace Ogre {
  class SceneNode;
}

namespace components {
  /**
   * This component handles the position, rotation, and
   * scale of the Actor in the world.
   *
   * This component gets hooked to a Ogre::SceneNode by
   * its manager.
   */
  class TransformComponent : public Component {

    public:
      /**
       * Initialize a new Transform component at a given world position
       */
      TransformComponent(Ogre::Vector3 position = Ogre::Vector3::ZERO) 
        : position(position)
      {
        rotation = Ogre::Quaternion::ZERO;
        scale = Ogre::Vector3::UNIT_SCALE;

        REGISTER_WITH(TransformManager)
      }

      ~TransformComponent() {
        UNREGISTER_WITH(TransformManager)
      }

      /**
       * Position in the world
       */
      Ogre::Vector3 position;

      /**
       * Rotation
       */
      Ogre::Quaternion rotation;

      /**
       * Scale of object, in 3 dimensions
       */
      Ogre::Vector3 scale;

      /** Internal **/

      Ogre::SceneNode* _sceneNode;
  };
}
#endif // __TRANSFORM_COMPONENT_H__
