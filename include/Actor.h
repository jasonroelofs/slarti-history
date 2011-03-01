#ifndef __ACTOR_H__
#define __ACTOR_H__

#include <vector>

class Component;
class Ogre::Vector3;

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
    TransformComponent transform;

    /**
     * Add a component to this actor
     */
    void addComponent(Component* comp);

  protected:
    /**
     * Actors are composed of Components, which add
     * data to the Actor and flag this Actor for added functionality.
     */
    std::vector<Component*> mComponents;

};

#endif //__ACTOR_H__
