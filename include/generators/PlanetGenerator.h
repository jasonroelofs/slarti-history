#ifndef __PLANET_GENERATOR_H__
#define __PLANET_GENERATOR_H__

#include "Planet.h"

/**
 * Factory for building random planets.
 */
class PlanetGenerator {

  protected:
    /**
     * Cannot instantiate
     */
    PlanetGenerator() { }

  public:
    static Planet* generatePlanet();

};

#endif //__PLANET_GENERATOR_H__
