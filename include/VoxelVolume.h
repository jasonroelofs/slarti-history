#ifndef __VOXEL_VOLUME_H__
#define __VOXEL_VOLUME_H__

#include "PolyVox/MaterialDensityPair.h"

class Voxel : 
  public PolyVox::MaterialDensityPair44
{
  public:
    Voxel() 
      : PolyVox::MaterialDensityPair44(0, 4)
    {}
};

typedef PolyVox::Volume<Voxel> VoxelVolume;

#endif //__VOXEL_VOLUME_H__
