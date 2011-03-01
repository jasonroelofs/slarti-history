#include "SolarSystem.h"
#include "PlanetGenerator.h"
#include "StarGenerator.h"

SolarSystem::SolarSystem(Ogre::SceneManager* manager)
  : mSceneManager(manager)
{
  mBaseSceneNode = mSceneManager->getRootSceneNode()->createChildSceneNode("SolarSystem");
}

void SolarSystem::generate() {

  if(mPlanet) {
    // Do some cleanup here
    mPlanet = NULL;
  }

  chooseSunType();
  generatePlanets();
}

void SolarSystem::chooseSunType() {
  mStar = StarGenerator::generateStar();

  Ogre::SceneNode* sunNode = mBaseSceneNode->createChildSceneNode();
  mStar->attachTo(sunNode);

  // OMG BIG and right in the center
  sunNode->setPosition(0.0f, 0.0f, 0.0f);
  sunNode->setScale(1000, 1000, 1000);
}

void SolarSystem::generatePlanets() {
  generatePlanet();
}

void SolarSystem::generatePlanet() {
  mPlanet = PlanetGenerator::generatePlanet();

  Ogre::SceneNode* planetNode = mBaseSceneNode->createChildSceneNode();
  mPlanet->attachTo(planetNode);

  planetNode->setPosition(0.0f, 0.0f, 2000.0f);
  planetNode->setScale(200, 200, 200);
}
