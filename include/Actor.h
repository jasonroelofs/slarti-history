#ifndef __ACTOR_H__
#define __ACTOR_H__

#include "components/TransformComponent.h"

#include <vector>

namespace components {
  class Component;
}

/**
 * Base class of all objects that go into the scene.
 * This is a minimal data class that can have any number of
 * components which actually define properties and flag the
 * appropriate functionality from Managers.
 */
class Actor {

  public:
    /**
     * Every actor automatically gets a Transform component.
     * We make it directly accessible because in the end this
     * is the most used set of data for an Actor.
     */
    components::TransformComponent* transform;

    /**
     * Add a component to this actor
     */
    void addComponent(components::Component* comp);

  protected:
    /**
     * Actors are composed of Components, which add
     * data to the Actor and flag this Actor for added functionality.
     */
    std::vector<components::Component*> mComponents;

};

#endif //__ACTOR_H__
