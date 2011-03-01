#include "managers/CameraManager.h"

#include <OgreViewport.h>

CameraManager::CameraManager(Ogre::SceneManager* manager)
  : mSceneManager(manager);
{
}

void CameraManager::initialize(CameraComponent* component) {
  mComponents.push_back(component);

  Ogre::Camera* camera = mSceneManager->createCamera("PlayerCam");

  // View configuration
  camera->lookAt(component->lookAt);
  camera->setNearClipDistance(component->nearClipDistance);
  camera->setFarClipDistance(component->farClipDistance);

  // Viewport Setup
  if(component->renderTarget) {
    Ogre::Viewport* vp = component->renderTarget->addViewport(camera);
    vp->setBackgroundColour(camera->clearColor);

    camera->setAspectRatio(
        Ogre::Real(vp->getActualWidth()) / Ogre::Real(vp->getActualHeight()));
  } // Alert if this isn't the case?

  component->camera = camera;
}
