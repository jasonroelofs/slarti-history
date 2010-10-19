#include "generators/LevelGenerator.h"

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
static VoxelVolume* LevelGenerator::generate() {
  LevelGenerator levelGen;

  levelGen.chooseSizeOfLevel();

  levelGen.buildRoomsInLevel();

  levelGen.calculateLevelBounds();

  VoxelVolume* volume = new VoxelVolume(
      levelGen.width, 
      levelGen.height, 
      levelGen.depth, 
      levelGen.blockSize);

  levelGen.prepareVolume(volume);

  levelGen.carveOutVolume(volume);

  return volume;
}

//=============================================================

LevelGenerator::LevelGenerator() {
}

void LevelGenerator::chooseSizeOfLevel() {
  // Pick one of four: Small, Medium, Large, Mega.
  // Bias more towards Small and Medium for now
  //
  // From selection give ourselves a size in blocks w x h x d
  //  ( for now all rooms will be on the same plane [same h] )
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
}

void LevelGenerator::calculateLevelBounds() {
  // Given w h d from chooseSizeOfLevel calculate the size in voxels
  // Resets width, height, depth to new values
}

void LevelGenerator::prepareVolume(VoxelVolume* volume) {
  Voxel voxel;
  uint8_t density = Voxel::getMaxDensity();

  // TODO FIXME
  // Need to find out how I can default the volume
  // to be full. For now, fill the volume to full density
  for(int x = 0; x < width; x++) {
    for(int y = 0; y < height; y++) {
      for(int z = 0; z < depth; z++) {
        //Get the old voxel
        voxel = volume->getVoxelAt(x,y,z);
        voxel.setDensity(density);
        //Wrte the voxel value into the volume	
        volume->setVoxelAt(x, y, z, voxel);
      }
    }
  }
}

void LevelGenerator::carveOutVolume(VoxelVolume* volume) {
  // For each room
  //  - Calculate top left and bottom right locations in voxel space
  //  - Set each voxel in the defined region as empty + material
}
