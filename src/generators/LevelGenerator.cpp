#include "generators/LevelGenerator.h"

#include "Utils.h"

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
        bool hasRoom = rand() % 100 > 30;

        if(hasRoom) {
          struct Room room = {
            // In block
            x, y, z,
            // Position
            Utils::random(5, 95), Utils::random(5, 95), Utils::random(5, 95),
            // Size
            Utils::random(20, 80), Utils::random(5, 50), Utils::random(20, 80),
          };

          cout << "Adding a room to our level!" << endl;

          mLevelData->rooms.push_back(room);
        }
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
  int regionHeight = blockSize * mLevelData->blockHeight;
  int regionDepth = blockSize * mLevelData->blockDepth;
  int regionWidth = blockSize * mLevelData->blockWidth;

  for(int idx = 0; idx < mLevelData->rooms.size(); idx++) {
    room = mLevelData->rooms[idx];

    cout << "Building room" << endl;

    topLeft.x = ((blockSize * room.blockX) + (blockSize * (room.x / 100.0f))) - ((blockSize / 2) * (room.width / 100.0f));
    topLeft.y = ((blockSize * room.blockY) + (blockSize * (room.y / 100.0f))) - ((blockSize / 2) * (room.height / 100.0f));
    topLeft.z = ((blockSize * room.blockZ) + (blockSize * (room.z / 100.0f))) - ((blockSize / 2) * (room.depth / 100.0f));

    bottomRight.x = topLeft.x + (blockSize * (room.width / 100.0f));
    bottomRight.y = topLeft.y + (blockSize * (room.height / 100.0f));
    bottomRight.z = topLeft.z + (blockSize * (room.depth / 100.0f));

    // Fix positions to always be inside the grid
    topLeft.x = max(topLeft.x, 2);
    topLeft.y = max(topLeft.y, 2);
    topLeft.z = max(topLeft.z, 2);

    bottomRight.x = min(bottomRight.x, regionWidth - 2);
    bottomRight.y = min(bottomRight.y, regionHeight - 2);
    bottomRight.z = min(bottomRight.z, regionDepth - 2);

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
