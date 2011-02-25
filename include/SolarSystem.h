#ifndef __SOLAR_SYSTEM_H__
#define __SOLAR_SYSTEM_H__

#include "Planet.h"
#include "Star.h"

#include <OgreSceneManager.h>
#include <OgreSceneNode.h>

/**
 * Model a solar system.
 * Every solar system has at least one star and can have
 * any number of planets from 0 to a bunch.
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

    /**
     * Figure out what kind of star system we have with this 
     * system, single star, double star, dwarf, neutron star, etc
     */
    void chooseSunType();

    /**
     * Generate a random number of planets (can choose to generate none) 
     * and place them appropriately in the system
     */
    void generatePlanets();

    /**
     * Build and place an individual planet
     */
    void generatePlanet();


  protected:
    Ogre::SceneManager* mSceneManager;

    // Base node that we use as a parent to our level
    Ogre::SceneNode* mBaseSceneNode;

    // Simple solar "system" we have one planet right now
    Planet* mPlanet;

    // Our center star
    Star* mStar;

    // Random number generator seed for this system
    unsigned int mSeed;

};

#endif // __SOLAR_SYSTEM_H__
