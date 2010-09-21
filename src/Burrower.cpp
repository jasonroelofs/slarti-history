#include "Burrower.h"

Burrower::Burrower(VoxelVolume* volume) 
  :mVolume(volume)
{
  mWidth = mVolume->getWidth();
  mHeight = mVolume->getHeight();
  mDepth = mVolume->getDepth();
}

void Burrower::burrow(int startX, int startZ) {
  // Build a 'start' path by tunnelling down to the appropriate start depth from 0
  // Pick a random direction and start walking (for now random cardinal direction)
  // After a given distance, build a room
}
