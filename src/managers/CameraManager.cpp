#include "managers/CameraManager.h"
#include "components/CameraComponent.h"
#include "components/TransformComponent.h"
#include "Actor.h"

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
    
    // hmm, maybe cleaner way of doing this? (accessing other component information)
    // Need to set camera position according to actor this component is added to
    camera->setPosition(component->_actor->transform->position);

    // Viewport Setup
    Ogre::Viewport* vp = component->renderTarget->addViewport(camera);
    vp->setBackgroundColour(component->clearColor);

    camera->setAspectRatio(
        Ogre::Real(vp->getActualWidth()) / Ogre::Real(vp->getActualHeight()));

    component->_camera = camera;
  }

  void CameraManager::remove(CameraComponent* component) {
  }

  void CameraManager::update() {
    CameraComponent* component;
    ComponentIterator it = mComponents.begin();
    ComponentIterator end = mComponents.end();

    for(; it < end; it++) {
      component = *it;
      component->_camera->setPosition(component->_actor->transform->position);
    }
  }


  MANAGER_IMPLEMENTATION(Camera)
}
