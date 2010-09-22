#ifndef __BURROWER_H__
#define __BURROWER_H__

#include "VoxelVolume.h"

/**
 * This class implements the Dungeon Burrowing
 * technique of random dungeon generation.
 *
 * Basically, given a start point (x, z) dig down to the
 * base y value for the level and start a burrower worm
 * in a random direction.
 *
 * The burrower travels in random directions, randomly chooses
 * to carve out randomly sized rooms and will randomly create
 * child burrowers which will head out in their own direction.
 */
class Burrower {
  public:
    Burrower(VoxelVolume* volume);

    /**
     * Start the burrowing. At the end of this
     * the voxel volume passed into the constructor
     * will contain a full dungeon
     */
    void burrow(int startX, int startZ);

    void takeSteps(int x, int y, int z, int moveX, int moveY, int moveZ, int toTake, int tunnelSize);

  protected:
    VoxelVolume* mVolume;

    // Saved dimensions of the volume
    int mWidth, mHeight, mDepth;
};

#endif //__BURROWER_H__
