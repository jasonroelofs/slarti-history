#include "generators/LevelGenerator.h"

#include <iostream>
using namespace std;

/**
 * The current idea of how this is going to work:
 *
 * We choose a size of the world: small, med, large, mega
 * Split the world into blocks of equal size
 * Iterate over every block, choosing to put a room in the block or not
 *  - Room center must be contained in the block region
 *  - Room bounds can extend into neighboring blocks
 *  - (handle case of edge blocks by: adding another row or clipping)
 *
 * Once all rooms have been given, or not given, a room
 *  - Figure out full voxel width, height, depth of region
 *  - Build voxel volume, specifying block size as well
 *  - Carve out each room in the voxel volume as defined from above
 *  - Return pointer to voxel volume
 */
VoxelVolume* LevelGenerator::generate() {
  LevelGenerator levelGen;

  levelGen.chooseSizeOfLevel();

  levelGen.buildRoomsInLevel();

  levelGen.calculateLevelBounds();

  VoxelVolume* volume = levelGen.prepareVolume();

  levelGen.carveOutVolume(volume);

  return volume;
}

//=============================================================

LevelGenerator::LevelGenerator() {
  mLevelData = new LevelData();
}

LevelGenerator::~LevelGenerator() {
  if(mLevelData) {
    delete mLevelData;
  }
}

void LevelGenerator::chooseSizeOfLevel() {
  // Pick one of four: Small, Medium, Large, Mega.
  mLevelData->size = Level_Small;

  // Bias more towards Small and Medium for now
  //
  // From selection give ourselves a size in blocks w x h x d
  //  ( for now all rooms will be on the same plane [same h] )
  mLevelData->blockWidth = 3 + rand() % 3;
  mLevelData->blockHeight = 1;
  mLevelData->blockDepth = 3 + rand() % 3;
}

void LevelGenerator::buildRoomsInLevel() {
  // For each block in level
  //  - Does this block have a room center?
  //    - Choose random point in block for center
  //    - Choose size (w x h x d) for room (hard-code h for now)
  //      - Bias towards the size of the block its in or smaller. Rare chance
  //        for a large multi-block room but definitely not out of the
  //        question
  //    - Possibly do collision checks with the edge of the 'level'
  //      - Center has to be >= n away from level boundaries?
  for(int z = 0; z < mLevelData->blockDepth; z++) {
    for(int y = 0; y < mLevelData->blockHeight; y++) {
      for(int x = 0; x < mLevelData->blockWidth; x++) {
        // In most cases, put a room
        //bool hasRoom = rand() % 100 > 20;

        //if(hasRoom) {
          struct Room room = {
            // In block
            x, y, z,
            // Centered
            50, 50, 50,
            // Half the size of the block
            50, 50, 50
          };

          cout << "Adding a room to our level!" << endl;

          mLevelData->rooms.push_back(room);
        //}
      }
    }
  }
}

void LevelGenerator::calculateLevelBounds() {
  // Given w h d from chooseSizeOfLevel calculate the size in voxels
  // Resets width, height, depth to new values
  mLevelData->blockSize = 32;

  mLevelData->width = mLevelData->blockWidth * mLevelData->blockSize;
  mLevelData->height = mLevelData->blockHeight * mLevelData->blockSize;
  mLevelData->depth = mLevelData->blockDepth * mLevelData->blockSize;

  cout << "built. Our data is: " << (mLevelData->width) << " x " << (mLevelData->height) << " x " << (mLevelData->depth) << endl;
}

VoxelVolume* LevelGenerator::prepareVolume() {

  // TODO Move this to another class to seperate
  // the rendering-related logic with the world-gen logic
  VoxelVolume* volume = new VoxelVolume(
      mLevelData->width,
      mLevelData->height,
      mLevelData->depth,
      mLevelData->blockSize);

  Voxel voxel;
  uint8_t density = Voxel::getMaxDensity();

  // TODO FIXME
  // Need to find out how I can default the volume
  // to be full. For now, fill the volume to full density
  for(int x = 0; x < mLevelData->width; x++) {
    for(int y = 0; y < mLevelData->height; y++) {
      for(int z = 0; z < mLevelData->depth; z++) {
        //Get the old voxel
        voxel = volume->getVoxelAt(x,y,z);
        voxel.setDensity(density);
        //Wrte the voxel value into the volume
        volume->setVoxelAt(x, y, z, voxel);
      }
    }
  }

  return volume;
}

void LevelGenerator::carveOutVolume(VoxelVolume* volume) {
  // For each room
  //  - Calculate top left and bottom right locations in voxel space
  //  - Set each voxel in the defined region as empty + material
  struct Room room;

  Ogre::Vector3 topLeft;
  Ogre::Vector3 bottomRight;

  int blockSize = mLevelData->blockSize;

  for(int idx = 0; idx < mLevelData->rooms.size(); idx++) {
    room = mLevelData->rooms[idx];

    cout << "Building room" << endl;

    topLeft.x = ((blockSize * room.blockX) + (blockSize * (1 / room.x))) - ((blockSize / 2) * (1 / room.width));
    topLeft.y = ((blockSize * room.blockY) + (blockSize * (1 / room.y))) - ((blockSize / 2) * (1 / room.height));
    topLeft.z = ((blockSize * room.blockZ) + (blockSize * (1 / room.z))) - ((blockSize / 2) * (1 / room.depth));

    bottomRight.x = topLeft.x + (blockSize * (1.0f / room.width));
    bottomRight.y = topLeft.y + (blockSize * (1.0f / room.height));
    bottomRight.z = topLeft.z + (blockSize * (1.0f / room.depth));

    cout << "Carving with " << topLeft << " to " << bottomRight << endl;

    carveRoom(volume, topLeft, bottomRight);
  }
}

void LevelGenerator::carveRoom(VoxelVolume* volume, Ogre::Vector3 topLeft, Ogre::Vector3 bottomRight) {
  Voxel voxel;
  uint8_t density = 0;

  // TODO FIXME
  // Need to find out how I can default the volume
  // to be full. For now, fill the volume to full density
  for(int x = topLeft.x; x < bottomRight.x; x++) {
    for(int y = topLeft.y; y < bottomRight.y; y++) {
      for(int z = topLeft.z; z < bottomRight.z; z++) {
        //Get the old voxel
        voxel = volume->getVoxelAt(x,y,z);
        voxel.setDensity(density);

        voxel.setMaterial(1);

        //Wrte the voxel value into the volume
        volume->setVoxelAt(x, y, z, voxel);
      }
    }
  }
}
