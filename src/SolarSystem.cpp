#include "SolarSystem.h"

#include "components/MeshComponent.h"

SolarSystem::SolarSystem() {
  mActor = new Actor();
}

void SolarSystem::generate() {
  chooseSunType();
  generatePlanets();
}

void SolarSystem::chooseSunType() {
  Actor* sun = new Actor(Ogre::Vector3(0, 0, 0));
  sun->transform->scale = Ogre::Vector3(100000, 100000, 100000);
  sun->addComponent(new components::MeshComponent("sphere.mesh"));
}

void SolarSystem::generatePlanets() {
  generatePlanet();
}

void SolarSystem::generatePlanet() {
  Actor* planet = new Actor(Ogre::Vector3(0, 0, 120000));
  planet->transform->scale = Ogre::Vector3(2000, 2000, 2000);
  planet->addComponent(new components::MeshComponent("sphere.mesh", "dirt"));
}
