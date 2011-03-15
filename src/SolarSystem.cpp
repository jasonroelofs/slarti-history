#include "SolarSystem.h"
#include "generators/PlanetGenerator.h"
#include "generators/StarGenerator.h"
#include "components/MeshComponent.h"

#include <iostream>
using namespace std;

SolarSystem::SolarSystem() {
}

void SolarSystem::generate() {
  generateStars();
  generatePlanets();
}

void SolarSystem::generateStars() {
  mStars.push_back(StarGenerator::generateStar());
}

void SolarSystem::generatePlanets() {
  int numPlanets = Ogre::Math::RangeRandom(0, 12);
  int orbitDiffMin = 200000;
  int orbitDiffMax = 500000;

  Actor* planet;
  int orbit = 100000;
  float angle;

  for(int i = 0; i < numPlanets; i++) {
    planet = PlanetGenerator::generatePlanet();

    orbit += Ogre::Math::RangeRandom(orbitDiffMin, orbitDiffMax);
    angle = Ogre::Math::RangeRandom(0, 360);

    planet->transform->position =
      Ogre::Vector3(
          orbit * Ogre::Math::Cos(Ogre::Degree(angle)),
          Ogre::Math::RangeRandom(-100000, 100000),
          orbit * Ogre::Math::Sin(Ogre::Degree(angle))
          );

    cout << "Planet generated at " << planet->transform->position << endl;

    mPlanets.push_back(planet);
  }
}
