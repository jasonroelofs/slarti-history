#include "Actor.h"

void Actor::addComponent(components::Component* comp) {
  mComponents.push_back(comp);
}
