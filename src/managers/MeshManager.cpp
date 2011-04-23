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

    if(!component->_entity) {
      Ogre::Entity* entity = mSceneManager->createEntity(component->_meshName);
      component->_entity = entity;
    }

    if(component->_materialName != "") {
      component->_entity->setMaterialName(component->_materialName);
    }

    Ogre::SceneNode* childNode = component->_actor->transform->_sceneNode->createChildSceneNode();

    childNode->setPosition(component->position);
    childNode->setOrientation(component->rotation);
    childNode->attachObject(component->_entity);
  }

  void MeshManager::remove(MeshComponent* component) {
  }

  void MeshManager::update(float timeSinceLastFrame) {
  }


  MANAGER_IMPLEMENTATION(Mesh)
}
