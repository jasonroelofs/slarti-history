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
  // int numStars = 1;

  mStars.push_back(StarGenerator::generateStar());
}

void SolarSystem::generatePlanets() {
  int numPlanets = Ogre::Math::RangeRandom(1, 6);
  Actor* planet;

  for(int i = 0; i < numPlanets; i++) {
    planet = PlanetGenerator::generatePlanet();

    cout << "Planet generated at " << planet->transform->position << endl;

    mPlanets.push_back(planet);
  }
}
