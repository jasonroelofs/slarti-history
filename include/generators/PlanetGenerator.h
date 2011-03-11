#ifndef __PLANET_GENERATOR_H__
#define __PLANET_GENERATOR_H__

#include "Actor.h"

#include <OgreVector3.h>

/**
 * Factory for building random planets.
 */
class PlanetGenerator {

  protected:
    /**
     * Cannot instantiate
     */
    PlanetGenerator() { }

    static const int MIN_PLANET_SIZE, MAX_PLANET_SIZE;

  public:
    static Actor* generatePlanet();

};

#endif //__PLANET_GENERATOR_H__
