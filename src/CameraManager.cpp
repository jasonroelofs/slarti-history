#include <limits>

#include "CameraManager.h"

CameraManager::CameraManager(Ogre::Camera* camera, InputManager* manager) 
  : mCamera(camera), 
    mInput(manager),
    mMovingForward(false),
    mMovingBack(false),
    mMovingRight(false),
    mMovingLeft(false),
    mTopSpeed(50),
    mVelocity(Ogre::Vector3::ZERO)
{
  mInput->map(Event::MoveLeft, this, &CameraManager::moveLeft);
  mInput->map(Event::MoveRight, this, &CameraManager::moveRight);
  mInput->map(Event::MoveForward, this, &CameraManager::moveForward);
  mInput->map(Event::MoveBack, this, &CameraManager::moveBack);
}

// Copying logic used in SdkCameraMan
void CameraManager::update(float timeSinceLastFrame) {
  Ogre::Vector3 accel = Ogre::Vector3::ZERO;
  if (mMovingForward) accel += mCamera->getDirection();
  if (mMovingBack) accel -= mCamera->getDirection();
  if (mMovingRight) accel += mCamera->getRight();
  if (mMovingLeft) accel -= mCamera->getRight();

  // if accelerating, try to reach top speed in a certain time
  Ogre::Real topSpeed = mTopSpeed * 20;

  if (accel.squaredLength() != 0) {
    accel.normalise();
    mVelocity += accel * topSpeed * timeSinceLastFrame * 10;
  }
  // if not accelerating, try to stop in a certain time
  else {
    mVelocity -= mVelocity * timeSinceLastFrame * 10;
  }

  Ogre::Real tooSmall = std::numeric_limits<Ogre::Real>::epsilon();

  // keep camera velocity below top speed and above epsilon
  if (mVelocity.squaredLength() > topSpeed * topSpeed)
  {
    mVelocity.normalise();
    mVelocity *= topSpeed;
  }
  else if (mVelocity.squaredLength() < tooSmall * tooSmall) {
    mVelocity = Ogre::Vector3::ZERO;
  }

  if (mVelocity != Ogre::Vector3::ZERO) {
    mCamera->move(mVelocity * timeSinceLastFrame);
  }
}

void CameraManager::moveLeft(bool down) {
  mMovingLeft = down;
}

void CameraManager::moveRight(bool down) {
  mMovingRight = down;
}

void CameraManager::moveForward(bool down) {
  mMovingForward = down;
}

void CameraManager::moveBack(bool down) {
  mMovingBack = down;
}
