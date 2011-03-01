#ifndef __TRANSFORM_COMPONENT_H__
#define __TRANSFORM_COMPONENT_H__

#include "components/Component.h"

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
       * Position in the world
       */
      Ogre::Vector3 position; // = Ogre::Vector3::ZERO;

      /**
       * Rotation
       */
      Ogre::Quaternion rotation; // = Ogre::Quaternion::ZERO;

      /**
       * Scale of object, in 3 dimensions
       */
      Ogre::Vector3 scale; // = Ogre::Vector3::UNIT_SCALE;

      /** Internal **/

      Ogre::SceneNode* _sceneNode;


  };
}
#endif // __TRANSFORM_COMPONENT_H__
