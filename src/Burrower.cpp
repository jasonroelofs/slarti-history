#include "Burrower.h"

Burrower::Burrower(VoxelVolume* volume) 
  :mVolume(volume)
{
  mWidth = mVolume->getWidth();
  mHeight = mVolume->getHeight();
  mDepth = mVolume->getDepth();
}

void Burrower::burrow(int startX, int startZ) {
  int x = startX, y, z = startZ, offset;
  int startDepth = mDepth;
  Voxel voxel;

  // Build a 'start' path by tunnelling down to the appropriate start depth from 0
  /*
  for(y = 0; y < startDepth; y++) {
    for(offset = -18; offset < 18; offset++) {
      voxel = mVolume->getVoxelAt(x + offset, y, z + offset);
      voxel.setDensity(Voxel::getMaxDensity());
      mVolume->setVoxelAt(x + offset, y, z + offset, voxel);
    }
  }
  */

  for(int x = 0; x < mWidth; x++) {
    for(int y = 0; y < mHeight; y++) {
      for(int z = 0; z < mDepth; z++) {

        //Get the old voxel
        voxel = mVolume->getVoxelAt(x,y,z);

        if( (x > 15 && x < 17) &&
            (y > 15 && y < 17)) {
          voxel.setDensity(Voxel::getMaxDensity());
        }

        //Wrte the voxel value into the volume	
        mVolume->setVoxelAt(x, y, z, voxel);
      }
    }
  }
  // Pick a random direction and start walking (for now random cardinal direction)
  // After a given distance, build a room
}
