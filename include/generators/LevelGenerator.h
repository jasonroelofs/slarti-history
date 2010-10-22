#ifndef __generators_LEVEL_GENERATOR_H__
#define __generators_LEVEL_GENERATOR_H__

#include "VoxelVolume.h"
#include "OgreVector3.h"

#include <vector>

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
     * Define a Room inside of a level
     */
    struct Room {
      // Block location
      int blockX, blockY, blockZ;
      
      // Offset inside of Block (0 - 100, percentage of size)
      int x, y, z;

      // Size of room itself (0 - 100 percentage of size)
      int width, height, depth;
    };

    struct LevelData {
      // See LevelSizes
      int size;
      
      // Size in 'blocks' or regions
      int blockWidth, blockHeight, blockDepth;
      
      // Size in voxels
      int width, height, depth;

      int blockSize;

      std::vector<Room> rooms;
    };

    /**
     * The following are meant to be used internally only
     * Use LevelGenerator::generate for a new randomly
     * generated level
     */
    LevelGenerator();
    ~LevelGenerator();

    void chooseSizeOfLevel();

    void buildRoomsInLevel();

    void calculateLevelBounds();

    VoxelVolume* prepareVolume();

    void carveOutVolume(VoxelVolume* volume);

    void carveRoom(VoxelVolume* volume, Ogre::Vector3 topLeft, Ogre::Vector3 bottomRight);

  private:
    LevelData* mLevelData;
};

#endif // __generators_LEVEL_GENERATOR_H__
