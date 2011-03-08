#ifndef __ACTOR_H__
#define __ACTOR_H__

#include "components/TransformComponent.h"

#include <vector>

#include <OgreVector3.h>


/**
 * Base class of all objects that go into the scene.
 * This is a minimal data class that can have any number of
 * components which actually define properties and flag the
 * appropriate functionality from Managers.
 */
class Actor {

  public:
    /**
     * Initialize a new Actor at a given position in World Space,
     * defaults to the origin
     */
    Actor(Ogre::Vector3 position = Ogre::Vector3::ZERO);

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

    /**
     * Create an Actor that's a child to this Actor
     */
    Actor* createChild(Ogre::Vector3 position = Ogre::Vector3::ZERO);

    /**
     * Parent actor of this actor
     */
    Actor* parent;

  protected:
    /**
     * Actors are composed of Components, which add
     * data to the Actor and flag this Actor for added functionality.
     */
    std::vector<components::Component*> mComponents;

    /**
     * Actors can have children actors that will inherit properties
     * of the parent actor
     */
    std::vector<Actor*> mChildren;

};

#endif //__ACTOR_H__
