#ifndef __VOXEL_VOLUME_H__
#define __VOXEL_VOLUME_H__

#include "PolyVox/Volume.h"
#include "PolyVox/MaterialDensityPair.h"

/*
class Voxel : 
  public PolyVox::MaterialDensityPair44
{
  public:
    Voxel() 
      : PolyVox::MaterialDensityPair44(0, 0)
    {}
};
*/

typedef PolyVox::MaterialDensityPair44 Voxel;
typedef PolyVox::Volume<Voxel> VoxelVolume;

#endif //__VOXEL_VOLUME_H__
