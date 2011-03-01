//#ifndef __CAMERA_MANAGER_H__
//#define __CAMERA_MANAGER_H__
//
//#include <Ogre.h>
//
//#include "InputManager.h"
//#include "Event.h"
//
///**
// * A CameraManager controls and manages a Camera
// */
//class CameraManager {
//  public:
//    /**
//     * Hook up a new manager with a camera and input
//     */
//    CameraManager(Ogre::Camera* camera, InputManager *input);
//
//    /**
//     * Update handler, called once a frame.
//     */
//    void update(float timeSinceLastFrame);
//
//    /**
//     * Camera movement methods.
//     * Move left / right are strafing
//     * Move forward / back are self defining
//     *
//     * These methods actually set a "is moving" flag as
//     * the key inputs only come in when key is down or a key
//     * is released.
//     */
//
//    void moveLeft(InputEvent event);
//    void moveRight(InputEvent event);
//    void moveForward(InputEvent event);
//    void moveBack(InputEvent event);
//
//    /**
//     * Rotating the view according to the mouse movement
//     */
//    void rotateView(InputEvent event);
//
//  private:
//    Ogre::Camera* mCamera;
//    InputManager* mInput;
//
//    // Which direction are we moving?
//    bool mMovingLeft;
//    bool mMovingRight;
//    bool mMovingForward;
//    bool mMovingBack;
//
//    Ogre::Real mTopSpeed;
//
//    // Keep track of our current state
//    Ogre::Vector3 mVelocity;
//};
//
//#endif // __CAMERA_MANAGER_H__
