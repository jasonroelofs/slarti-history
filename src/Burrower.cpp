#include "Burrower.h"

#include <iostream>
using namespace std;

Burrower::Burrower(VoxelVolume* volume)
  :mVolume(volume)
{
  mWidth = mVolume->getWidth();
  mHeight = mVolume->getHeight();
  mDepth = mVolume->getDepth();
}

void Burrower::burrow(int startX, int startZ) {
  int x = startX, y, z = startZ;
  int startHeight = mHeight / 2;
  Voxel voxel;

  int tunnelSize = 1;

  // Build a 'start' path by tunnelling down to the appropriate start depth from 0
  // building our tunnel to be a cube
  for(x = startX - tunnelSize; x < startX + tunnelSize; x++) {
    for(y = 0; y < startHeight; y++) {
      for(z = startZ - tunnelSize; z < startZ + tunnelSize; z++) {
        //Get the old voxel
        voxel = mVolume->getVoxelAt(x,y,z);
        voxel.setDensity(0);
        //Wrte the voxel value into the volume
        mVolume->setVoxelAt(x, y, z, voxel);
      }
    }
  }

  int stepsToTake = 240, stepsTaken = 0;

  int moveX = 1, moveY = 0, moveZ = 0;
  int tmp;

  while(stepsToTake > 0) {
    stepsTaken = rand() % 10;
    moveX = 0;
    moveZ = 0;

    // Choose a direction
    if(rand() % 10 > 4) {
      moveX = 1;
    } else {
      moveZ = 1;
    }

    /*
    // Play with up / down
    tmp = rand() % 20;
    if(tmp < 10) {
      moveY = 0;
    } else if (tmp < 15) {
      moveY = 1;
    } else {
      moveY = -1;
    }
    */

    // Pick a tunnel size
    tunnelSize = rand() % 4 + 1;

    takeSteps(x, y, z, moveX, moveY, moveZ, stepsTaken, tunnelSize);

    x += moveX * stepsTaken;
    y += moveY * stepsTaken;
    z += moveZ * stepsTaken;

    stepsToTake -= stepsTaken;

    cout << "Steps taken: " << stepsTaken 
      << " Moved X: " << moveX 
      << " Moved Z: " << moveZ 
      << " New x, y, z: " 
      << x 
      << ", " << y 
      << ", " << z 
      << endl;
  }

  // After a given distance, build a room
}

void Burrower::takeSteps(
    int startX, int startY, int startZ,
    int moveX, int moveY, int moveZ,
    int toTake, int tunnelSize)
{
  int tunnelSizeX = tunnelSize + (moveX * toTake);
  int tunnelSizeY = tunnelSize + (moveY * toTake);
  int tunnelSizeZ = tunnelSize + (moveZ * toTake);

  Voxel voxel;

  int x, y, z;

  for(x = startX - tunnelSizeX; x < startX + tunnelSizeX; x++) {
    for(y = startY - tunnelSizeY; y < startY + tunnelSizeY; y++) {
      for(z = startZ - tunnelSizeZ; z < startZ + tunnelSizeZ; z++) {
        // Edge protection
        if(x > 1 && x < mWidth &&
            y > 1 && y < mHeight &&
            z > 1 && z < mDepth) {
          //Get the old voxel
          voxel = mVolume->getVoxelAt(x,y,z);
          voxel.setDensity(0);
          //Wrte the voxel value into the volume
          mVolume->setVoxelAt(x, y, z, voxel);
        }
      }
    }
  }

}
