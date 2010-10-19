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

    /**
     * When a level is getting kicked out for a new
     * one, we need to clear out all old scene nodes
     * and polygon data.
     */
    void clearExisting();

    /**
     * Once we've got a new volume from
     * the generator in mVolume, we turn the
     * voxel data into polygon data to be rendered.
     */
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
