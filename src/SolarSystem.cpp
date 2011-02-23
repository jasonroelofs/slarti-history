#include "SolarSystem.h"
#include "PlanetGenerator.h"

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

  mPlanet = PlanetGenerator::generatePlanet();

  Ogre::SceneNode* planetNode = mBaseSceneNode->createChildSceneNode();
  mPlanet->attachTo(planetNode);

  mBaseSceneNode->setPosition(0.0f, 0.0f, 0.0f);
  mBaseSceneNode->setScale(10, 10, 10);
}
