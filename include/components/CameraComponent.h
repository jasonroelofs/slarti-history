#ifndef __CAMERA_COMPONENT_H__
#define __CAMERA_COMPONENT_H__

#include "Component.h"

class Ogre::Vector3;
class Ogre::Camera;
class Ogre::RenderTarget;
class Ogre::ColourValue;

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
    int nearClipDistance = 1;
    int farClipDistance = 0;

    /**
     * Ogre's camera object
     */
    Ogre::Camera* camera;

    /**
     * Render target this camera is rendering to
     */
    Ogre::RenderTarget* renderTarget;

    /**
     * Color the framebuffer will get cleared to on
     * each frame
     */
    Ogre::ColourValue clearColor;
};

#endif // __CAMERA_COMPONENT_H__
