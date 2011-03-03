#include "Actor.h"
#include "components/TransformComponent.h"

Actor::Actor(Ogre::Vector3 position) {
  transform = new components::TransformComponent(position);
}

void Actor::addComponent(components::Component* comp) {
  comp->_actor = this;

  mComponents.push_back(comp);
}
