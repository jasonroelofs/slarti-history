#ifndef __UTILS_H__
#define __UTILS_H__

#include <OgreVector3.h>

#include <cstdlib>

#ifndef max
#define max(a,b) (((a) > (b)) ? (a) : (b))
#define min(a,b) (((a) < (b)) ? (a) : (b))
#endif

namespace utils {
  // A sector is a 100k unit cube
  const int SECTOR_SIZE = 100000;

  // Sector grid is 10 x 10 x 10
  // Note: Ogre's origin is actually in the center of the sector grid, so we need to offset
  const int MAX_SECTORS = 10;

  /**
   * Pick a random number between two numbers.
   * This uses rand()
   */
  int random(int from, int to);

  /**
   * Given a sector, find the position in Ogre Units of the center
   * of the sector
   */
  Ogre::Vector3 calculatePosition(Ogre::Vector3 sector);

  /**
   * Given a sector (1 - 10)x3, and an offset [0,1)x3 figure out
   * the actual position in Ogre Units.
   */
  Ogre::Vector3 calculatePosition(Ogre::Vector3 sector, Ogre::Vector3 offset);
}

#endif // __UTILS_H__
