#include "managers/MeshManager.h"
#include "components/MeshComponent.h"
#include "components/TransformComponent.h"
#include "Actor.h"

#include <OgreSceneManager.h>
#include <OgreEntity.h>
#include <OgreSceneNode.h>

namespace managers {

  MeshManager::MeshManager(Ogre::SceneManager* manager)
    : mSceneManager(manager)
  {
    msInstance = this;
  }

  void MeshManager::initialize(MeshComponent* component) {
    mComponents.push_back(component);

    Ogre::Entity* entity = mSceneManager->createEntity(component->_meshName);

    component->_actor->transform->_sceneNode->attachObject(entity);
    component->_entity = entity;
  }

  void MeshManager::remove(MeshComponent* component) {
  }

  void MeshManager::update(float timeSinceLastFrame) {
  }


  MANAGER_IMPLEMENTATION(Mesh)
}
