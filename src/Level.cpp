#include "Level.h"

#include <OgreManualObject.h>
#include <OgreRenderOperation.h>

//#include <cstdio>
//#include <ctime>

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
 // srand( time(NULL) );
}

void Level::generate() {
  clearExisting();

  mBaseLevelNode = mSceneManager->getRootSceneNode()->createChildSceneNode("VoxelLevel");
	mVolume = new Volume<MaterialDensityPair44>(32, 32, 512);

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

  mSceneManager->destroyMovableObject("VoxelRenderable", SurfacePatchRenderableFactory::FACTORY_TYPE_NAME);
}

/**
 * The following is taken from various parts of PolyVox
 * and the Thermite3D engine.
 */

void Level::createVoxelVolume() {
  uint8_t density = MaterialDensityPair44::getMaxDensity();
  int depth = mVolume->getDepth(),
      width = mVolume->getWidth(),
      height = mVolume->getHeight();

  for(int x = 0; x < width; x++) {
    for(int y = 0; y < height; y++) {
      for(int z = 0; z < depth; z++) {

        //Get the old voxel
        MaterialDensityPair44 voxel = mVolume->getVoxelAt(x,y,z);

        if( (x > 15 && x < 20) &&
            (y > 15 && y < 20)) {
          voxel.setDensity(0);
        } else {
          //Modify the density
          voxel.setDensity(density);
        }

        //Wrte the voxel value into the volume	
        mVolume->setVoxelAt(x, y, z, voxel);
      }
    }
  }
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
  SurfaceExtractor<MaterialDensityPair44> extractor(mVolume, mVolume->getEnclosingRegion(), &mesh);
  extractor.execute();
  
  SurfacePatchRenderable* renderable =  dynamic_cast<SurfacePatchRenderable*>(
      mSceneManager->createMovableObject("VoxelRenderable", SurfacePatchRenderableFactory::FACTORY_TYPE_NAME));
  renderable->setMaterial("BaseWhiteNoLighting");

  mBaseLevelNode->attachObject(renderable);

  renderable->buildRenderOperationFrom(mesh, true);

  mBaseLevelNode->setScale(100, 100, 100);
  mBaseLevelNode->setPosition(0.0f, 0.0f, 0.0f);
}
