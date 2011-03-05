#ifndef __CAMERA_COMPONENT_H__
#define __CAMERA_COMPONENT_H__

#include "components/Component.h"
#include "managers/CameraManager.h"

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
       * Initialize a new Camera. Needs to have a render target.
       */
      CameraComponent(Ogre::RenderTarget* target) 
        : lookAt(Ogre::Vector3::ZERO),
          nearClipDistance(1),
          farClipDistance(0),
          clearColor(Ogre::ColourValue::Black),
          renderTarget(target)
      {
        REGISTER_WITH(CameraManager)
      }

      ~CameraComponent() {
        UNREGISTER_WITH(CameraManager)
      }

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
