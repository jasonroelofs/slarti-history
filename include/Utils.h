#ifndef __UTILS_H__
#define __UTILS_H__

#include <OgreVector3.h>
#include <OgreLogManager.h>

#include <cstdlib>

#define OGRE_LOG(msg) \
  { Ogre::LogManager::getSingleton().logMessage(msg); }

namespace utils {
  const int SECTOR_SIZE = 200000;
  const int MAX_SECTORS = 10; // cubed

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
