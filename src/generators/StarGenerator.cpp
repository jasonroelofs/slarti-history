#include "StarGenerator.h"
#include "components/MeshComponent.h"

#include <OgreVector3.h>
#include <OgreMath.h>

Actor* StarGenerator::generateStar() {
  Actor* sun = new Actor(Ogre::Vector3(0, 0, 0));
  int scale = Ogre::Math::RangeRandom(50000, 100000);

  sun->transform->scale = Ogre::Vector3(scale, scale, scale);
  sun->addComponent(new components::MeshComponent("sphere.mesh"));
  return sun;
}
