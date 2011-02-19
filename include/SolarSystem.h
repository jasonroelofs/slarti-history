#ifndef __SOLAR_SYSTEM_H__
#define __SOLAR_SYSTEM_H__

#include "Planet.h"

#include <OgreSceneManager.h>
#include <OgreSceneNode.h>

/**
 * 
 */
class SolarSystem {
  public:
    /**
     * Build a new solar system, given the scene manager to 
     * add things to the world
     */
    SolarSystem(Ogre::SceneManager* manager);

    /**
     * Generate a new set of planets for this solar system
     */
    void generate();


  protected:
    Ogre::SceneManager* mSceneManager;

    // Base node that we use as a parent to our level
    Ogre::SceneNode* mBaseSceneNode;

    // Simple solar "system" we have one planet right now
    Planet* mPlanet;

};

#endif // __SOLAR_SYSTEM_H__
