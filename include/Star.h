#ifndef __STAR_H__
#define __STAR_H__

//#include "SolarSystem.h"

#include <OgreSceneNode.h>

/**
 * Abstract planet representation.
 * Is a part of a solar system, has renderable
 */
class Star {

  public:
    Star();

    void attachTo(Ogre::SceneNode* node);

  private:
    // Link to the solar system this planet belongs to
//    SolarSystem* mSolarSystem;

    // The SceneNode this planet uses to place itself
    // in the scene
    Ogre::SceneNode* mSceneNode;
};

#endif // __STAR_H__
