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
    camera->lookAt(component->lookAt);
    camera->setNearClipDistance(component->nearClipDistance);
    camera->setFarClipDistance(component->farClipDistance);

    // Viewport Setup
    Ogre::Viewport* vp = component->renderTarget->addViewport(camera);
    vp->setBackgroundColour(component->clearColor);

    camera->setAspectRatio(
        Ogre::Real(vp->getActualWidth()) / Ogre::Real(vp->getActualHeight()));

    component->_actor->transform->_sceneNode->attachObject(camera);
    component->_camera = camera;
  }

  void CameraManager::remove(CameraComponent* component) {
  }

  void CameraManager::update(float timeSinceLastFrame) {
    /*
    CameraComponent* component;
    ComponentIterator it = mComponents.begin();
    ComponentIterator end = mComponents.end();
    Ogre::Vector3 pos;
    Ogre::Quaternion rotation;

    for(; it < end; it++) {
      component = *it;
      pos = component->_actor->transform->position;
      rotation = component->_actor->transform->rotation;

      if(component->_camera->getPosition() != pos) {
        component->_camera->setPosition(pos);
        //component->_camera->setOrientation(rotation);
      }
    }
    */
  }


  MANAGER_IMPLEMENTATION(Camera)
}
