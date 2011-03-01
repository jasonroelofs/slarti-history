#ifndef __CAMERA_COMPONENT_H__
#define __CAMERA_COMPONENT_H__

#include "components/Component.h"

#include <OgreVector3.h>
#include <OgreColourValue.h>

namespace Ogre {
  class Camera;
  class RenderTarget;
}

namespace components {

  /**
   * Component that defines information related to being
   * a camera viewing the scene
   */
  class CameraComponent : public Component {

    public:
      /**
       * Where the camera is looking at
       */
      Ogre::Vector3 lookAt;

      /**
       * View frustum clip distances
       */
      int nearClipDistance; // = 1;
      int farClipDistance; // = 0;

      /**
       * Color the framebuffer will get cleared to on
       * each frame
       */
      Ogre::ColourValue clearColor;

      /**
       * Render target this camera is rendering to
       */
      Ogre::RenderTarget* renderTarget;

      /** Internal **/

      /**
       * Ogre's camera object
       */
      Ogre::Camera* _camera;

  };

}
#endif // __CAMERA_COMPONENT_H__
