#include "Utils.h"

namespace utils {

  int random(int from, int to) {
    return from + (rand() % (to - from));
  }

  Ogre::Vector3 calculatePosition(Ogre::Vector3 sector) {
    return Ogre::Vector3(
      (sector.x - (MAX_SECTORS / 2)) * SECTOR_SIZE,
      (sector.y - (MAX_SECTORS / 2)) * SECTOR_SIZE,
      (sector.z - (MAX_SECTORS / 2)) * SECTOR_SIZE
    );
  }

  Ogre::Vector3 calculatePosition(Ogre::Vector3 sector, Ogre::Vector3 offset) {
    return calculatePosition(sector) + (offset * SECTOR_SIZE); 
  }
}
