#ifndef __VOXEL_LEVEL_GENERATOR_H__
#define __VOXEL_LEVEL_GENERATOR_H__

/**
 * This generator uses Voxel fields and the worm / burrower
 * method of generating random dungeons.
 *
 */
class VoxelLevelGenerator {
  public:
    VoxelLevelGenerator(int startX, int startZ);
    ~VoxelLevelGenerator() { }


  protected:
    int mStartX, mStartZ;
};

#endif //__VOXEL_LEVEL_GENERATOR_H__
