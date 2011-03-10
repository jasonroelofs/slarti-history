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
  Actor* planet = new Actor(Ogre::Vector3(0, 0, 7500000));
  planet->transform->scale = Ogre::Vector3(2000, 2000, 2000);
  planet->addComponent(new components::MeshComponent("sphere.mesh", "dirt"));

  // Reference planets, made super huge so I can see them
  Actor* planet2 = new Actor(Ogre::Vector3(7500000, 0, 0));
  planet2->transform->scale = Ogre::Vector3(80000, 80000, 80000);
  planet2->addComponent(new components::MeshComponent("sphere.mesh", "dirt"));

  Actor* planet3 = new Actor(Ogre::Vector3(0, 7500000, 0));
  planet3->transform->scale = Ogre::Vector3(100000, 100000, 100000);
  planet3->addComponent(new components::MeshComponent("sphere.mesh", "dirt"));

  Actor* planet4 = new Actor(Ogre::Vector3(0, 0, -7500000));
  planet4->transform->scale = Ogre::Vector3(2000, 2000, 2000);
  planet4->addComponent(new components::MeshComponent("sphere.mesh", "dirt"));

  Actor* planet5 = new Actor(Ogre::Vector3(-7500000, 0, 0));
  planet5->transform->scale = Ogre::Vector3(80000, 80000, 80000);
  planet5->addComponent(new components::MeshComponent("sphere.mesh", "dirt"));

  Actor* planet6 = new Actor(Ogre::Vector3(0, -7500000, 0));
  planet6->transform->scale = Ogre::Vector3(100000, 100000, 100000);
  planet6->addComponent(new components::MeshComponent("sphere.mesh", "dirt"));
}
