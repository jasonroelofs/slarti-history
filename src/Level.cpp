#include "Level.h"
#include "generators/LevelGenerator.h"

#include <OgreManualObject.h>
#include <OgreRenderOperation.h>

#include <iostream>
using namespace std;

#include "PolyVox/SurfaceExtractor.h"
#include "PolyVox/SurfaceMesh.h"
using namespace PolyVox;

#include "SurfacePatchRenderable.h"

Level::Level(Ogre::SceneManager* manager)
  : mSceneManager(manager),
    mBaseLevelNode(0),
    mVolume(0)
{
}

void Level::generate() {
  clearExisting();

  mBaseLevelNode = mSceneManager->getRootSceneNode()->createChildSceneNode("VoxelLevel");
  
  // Get a new volume from the generator
	mVolume = LevelGenerator::generate();

  // Build polygon data from the volume
  buildRenderable();
}

void Level::clearExisting() {
  if(mBaseLevelNode) {
    mSceneManager->destroySceneNode("VoxelLevel");
    mBaseLevelNode = 0;
  }

  mSceneManager->destroyMovableObject("VoxelRenderable", 
      SurfacePatchRenderableFactory::FACTORY_TYPE_NAME);

  if(mVolume) {
    delete mVolume;
    mVolume = 0;
  }
}

void Level::buildRenderable() {
  // Extract the data to build our render node
  SurfaceMesh mesh;
  SurfaceExtractor<Voxel> extractor(mVolume, mVolume->getEnclosingRegion(), &mesh);
  extractor.execute();
  
  SurfacePatchRenderable* renderable =  dynamic_cast<SurfacePatchRenderable*>(
      mSceneManager->createMovableObject("VoxelRenderable", 
        SurfacePatchRenderableFactory::FACTORY_TYPE_NAME));
  renderable->setMaterial("walls/dirt");

  mBaseLevelNode->attachObject(renderable);

  renderable->buildRenderOperationFrom(mesh, true);

  mBaseLevelNode->setPosition(0.0f, 0.0f, 0.0f);
  mBaseLevelNode->setScale(-100, -100, -100);
}
