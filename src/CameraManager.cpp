#include <limits>

#include <iostream>
using namespace std;

#include "CameraManager.h"

CameraManager::CameraManager(Ogre::Camera* camera, InputManager* manager) 
  : mCamera(camera), 
    mInput(manager),
    mMovingLeft(false),
    mMovingRight(false),
    mMovingForward(false),
    mMovingBack(false),
    mTopSpeed(1),
    mVelocity(Ogre::Vector3::ZERO)
{
  mInput->map(Event::MoveLeft, this, &CameraManager::moveLeft);
  mInput->map(Event::MoveRight, this, &CameraManager::moveRight);
  mInput->map(Event::MoveForward, this, &CameraManager::moveForward);
  mInput->map(Event::MoveBack, this, &CameraManager::moveBack);

  mInput->map(Event::MouseMoved, this, &CameraManager::rotateView);
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

void CameraManager::rotateView(InputEvent event) {
  mCamera->yaw(Ogre::Degree(-event.xDiff * 0.30f));
  mCamera->pitch(Ogre::Degree(-event.yDiff * 0.30f));
}

void CameraManager::moveLeft(InputEvent event) {
  mMovingLeft = event.isDown;
}

void CameraManager::moveRight(InputEvent event) {
  mMovingRight = event.isDown;
}

void CameraManager::moveForward(InputEvent event) {
  mMovingForward = event.isDown;
}

void CameraManager::moveBack(InputEvent event) {
  mMovingBack = event.isDown;
}
