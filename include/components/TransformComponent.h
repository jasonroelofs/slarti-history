#ifndef __TRANSFORM_COMPONENT_H__
#define __TRANSFORM_COMPONENT_H__

#include "components/Component.h"
#include "managers/TransformManager.h"

#include <OgreVector3.h>
#include <OgreMath.h>
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
        : position(position),
          movingForward(false),
          movingBack(false),
          movingLeft(false),
          movingRight(false),
          moveRelativeToRotation(false)
      {
        rotation = Ogre::Quaternion::ZERO;
        scale = Ogre::Vector3::UNIT_SCALE;
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

      /**
       * State change flags
       */
      bool movingForward, movingBack, movingLeft, movingRight;

      /**
       * Should the next update move this transform relative to
       * any set orientation or should it just move directly with
       * axes?
       */
      bool moveRelativeToRotation;

      /**
       * Rotating with euler methods.
       * Ripped from how Ogre::Camera works.
       */
      void yaw(const Ogre::Degree& angle) {
        Ogre::Vector3 yAxis = rotation * Ogre::Vector3::UNIT_Y;
        rotate(yAxis, angle);
      }

      void pitch(const Ogre::Degree& angle) {
        Ogre::Vector3 xAxis = rotation * Ogre::Vector3::UNIT_X;
        rotate(xAxis, angle);
      }

      void rotate(const Ogre::Vector3& axis, const Ogre::Radian& angle)
      {
        Ogre::Quaternion q;
        q.FromAngleAxis(angle,axis);
        rotate(q);
      }

      void rotate(const Ogre::Quaternion& quat) {
        Ogre::Quaternion qnorm = quat;
        qnorm.normalise();
        rotation = qnorm * rotation;
      }

      REGISTRATION_WITH(TransformManager)
  };
}
#endif // __TRANSFORM_COMPONENT_H__
