#include "StarGenerator.h"
#include "components/MeshComponent.h"

#include <OgreVector3.h>

Actor* StarGenerator::generateStar() {
  Actor* sun = new Actor(Ogre::Vector3(0, 0, 0));
  sun->transform->scale = Ogre::Vector3(100000, 100000, 100000);
  sun->addComponent(new components::MeshComponent("sphere.mesh"));
  return sun;
}
