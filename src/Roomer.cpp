#include "Roomer.h"

Roomer::Roomer(VoxelVolume* volume)
  : mVolume(volume)
{}

void Roomer::go() {
  // First build up some statistics on the volume
  // we've been given
  int volumeHeight = mVolume->getHeight();
  int volumeWidth = mVolume->getWidth();
  int volumeDepth = mVolume->getDepth();

  int maxRoomHeight = volumeHeight - 4;
  int maxRoomWidth = volumeWidth - 4;
  int maxRoomDepth = volumeDepth - 4;

  // Hard code minimum for now
  int minRoomHeight = 8, minRoomWidth = 8, minRoomDepth = 8;

  // Figure out how many rooms we want to place
  //
  // From that, figure out the average size of each room
  // so that they'll fit in a reasonable, but still random
  // manner
  //
  // Once the rooms are built, try to connect them

  int numRooms = rand() % 5;
  int roomWidth, roomHeight, roomDepth;
  Ogre::Vector3 topLeft, bottomRight;

  for(int i = 0; i < numRooms; i++) {
    // Size of room
    roomWidth = rand() % (maxRoomWidth - minRoomWidth);
    roomHeight = rand() % (maxRoomHeight - minRoomHeight);
    roomDepth = rand() % (maxRoomDepth - minRoomDepth);

    // Position of room
    topLeft.x = rand() % (maxRoomWidth - minRoomWidth - minRoomWidth);
    topLeft.y = rand() % (maxRoomHeight - minRoomHeight - minRoomHeight);
    topLeft.z = rand() % (maxRoomDepth - minRoomDepth - minRoomDepth);

    bottomRight.x = topLeft.x + roomWidth;
    bottomRight.y = topLeft.y + roomHeight;
    bottomRight.z = topLeft.z + roomDepth;

    buildRoom(topLeft, bottomRight);
  }
}

void Roomer::buildRoom(Ogre::Vector3 topLeft, Ogre::Vector3 bottomRight) {
  Voxel voxel;
  uint8_t density = Voxel::getMaxDensity();

  for(int z = topLeft.z; z < bottomRight.z; z++) {
    for(int y = topLeft.y; y < bottomRight.y; y++) {
      for(int x = topLeft.x; x < bottomRight.x; x++) {
        //Get the old voxel
        voxel = mVolume->getVoxelAt(x,y,z);
        voxel.setDensity(0);
        voxel.setMaterial(1);
        //Wrte the voxel value into the volume
        mVolume->setVoxelAt(x, y, z, voxel);
      }
    }
  }
}
