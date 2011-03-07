#include "Actor.h"
#include "components/TransformComponent.h"

Actor::Actor(Ogre::Vector3 position) {
  transform = new components::TransformComponent(position);
  addComponent(transform);
}

void Actor::addComponent(components::Component* comp) {
  comp->_actor = this;
  mComponents.push_back(comp);

  // Make sure the component registers with it's manager
  comp->_register();
}
