#ifndef __generators_LEVEL_GENERATOR_H__
#define __generators_LEVEL_GENERATOR_H__

#include "VoxelVolume.h"

/**
 * This static generator class starts the process of
 * building up a new voxel volume for a level.
 */
class LevelGenerator {

  public:
    /**
     * Build and return a new voxel volume carved out and
     * ready to be built into a renderable.
     *
     * The receiver of this volume is responsible for 
     * cleaning up the voxel pointer when it's no longer
     * needed.
     */
    static VoxelVolume* generate();

  public:

    enum LevelSizes {
      Level_Small,
      Level_Medium,
      Level_Large,
      Level_Mega
    };

    /**
     * The following are meant to be used internally only
     * Use LevelGenerator::generate for a new randomly
     * generated level
     */
    LevelGenerator();

    void chooseSizeOfLevel();

    void buildRoomsInLevel();

    void calculateLevelBounds();

    void prepareVolume(VoxelVolume* volume);

    void carveOutVolume(VoxelVolume* volume);

    // Will either be the dimensions of the level in blocks
    // or the dimensions of the level in voxels depending on
    // what point in the process we're on
    int width, height, depth, blockSize;

};

#endif // __generators_LEVEL_GENERATOR_H__
