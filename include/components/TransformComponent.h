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
      TransformComponent(Ogre::Vector3 position = Ogre::Vector3::ZERO) 
        : position(position)
      {
 //       managers::TransformManager::getInstance()->_registerComponent(this);

        rotation = Ogre::Quaternion::ZERO;
        scale = Ogre::Vector3::UNIT_SCALE;
      }

      ~TransformComponent() {
//        managers::TransformManager::getInstance()->_unregisterComponent(this);
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
