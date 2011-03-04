#include "managers/CameraManager.h"
#include "components/CameraComponent.h"

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
    camera->lookAt(component->lookAt);
    camera->setNearClipDistance(component->nearClipDistance);
    camera->setFarClipDistance(component->farClipDistance);

    // Viewport Setup
    Ogre::Viewport* vp = component->renderTarget->addViewport(camera);
    vp->setBackgroundColour(component->clearColor);

    camera->setAspectRatio(
        Ogre::Real(vp->getActualWidth()) / Ogre::Real(vp->getActualHeight()));

    component->_camera = camera;
  }

  void CameraManager::update() {
  }


  MANAGER_IMPLEMENTATION(Camera)
}
