#pragma region License
/*******************************************************************************
Copyright (c) 2005-2009 David Williams

This software is provided 'as-is', without any express or implied
warranty. In no event will the authors be held liable for any damages
arising from the use of this software.

Permission is granted to anyone to use this software for any purpose,
including commercial applications, and to alter it and redistribute it
freely, subject to the following restrictions:

    1. The origin of this software must not be misrepresented; you must not
    claim that you wrote the original software. If you use this software
    in a product, an acknowledgment in the product documentation would be
    appreciated but is not required.

    2. Altered source versions must be plainly marked as such, and must not be
    misrepresented as being the original software.

    3. This notice may not be removed or altered from any source
    distribution. 	
*******************************************************************************/
#pragma endregion

#ifndef __PolyVox_SurfaceExtractor_H__
#define __PolyVox_SurfaceExtractor_H__

#pragma region Headers
#include "PolyVox/PolyVoxForwardDeclarations.h"
#include "PolyVox/VolumeSampler.h"

#include "PolyVox/PolyVoxImpl/TypeDef.h"
#pragma endregion

namespace PolyVox
{
	template <typename VoxelType>
	class SurfaceExtractor
	{
	public:
		SurfaceExtractor(Volume<VoxelType>* volData, Region region, SurfaceMesh* result);

		void execute();

	private:
		//Compute the cell bitmask for a particular slice in z.
		template<bool isPrevZAvail>
		uint32_t computeBitmaskForSlice(const Array2DUint8& pPreviousBitmask, Array2DUint8& pCurrentBitmask);

		//Compute the cell bitmask for a given cell.
		template<bool isPrevXAvail, bool isPrevYAvail, bool isPrevZAvail>
		void computeBitmaskForCell(const Array2DUint8& pPreviousBitmask, Array2DUint8& pCurrentBitmask);

		//Use the cell bitmasks to generate all the vertices needed for that slice
		void generateVerticesForSlice(const Array2DUint8& pCurrentBitmask,
			Array2DInt32& m_pCurrentVertexIndicesX,
			Array2DInt32& m_pCurrentVertexIndicesY,
			Array2DInt32& m_pCurrentVertexIndicesZ);

		Vector3DFloat computeCentralDifferenceGradient(const VolumeSampler<VoxelType>& volIter);
		Vector3DFloat computeSobelGradient(const VolumeSampler<VoxelType>& volIter);

		//Use the cell bitmasks to generate all the indices needed for that slice
		void generateIndicesForSlice(const Array2DUint8& pPreviousBitmask,
			const Array2DInt32& m_pPreviousVertexIndicesX,
			const Array2DInt32& m_pPreviousVertexIndicesY,
			const Array2DInt32& m_pPreviousVertexIndicesZ,
			const Array2DInt32& m_pCurrentVertexIndicesX,
			const Array2DInt32& m_pCurrentVertexIndicesY,
			const Array2DInt32& m_pCurrentVertexIndicesZ);

		//The volume data and a sampler to access it.
		Volume<VoxelType>* m_volData;
		VolumeSampler<VoxelType> m_sampVolume;

		//Holds a position in volume space.
		uint16_t uXVolSpace;
		uint16_t uYVolSpace;
		uint16_t uZVolSpace;

		//Holds a position in region space.
		uint16_t uXRegSpace;
		uint16_t uYRegSpace;
		uint16_t uZRegSpace;

		//Used to return the number of cells in a slice which contain triangles.
		uint32_t m_uNoOfOccupiedCells;

		//The surface patch we are currently filling.
		SurfaceMesh* m_meshCurrent;

		//Information about the region we are currently processing
		Region m_regSizeInVoxels;
		Region m_regSizeInCells;
		/*Region m_regSizeInVoxelsCropped;
		Region m_regSizeInVoxelsUncropped;
		Region m_regVolumeCropped;*/
		Region m_regSlicePrevious;
		Region m_regSliceCurrent;
	};
}

#include "PolyVox/SurfaceExtractor.inl"

#endif
