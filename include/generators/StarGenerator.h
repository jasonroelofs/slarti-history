#ifndef __STAR_GENERATOR_H__
#define __STAR_GENERATOR_H__

#include "Actor.h"

/**
 * Factory for building stars
 */
class StarGenerator {

  protected:
    /**
     * Cannot instantiate
     */
    StarGenerator() { }

  public:
    static Actor* generateStar();

};

#endif //__STAR_GENERATOR_H__
