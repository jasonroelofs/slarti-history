#ifndef __ROOMER_H__
#define __ROOMER_H__

#include "VoxelVolume.h"
#include <OgreVector3.h>

/**
 * This is a level generator class.
 * In this generator, we build up a random number of rooms
 * in the volume given then work to connect them together
 */
class Roomer {
  public:
    Roomer(VoxelVolume* volume);

    /**
     * Run the level generator
     */
    void go();

  protected:
    void buildRoom(Ogre::Vector3 topLeft, Ogre::Vector3 bottomRight);

  private:
    VoxelVolume* mVolume;
};

#endif //__ROOMER_H__
