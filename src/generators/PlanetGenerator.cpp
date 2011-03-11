#include "PlanetGenerator.h"
#include "Utils.h"

#include "components/MeshComponent.h"

#include <OgreMath.h>

const int PlanetGenerator::MIN_PLANET_SIZE = 2000;
const int PlanetGenerator::MAX_PLANET_SIZE = 10000;

Actor* PlanetGenerator::generatePlanet() { //Ogre::Vector3 sector, Ogre::Vector3 offset, float size) {

  // choose sector to place planet
  // choose offset of sector
  // choose size of planet

  Ogre::Vector3 sector(
    Ogre::Math::RangeRandom(1, 10),
    Ogre::Math::RangeRandom(1, 10),
    Ogre::Math::RangeRandom(1, 10)
  );

  Ogre::Vector3 offset(
    Ogre::Math::UnitRandom(),
    Ogre::Math::UnitRandom(),
    Ogre::Math::UnitRandom()
  );

  int size = Ogre::Math::RangeRandom(MIN_PLANET_SIZE, MAX_PLANET_SIZE);
  
  Actor* planet = new Actor(utils::calculatePosition(sector, offset));
  planet->transform->scale = Ogre::Vector3(size, size, size);
  planet->addComponent(new components::MeshComponent("sphere.mesh", "dirt"));

  return planet;
}
