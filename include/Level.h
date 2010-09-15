#ifndef __LEVEL_H__
#define __LEVEL_H__

#include <OgreSceneManager.h>

/**
 * This class handles the generation of a single
 * dungeon level.
 */
class Level {
  public:
    Level(Ogre::SceneManager* manager);
    ~Level();

    /**
     * Generate new geometry for this level
     */
    void generate();

  private:
    // Link back to the scene manager
    Ogre::SceneManager* mSceneManager;
};

#endif // __LEVEL_H__
