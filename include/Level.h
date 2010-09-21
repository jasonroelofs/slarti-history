#ifndef __LEVEL_H__
#define __LEVEL_H__

#include <OgreSceneManager.h>
#include <OgreSceneNode.h>

#include "VoxelVolume.h"

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

    void clearExisting();

    void createVoxelVolume();
    void buildRenderable();

  private:
    // Link back to the scene manager
    Ogre::SceneManager* mSceneManager;

    // Base node that we use as a parent to our level
    Ogre::SceneNode* mBaseLevelNode;

    // Our volume of voxel data
    VoxelVolume* mVolume;
};

#endif // __LEVEL_H__
