#include "managers/CameraManager.h"
#include "components/CameraComponent.h"
#include "components/TransformComponent.h"
#include "Actor.h"

#include <OgreQuaternion.h>
#include <OgreSceneManager.h>
#include <OgreViewport.h>

namespace managers {

  CameraManager::CameraManager(Ogre::SceneManager* manager)
    : mSceneManager(manager)
  {
    msInstance = this;
  }

  void CameraManager::initialize(CameraComponent* component) {
    mComponents.push_back(component);

    Ogre::Camera* camera = mSceneManager->createCamera("PlayerCam");

    // View configuration
    camera->setNearClipDistance(component->nearClipDistance);
    camera->setFarClipDistance(component->farClipDistance);

    // Viewport Setup
    Ogre::Viewport* vp = component->renderTarget->addViewport(camera);
    vp->setBackgroundColour(component->clearColor);

    camera->setAspectRatio(
        Ogre::Real(vp->getActualWidth()) / Ogre::Real(vp->getActualHeight()));

    // As the camera is getting attached to this actor's scene node,
    // we need to set the scene node to the initial orientation
    // of this camera, otherwise things don't work.
    components::TransformComponent* transform = component->_actor->transform;
    transform->_sceneNode->setFixedYawAxis(transform->fixedYaw);
    transform->_sceneNode->lookAt(component->lookAt, Ogre::Node::TS_WORLD);
    transform->rotation = transform->_sceneNode->getOrientation(); 

    transform->_sceneNode->attachObject(camera);

    component->_camera = camera;
  }

  void CameraManager::remove(CameraComponent* component) {
  }

  void CameraManager::update(float timeSinceLastFrame) {
  }


  MANAGER_IMPLEMENTATION(Camera)
}
