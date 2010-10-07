#include "Level.h"

#include <OgreManualObject.h>
#include <OgreRenderOperation.h>

#include <iostream>
using namespace std;

#include "PolyVox/SurfaceExtractor.h"
#include "PolyVox/SurfaceMesh.h"
using namespace PolyVox;

#include "SurfacePatchRenderable.h"
#include "Burrower.h"
#include "Roomer.h"
#include "VoxelVolume.h"

Level::Level(Ogre::SceneManager* manager)
  : mSceneManager(manager),
    mBaseLevelNode(0),
    mVolume(0)
{
}

void Level::generate() {
  clearExisting();

  mBaseLevelNode = mSceneManager->getRootSceneNode()->createChildSceneNode("VoxelLevel");
  createVoxelVolume();
  buildRenderable();
}

void Level::clearExisting() {
  if(mVolume) {
    delete mVolume;
    mVolume = 0;
  }

  if(mBaseLevelNode) {
    mSceneManager->destroySceneNode("VoxelLevel");
    mBaseLevelNode = 0;
  }

  mSceneManager->destroyMovableObject("VoxelRenderable", 
      SurfacePatchRenderableFactory::FACTORY_TYPE_NAME);
}

/**
 * The following is taken from various parts of PolyVox
 * and the Thermite3D engine.
 */

void Level::createVoxelVolume() {
	mVolume = new VoxelVolume(128, 32, 128, 0);

//  Burrower burrower(mVolume);
  Roomer roomer(mVolume);

  int depth = mVolume->getDepth(),
      width = mVolume->getWidth(),
      height = mVolume->getHeight();
  Voxel voxel;
  uint8_t density = Voxel::getMaxDensity();

  // TODO FIXME
  // Need to find out how I can default the volume
  // to be full. For now, fill the volume for the
  // burrower
  for(int x = 0; x < width; x++) {
    for(int y = 0; y < height; y++) {
      for(int z = 0; z < depth; z++) {
        //Get the old voxel
        voxel = mVolume->getVoxelAt(x,y,z);
        voxel.setDensity(density);
        //Wrte the voxel value into the volume	
        mVolume->setVoxelAt(x, y, z, voxel);
      }
    }
  }

  // Start the burrower half way in the field
  //burrower.burrow(4, 4);

  roomer.go();
}

/*
void createSphere() {

  int radius = 10;

	//This vector hold the position of the center of the volume
	Vector3DFloat volumeCenter(mVolume->getWidth() / 2, mVolume->getHeight() / 2, mVolume->getDepth() / 2);

	//This three-level for loop iterates over every voxel in the volume
	for (int z = 0; z < mVolume->getWidth(); z++)
	{
		for (int y = 0; y < mVolume->getHeight(); y++)
		{
			for (int x = 0; x < mVolume->getDepth(); x++)
			{
				//Store our current position as a vector...
				Vector3DFloat currentPos(x,y,z);	
				//And compute how far the current position is from the center of the volume
				float distToCenter = (currentPos - volumeCenter).length();

				//If the current voxel is less than 'radius' units from the center then we make it solid.
				if(distToCenter <= radius)
				{
					//Our new density value
					uint8_t density = MaterialDensityPair44::getMaxDensity();

					//Get the old voxel
					MaterialDensityPair44 voxel = mVolume->getVoxelAt(x,y,z);

					//Modify the density
					voxel.setDensity(density);

					//Wrte the voxel value into the volume	
					mVolume->setVoxelAt(x, y, z, voxel);
				}
			}
		}
  }
}
*/

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
