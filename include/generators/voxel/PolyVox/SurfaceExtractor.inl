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

#include "PolyVox/Array.h"
#include "PolyVox/MaterialDensityPair.h"
#include "PolyVox/SurfaceMesh.h"
#include "PolyVox/PolyVoxImpl/MarchingCubesTables.h"
#include "PolyVox/SurfaceVertex.h"

namespace PolyVox
{
	template <typename VoxelType>
	SurfaceExtractor<VoxelType>::SurfaceExtractor(Volume<VoxelType>* volData, Region region, SurfaceMesh* result)
		:m_volData(volData)
		,m_sampVolume(volData)
		,m_regSizeInVoxels(region)
		,m_meshCurrent(result)
	{
		m_regSizeInVoxels.cropTo(m_volData->getEnclosingRegion());
		m_regSizeInCells = m_regSizeInVoxels;
		m_regSizeInCells.setUpperCorner(m_regSizeInCells.getUpperCorner() - Vector3DInt16(1,1,1));

		m_meshCurrent->clear();
	}

	template <typename VoxelType>
	void SurfaceExtractor<VoxelType>::execute()
	{		
		uint32_t arraySizes[2]= {m_regSizeInVoxels.width()+1, m_regSizeInVoxels.height()+1}; // Array dimensions

		//For edge indices
		Array2DInt32 m_pPreviousVertexIndicesX(arraySizes);
		Array2DInt32 m_pPreviousVertexIndicesY(arraySizes);
		Array2DInt32 m_pPreviousVertexIndicesZ(arraySizes);
		Array2DInt32 m_pCurrentVertexIndicesX(arraySizes);
		Array2DInt32 m_pCurrentVertexIndicesY(arraySizes);
		Array2DInt32 m_pCurrentVertexIndicesZ(arraySizes);

		Array2DUint8 pPreviousBitmask(arraySizes);
		Array2DUint8 pCurrentBitmask(arraySizes);

		//Create a region corresponding to the first slice
		m_regSlicePrevious = m_regSizeInVoxels;
		Vector3DInt16 v3dUpperCorner = m_regSlicePrevious.getUpperCorner();
		v3dUpperCorner.setZ(m_regSlicePrevious.getLowerCorner().getZ()); //Set the upper z to the lower z to make it one slice thick.
		m_regSlicePrevious.setUpperCorner(v3dUpperCorner);
		m_regSliceCurrent = m_regSlicePrevious;	

		uint32_t uNoOfNonEmptyCellsForSlice0 = 0;
		uint32_t uNoOfNonEmptyCellsForSlice1 = 0;

		//Process the first slice (previous slice not available)
		computeBitmaskForSlice<false>(pPreviousBitmask, pCurrentBitmask);
		uNoOfNonEmptyCellsForSlice1 = m_uNoOfOccupiedCells;

		if(uNoOfNonEmptyCellsForSlice1 != 0)
		{
			memset(m_pCurrentVertexIndicesX.getRawData(), 0xff, m_pCurrentVertexIndicesX.getNoOfElements() * 4);
			memset(m_pCurrentVertexIndicesY.getRawData(), 0xff, m_pCurrentVertexIndicesY.getNoOfElements() * 4);
			memset(m_pCurrentVertexIndicesZ.getRawData(), 0xff, m_pCurrentVertexIndicesZ.getNoOfElements() * 4);
			generateVerticesForSlice(pCurrentBitmask, m_pCurrentVertexIndicesX, m_pCurrentVertexIndicesY, m_pCurrentVertexIndicesZ);				
		}

		std::swap(uNoOfNonEmptyCellsForSlice0, uNoOfNonEmptyCellsForSlice1);
		pPreviousBitmask.swap(pCurrentBitmask);
		m_pPreviousVertexIndicesX.swap(m_pCurrentVertexIndicesX);
		m_pPreviousVertexIndicesY.swap(m_pCurrentVertexIndicesY);
		m_pPreviousVertexIndicesZ.swap(m_pCurrentVertexIndicesZ);

		m_regSlicePrevious = m_regSliceCurrent;
		m_regSliceCurrent.shift(Vector3DInt16(0,0,1));

		//Process the other slices (previous slice is available)
		for(int16_t uSlice = 1; uSlice <= m_regSizeInVoxels.depth(); uSlice++)
		{	
			computeBitmaskForSlice<true>(pPreviousBitmask, pCurrentBitmask);
			uNoOfNonEmptyCellsForSlice1 = m_uNoOfOccupiedCells;

			if(uNoOfNonEmptyCellsForSlice1 != 0)
			{
				memset(m_pCurrentVertexIndicesX.getRawData(), 0xff, m_pCurrentVertexIndicesX.getNoOfElements() * 4);
				memset(m_pCurrentVertexIndicesY.getRawData(), 0xff, m_pCurrentVertexIndicesY.getNoOfElements() * 4);
				memset(m_pCurrentVertexIndicesZ.getRawData(), 0xff, m_pCurrentVertexIndicesZ.getNoOfElements() * 4);
				generateVerticesForSlice(pCurrentBitmask, m_pCurrentVertexIndicesX, m_pCurrentVertexIndicesY, m_pCurrentVertexIndicesZ);				
			}

			if((uNoOfNonEmptyCellsForSlice0 != 0) || (uNoOfNonEmptyCellsForSlice1 != 0))
			{
				generateIndicesForSlice(pPreviousBitmask, m_pPreviousVertexIndicesX, m_pPreviousVertexIndicesY, m_pPreviousVertexIndicesZ, m_pCurrentVertexIndicesX, m_pCurrentVertexIndicesY, m_pCurrentVertexIndicesZ);
			}

			std::swap(uNoOfNonEmptyCellsForSlice0, uNoOfNonEmptyCellsForSlice1);
			pPreviousBitmask.swap(pCurrentBitmask);
			m_pPreviousVertexIndicesX.swap(m_pCurrentVertexIndicesX);
			m_pPreviousVertexIndicesY.swap(m_pCurrentVertexIndicesY);
			m_pPreviousVertexIndicesZ.swap(m_pCurrentVertexIndicesZ);

			m_regSlicePrevious = m_regSliceCurrent;
			m_regSliceCurrent.shift(Vector3DInt16(0,0,1));
		}

		m_meshCurrent->m_Region = m_regSizeInVoxels;

		m_meshCurrent->m_vecLodRecords.clear();
		LodRecord lodRecord;
		lodRecord.beginIndex = 0;
		lodRecord.endIndex = m_meshCurrent->getNoOfIndices();
		m_meshCurrent->m_vecLodRecords.push_back(lodRecord);
	}

	template<typename VoxelType>
	template<bool isPrevZAvail>
	uint32_t SurfaceExtractor<VoxelType>::computeBitmaskForSlice(const Array2DUint8& pPreviousBitmask, Array2DUint8& pCurrentBitmask)
	{
		m_uNoOfOccupiedCells = 0;

		const uint16_t uMaxXVolSpace = m_regSliceCurrent.getUpperCorner().getX();
		const uint16_t uMaxYVolSpace = m_regSliceCurrent.getUpperCorner().getY();

		uZVolSpace = m_regSliceCurrent.getLowerCorner().getZ();
		uZRegSpace = uZVolSpace - m_regSizeInVoxels.getLowerCorner().getZ();

		//Process the lower left corner
		uYVolSpace = m_regSliceCurrent.getLowerCorner().getY();
		uXVolSpace = m_regSliceCurrent.getLowerCorner().getX();

		uXRegSpace = uXVolSpace - m_regSizeInVoxels.getLowerCorner().getX();
		uYRegSpace = uYVolSpace - m_regSizeInVoxels.getLowerCorner().getY();

		m_sampVolume.setPosition(uXVolSpace,uYVolSpace,uZVolSpace);
		computeBitmaskForCell<false, false, isPrevZAvail>(pPreviousBitmask, pCurrentBitmask);

		//Process the edge where x is minimal.
		uXVolSpace = m_regSliceCurrent.getLowerCorner().getX();
		m_sampVolume.setPosition(uXVolSpace, m_regSliceCurrent.getLowerCorner().getY(), uZVolSpace);
		for(uYVolSpace = m_regSliceCurrent.getLowerCorner().getY() + 1; uYVolSpace <= uMaxYVolSpace; uYVolSpace++)
		{
			uXRegSpace = uXVolSpace - m_regSizeInVoxels.getLowerCorner().getX();
			uYRegSpace = uYVolSpace - m_regSizeInVoxels.getLowerCorner().getY();

			m_sampVolume.movePositiveY();

			computeBitmaskForCell<false, true, isPrevZAvail>(pPreviousBitmask, pCurrentBitmask);
		}

		//Process the edge where y is minimal.
		uYVolSpace = m_regSliceCurrent.getLowerCorner().getY();
		m_sampVolume.setPosition(m_regSliceCurrent.getLowerCorner().getX(), uYVolSpace, uZVolSpace);
		for(uXVolSpace = m_regSliceCurrent.getLowerCorner().getX() + 1; uXVolSpace <= uMaxXVolSpace; uXVolSpace++)
		{	
			uXRegSpace = uXVolSpace - m_regSizeInVoxels.getLowerCorner().getX();
			uYRegSpace = uYVolSpace - m_regSizeInVoxels.getLowerCorner().getY();

			m_sampVolume.movePositiveX();

			computeBitmaskForCell<true, false, isPrevZAvail>(pPreviousBitmask, pCurrentBitmask);
		}

		//Process all remaining elemnents of the slice. In this case, previous x and y values are always available
		for(uYVolSpace = m_regSliceCurrent.getLowerCorner().getY() + 1; uYVolSpace <= uMaxYVolSpace; uYVolSpace++)
		{
			m_sampVolume.setPosition(m_regSliceCurrent.getLowerCorner().getX(), uYVolSpace, uZVolSpace);
			for(uXVolSpace = m_regSliceCurrent.getLowerCorner().getX() + 1; uXVolSpace <= uMaxXVolSpace; uXVolSpace++)
			{	
				uXRegSpace = uXVolSpace - m_regSizeInVoxels.getLowerCorner().getX();
				uYRegSpace = uYVolSpace - m_regSizeInVoxels.getLowerCorner().getY();

				m_sampVolume.movePositiveX();

				computeBitmaskForCell<true, true, isPrevZAvail>(pPreviousBitmask, pCurrentBitmask);
			}
		}

		return m_uNoOfOccupiedCells;
	}

	template<typename VoxelType>
	template<bool isPrevXAvail, bool isPrevYAvail, bool isPrevZAvail>
	void SurfaceExtractor<VoxelType>::computeBitmaskForCell(const Array2DUint8& pPreviousBitmask, Array2DUint8& pCurrentBitmask)
	{
		uint8_t iCubeIndex = 0;

		VoxelType v000;
		VoxelType v100;
		VoxelType v010;
		VoxelType v110;
		VoxelType v001;
		VoxelType v101;
		VoxelType v011;
		VoxelType v111;

		if(isPrevZAvail)
		{
			if(isPrevYAvail)
			{
				if(isPrevXAvail)
				{
					v111 = m_sampVolume.peekVoxel1px1py1pz();

					//z
					uint8_t iPreviousCubeIndexZ = pPreviousBitmask[uXRegSpace][uYRegSpace];
					iPreviousCubeIndexZ >>= 4;

					//y
					uint8_t iPreviousCubeIndexY = pCurrentBitmask[uXRegSpace][uYRegSpace-1];
					iPreviousCubeIndexY &= 192; //192 = 128 + 64
					iPreviousCubeIndexY >>= 2;

					//x
					uint8_t iPreviousCubeIndexX = pCurrentBitmask[uXRegSpace-1][uYRegSpace];
					iPreviousCubeIndexX &= 128;
					iPreviousCubeIndexX >>= 1;

					iCubeIndex = iPreviousCubeIndexX | iPreviousCubeIndexY | iPreviousCubeIndexZ;

					if (v111.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 128;
				}
				else //previous X not available
				{
					v011 = m_sampVolume.peekVoxel0px1py1pz();
					v111 = m_sampVolume.peekVoxel1px1py1pz();

					//z
					uint8_t iPreviousCubeIndexZ = pPreviousBitmask[uXRegSpace][uYRegSpace];
					iPreviousCubeIndexZ >>= 4;

					//y
					uint8_t iPreviousCubeIndexY = pCurrentBitmask[uXRegSpace][uYRegSpace-1];
					iPreviousCubeIndexY &= 192; //192 = 128 + 64
					iPreviousCubeIndexY >>= 2;

					iCubeIndex = iPreviousCubeIndexY | iPreviousCubeIndexZ;

					if (v011.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 64;
					if (v111.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 128;
				}
			}
			else //previous Y not available
			{
				if(isPrevXAvail)
				{
					v101 = m_sampVolume.peekVoxel1px0py1pz();
					v111 = m_sampVolume.peekVoxel1px1py1pz();

					//z
					uint8_t iPreviousCubeIndexZ = pPreviousBitmask[uXRegSpace][uYRegSpace];
					iPreviousCubeIndexZ >>= 4;

					//x
					uint8_t iPreviousCubeIndexX = pCurrentBitmask[uXRegSpace-1][uYRegSpace];
					iPreviousCubeIndexX &= 160; //160 = 128+32
					iPreviousCubeIndexX >>= 1;

					iCubeIndex = iPreviousCubeIndexX | iPreviousCubeIndexZ;

					if (v101.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 32;
					if (v111.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 128;
				}
				else //previous X not available
				{
					v001 = m_sampVolume.peekVoxel0px0py1pz();
					v101 = m_sampVolume.peekVoxel1px0py1pz();
					v011 = m_sampVolume.peekVoxel0px1py1pz();
					v111 = m_sampVolume.peekVoxel1px1py1pz();

					//z
					uint8_t iPreviousCubeIndexZ = pPreviousBitmask[uXRegSpace][uYRegSpace];
					iCubeIndex = iPreviousCubeIndexZ >> 4;

					if (v001.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 16;
					if (v101.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 32;
					if (v011.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 64;
					if (v111.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 128;
				}
			}
		}
		else //previous Z not available
		{
			if(isPrevYAvail)
			{
				if(isPrevXAvail)
				{
					v110 = m_sampVolume.peekVoxel1px1py0pz();
					v111 = m_sampVolume.peekVoxel1px1py1pz();

					//y
					uint8_t iPreviousCubeIndexY = pCurrentBitmask[uXRegSpace][uYRegSpace-1];
					iPreviousCubeIndexY &= 204; //204 = 128+64+8+4
					iPreviousCubeIndexY >>= 2;

					//x
					uint8_t iPreviousCubeIndexX = pCurrentBitmask[uXRegSpace-1][uYRegSpace];
					iPreviousCubeIndexX &= 170; //170 = 128+32+8+2
					iPreviousCubeIndexX >>= 1;

					iCubeIndex = iPreviousCubeIndexX | iPreviousCubeIndexY;

					if (v110.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 8;
					if (v111.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 128;
				}
				else //previous X not available
				{
					v010 = m_sampVolume.peekVoxel0px1py0pz();
					v110 = m_sampVolume.peekVoxel1px1py0pz();

					v011 = m_sampVolume.peekVoxel0px1py1pz();
					v111 = m_sampVolume.peekVoxel1px1py1pz();

					//y
					uint8_t iPreviousCubeIndexY = pCurrentBitmask[uXRegSpace][uYRegSpace-1];
					iPreviousCubeIndexY &= 204; //204 = 128+64+8+4
					iPreviousCubeIndexY >>= 2;

					iCubeIndex = iPreviousCubeIndexY;

					if (v010.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 4;
					if (v110.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 8;
					if (v011.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 64;
					if (v111.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 128;
				}
			}
			else //previous Y not available
			{
				if(isPrevXAvail)
				{
					v100 = m_sampVolume.peekVoxel1px0py0pz();
					v110 = m_sampVolume.peekVoxel1px1py0pz();

					v101 = m_sampVolume.peekVoxel1px0py1pz();
					v111 = m_sampVolume.peekVoxel1px1py1pz();

					//x
					uint8_t iPreviousCubeIndexX = pCurrentBitmask[uXRegSpace-1][uYRegSpace];
					iPreviousCubeIndexX &= 170; //170 = 128+32+8+2
					iPreviousCubeIndexX >>= 1;

					iCubeIndex = iPreviousCubeIndexX;

					if (v100.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 2;	
					if (v110.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 8;
					if (v101.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 32;
					if (v111.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 128;
				}
				else //previous X not available
				{
					v000 = m_sampVolume.getVoxel();
					v100 = m_sampVolume.peekVoxel1px0py0pz();
					v010 = m_sampVolume.peekVoxel0px1py0pz();
					v110 = m_sampVolume.peekVoxel1px1py0pz();

					v001 = m_sampVolume.peekVoxel0px0py1pz();
					v101 = m_sampVolume.peekVoxel1px0py1pz();
					v011 = m_sampVolume.peekVoxel0px1py1pz();
					v111 = m_sampVolume.peekVoxel1px1py1pz();

					if (v000.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 1;
					if (v100.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 2;
					if (v010.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 4;
					if (v110.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 8;
					if (v001.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 16;
					if (v101.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 32;
					if (v011.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 64;
					if (v111.getDensity() < VoxelType::getThreshold()) iCubeIndex |= 128;
				}
			}
		}

		//Save the bitmask
		pCurrentBitmask[uXRegSpace][uYVolSpace- m_regSizeInVoxels.getLowerCorner().getY()] = iCubeIndex;

		if(edgeTable[iCubeIndex] != 0)
		{
			++m_uNoOfOccupiedCells;
		}
	}

	template <typename VoxelType>
	void SurfaceExtractor<VoxelType>::generateVerticesForSlice(const Array2DUint8& pCurrentBitmask,
		Array2DInt32& m_pCurrentVertexIndicesX,
		Array2DInt32& m_pCurrentVertexIndicesY,
		Array2DInt32& m_pCurrentVertexIndicesZ)
	{
		uint16_t uZVolSpace = m_regSliceCurrent.getLowerCorner().getZ();
		const uint16_t uZRegSpace = uZVolSpace - m_regSizeInVoxels.getLowerCorner().getZ();
		//bool isZEdge = ((uZVolSpace == m_regSizeInVoxelsCropped.getLowerCorner().getZ()) || (uZVolSpace == m_regSizeInVoxelsCropped.getUpperCorner().getZ()));
		bool isNegZEdge = (uZVolSpace == m_regSizeInVoxels.getLowerCorner().getZ());
		bool isPosZEdge = (uZVolSpace == m_regSizeInVoxels.getUpperCorner().getZ());

		//Iterate over each cell in the region
		for(uint16_t uYVolSpace = m_regSliceCurrent.getLowerCorner().getY(); uYVolSpace <= m_regSliceCurrent.getUpperCorner().getY(); uYVolSpace++)
		{
			const uint16_t uYRegSpace = uYVolSpace - m_regSizeInVoxels.getLowerCorner().getY();
			//bool isYEdge = ((uYVolSpace == m_regSizeInVoxelsCropped.getLowerCorner().getY()) || (uYVolSpace == m_regSizeInVoxelsCropped.getUpperCorner().getY()));
			bool isNegYEdge = (uYVolSpace == m_regSizeInVoxels.getLowerCorner().getY());
			bool isPosYEdge = (uYVolSpace == m_regSizeInVoxels.getUpperCorner().getY());

			for(uint16_t uXVolSpace = m_regSliceCurrent.getLowerCorner().getX(); uXVolSpace <= m_regSliceCurrent.getUpperCorner().getX(); uXVolSpace++)
			{		
				//Current position
				const uint16_t uXRegSpace = uXVolSpace - m_regSizeInVoxels.getLowerCorner().getX();
				//bool isXEdge = ((uXVolSpace == m_regSizeInVoxelsCropped.getLowerCorner().getX()) || (uXVolSpace == m_regSizeInVoxelsCropped.getUpperCorner().getX()));
				bool isNegXEdge = (uXVolSpace == m_regSizeInVoxels.getLowerCorner().getX());
				bool isPosXEdge = (uXVolSpace == m_regSizeInVoxels.getUpperCorner().getX());

				//Determine the index into the edge table which tells us which vertices are inside of the surface
				uint8_t iCubeIndex = pCurrentBitmask[uXRegSpace][uYRegSpace];

				/* Cube is entirely in/out of the surface */
				if (edgeTable[iCubeIndex] == 0)
				{
					continue;
				}

				//Check whether the generated vertex will lie on the edge of the region


				m_sampVolume.setPosition(uXVolSpace,uYVolSpace,uZVolSpace);
				const VoxelType v000 = m_sampVolume.getVoxel();
				const Vector3DFloat n000 = computeCentralDifferenceGradient(m_sampVolume);

				/* Find the vertices where the surface intersects the cube */
				if (edgeTable[iCubeIndex] & 1)
				{
					m_sampVolume.movePositiveX();
					const VoxelType v100 = m_sampVolume.getVoxel();
					const Vector3DFloat n100 = computeCentralDifferenceGradient(m_sampVolume);

					//float fInterp = static_cast<float>(v100.getDensity() - VoxelType::getMinDensity()) / static_cast<float>(VoxelType::getMaxDensity() - VoxelType::getMinDensity());
					float fInterp = static_cast<float>(VoxelType::getThreshold() - v000.getDensity()) / static_cast<float>(v100.getDensity() - v000.getDensity());
					//fInterp = 0.5f;

					const Vector3DFloat v3dPosition(static_cast<float>(uXVolSpace - m_regSizeInVoxels.getLowerCorner().getX()) + fInterp, static_cast<float>(uYVolSpace - m_regSizeInVoxels.getLowerCorner().getY()), static_cast<float>(uZVolSpace - m_regSizeInCells.getLowerCorner().getZ()));
					//const Vector3DFloat v3dNormal(v000.getDensity() > v100.getDensity() ? 1.0f : -1.0f,0.0,0.0);

					Vector3DFloat v3dNormal = (n100*fInterp) + (n000*(1-fInterp));
					v3dNormal.normalise();

					const uint8_t uMaterial = v000.getMaterial() | v100.getMaterial(); //Because one of these is 0, the or operation takes the max.

					SurfaceVertex surfaceVertex(v3dPosition, v3dNormal, uMaterial);
					//surfaceVertex.setOnGeometryEdge(isXEdge || isYEdge || isZEdge);
					surfaceVertex.setOnGeometryEdgeNegX(isNegXEdge);
					surfaceVertex.setOnGeometryEdgePosX(isPosXEdge);
					surfaceVertex.setOnGeometryEdgeNegY(isNegYEdge);
					surfaceVertex.setOnGeometryEdgePosY(isPosYEdge);
					surfaceVertex.setOnGeometryEdgeNegZ(isNegZEdge);
					surfaceVertex.setOnGeometryEdgePosZ(isPosZEdge);
					uint32_t uLastVertexIndex = m_meshCurrent->addVertex(surfaceVertex);
					m_pCurrentVertexIndicesX[uXVolSpace - m_regSizeInVoxels.getLowerCorner().getX()][uYVolSpace - m_regSizeInVoxels.getLowerCorner().getY()] = uLastVertexIndex;
				}
				if (edgeTable[iCubeIndex] & 8)
				{
					m_sampVolume.movePositiveY();
					const VoxelType v010 = m_sampVolume.getVoxel();
					const Vector3DFloat n010 = computeCentralDifferenceGradient(m_sampVolume);

					float fInterp = static_cast<float>(VoxelType::getThreshold() - v000.getDensity()) / static_cast<float>(v010.getDensity() - v000.getDensity());
					//fInterp = 0.5f;

					const Vector3DFloat v3dPosition(static_cast<float>(uXVolSpace - m_regSizeInVoxels.getLowerCorner().getX()), static_cast<float>(uYVolSpace - m_regSizeInVoxels.getLowerCorner().getY()) + fInterp, static_cast<float>(uZVolSpace - m_regSizeInVoxels.getLowerCorner().getZ()));
					//const Vector3DFloat v3dNormal(0.0,v000.getDensity() > v010.getDensity() ? 1.0f : -1.0f,0.0);

					Vector3DFloat v3dNormal = (n010*fInterp) + (n000*(1-fInterp));
					v3dNormal.normalise();

					const uint8_t uMaterial = v000.getMaterial() | v010.getMaterial(); //Because one of these is 0, the or operation takes the max.

					SurfaceVertex surfaceVertex(v3dPosition, v3dNormal, uMaterial);
					//surfaceVertex.setOnGeometryEdge(isXEdge || isYEdge || isZEdge);
					surfaceVertex.setOnGeometryEdgeNegX(isNegXEdge);
					surfaceVertex.setOnGeometryEdgePosX(isPosXEdge);
					surfaceVertex.setOnGeometryEdgeNegY(isNegYEdge);
					surfaceVertex.setOnGeometryEdgePosY(isPosYEdge);
					surfaceVertex.setOnGeometryEdgeNegZ(isNegZEdge);
					surfaceVertex.setOnGeometryEdgePosZ(isPosZEdge);
					uint32_t uLastVertexIndex = m_meshCurrent->addVertex(surfaceVertex);
					m_pCurrentVertexIndicesY[uXVolSpace - m_regSizeInVoxels.getLowerCorner().getX()][uYVolSpace - m_regSizeInVoxels.getLowerCorner().getY()] = uLastVertexIndex;
				}
				if (edgeTable[iCubeIndex] & 256)
				{
					m_sampVolume.movePositiveZ();
					const VoxelType v001 = m_sampVolume.getVoxel();
					const Vector3DFloat n001 = computeCentralDifferenceGradient(m_sampVolume);

					float fInterp = static_cast<float>(VoxelType::getThreshold() - v000.getDensity()) / static_cast<float>(v001.getDensity() - v000.getDensity());
					//fInterp = 0.5f;

					const Vector3DFloat v3dPosition(static_cast<float>(uXVolSpace - m_regSizeInVoxels.getLowerCorner().getX()), static_cast<float>(uYVolSpace - m_regSizeInVoxels.getLowerCorner().getY()), static_cast<float>(uZVolSpace - m_regSizeInVoxels.getLowerCorner().getZ()) + fInterp);
					//const Vector3DFloat v3dNormal(0.0,0.0,v000.getDensity() > v001.getDensity() ? 1.0f : -1.0f);

					Vector3DFloat v3dNormal = (n001*fInterp) + (n000*(1-fInterp));
					v3dNormal.normalise();

					const uint8_t uMaterial = v000.getMaterial() | v001.getMaterial(); //Because one of these is 0, the or operation takes the max.

					SurfaceVertex surfaceVertex(v3dPosition, v3dNormal, uMaterial);
					//surfaceVertex.setOnGeometryEdge(isXEdge || isYEdge || isZEdge);
					surfaceVertex.setOnGeometryEdgeNegX(isNegXEdge);
					surfaceVertex.setOnGeometryEdgePosX(isPosXEdge);
					surfaceVertex.setOnGeometryEdgeNegY(isNegYEdge);
					surfaceVertex.setOnGeometryEdgePosY(isPosYEdge);
					surfaceVertex.setOnGeometryEdgeNegZ(isNegZEdge);
					surfaceVertex.setOnGeometryEdgePosZ(isPosZEdge);
					uint32_t uLastVertexIndex = m_meshCurrent->addVertex(surfaceVertex);
					m_pCurrentVertexIndicesZ[uXVolSpace - m_regSizeInVoxels.getLowerCorner().getX()][uYVolSpace - m_regSizeInVoxels.getLowerCorner().getY()] = uLastVertexIndex;
				}
			}//For each cell
		}
	}

	template <typename VoxelType>
	Vector3DFloat SurfaceExtractor<VoxelType>::computeCentralDifferenceGradient(const VolumeSampler<VoxelType>& volIter)
	{
		uint8_t voxel1nx = volIter.peekVoxel1nx0py0pz().getDensity();
		uint8_t voxel1px = volIter.peekVoxel1px0py0pz().getDensity();

		uint8_t voxel1ny = volIter.peekVoxel0px1ny0pz().getDensity();
		uint8_t voxel1py = volIter.peekVoxel0px1py0pz().getDensity();

		uint8_t voxel1nz = volIter.peekVoxel0px0py1nz().getDensity();
		uint8_t voxel1pz = volIter.peekVoxel0px0py1pz().getDensity();

		return Vector3DFloat
		(
			static_cast<float>(voxel1nx) - static_cast<float>(voxel1px),
			static_cast<float>(voxel1ny) - static_cast<float>(voxel1py),
			static_cast<float>(voxel1nz) - static_cast<float>(voxel1pz)
		);
	}

	template <typename VoxelType>
	Vector3DFloat SurfaceExtractor<VoxelType>::computeSobelGradient(const VolumeSampler<VoxelType>& volIter)
	{
		static const int weights[3][3][3] = {  {  {2,3,2}, {3,6,3}, {2,3,2}  },  {
			{3,6,3},  {6,0,6},  {3,6,3} },  { {2,3,2},  {3,6,3},  {2,3,2} } };

			const uint8_t pVoxel1nx1ny1nz = volIter.peekVoxel1nx1ny1nz().getDensity();
			const uint8_t pVoxel1nx1ny0pz = volIter.peekVoxel1nx1ny0pz().getDensity();
			const uint8_t pVoxel1nx1ny1pz = volIter.peekVoxel1nx1ny1pz().getDensity();
			const uint8_t pVoxel1nx0py1nz = volIter.peekVoxel1nx0py1nz().getDensity();
			const uint8_t pVoxel1nx0py0pz = volIter.peekVoxel1nx0py0pz().getDensity();
			const uint8_t pVoxel1nx0py1pz = volIter.peekVoxel1nx0py1pz().getDensity();
			const uint8_t pVoxel1nx1py1nz = volIter.peekVoxel1nx1py1nz().getDensity();
			const uint8_t pVoxel1nx1py0pz = volIter.peekVoxel1nx1py0pz().getDensity();
			const uint8_t pVoxel1nx1py1pz = volIter.peekVoxel1nx1py1pz().getDensity();

			const uint8_t pVoxel0px1ny1nz = volIter.peekVoxel0px1ny1nz().getDensity();
			const uint8_t pVoxel0px1ny0pz = volIter.peekVoxel0px1ny0pz().getDensity();
			const uint8_t pVoxel0px1ny1pz = volIter.peekVoxel0px1ny1pz().getDensity();
			const uint8_t pVoxel0px0py1nz = volIter.peekVoxel0px0py1nz().getDensity();
			//const uint8_t pVoxel0px0py0pz = volIter.peekVoxel0px0py0pz().getDensity();
			const uint8_t pVoxel0px0py1pz = volIter.peekVoxel0px0py1pz().getDensity();
			const uint8_t pVoxel0px1py1nz = volIter.peekVoxel0px1py1nz().getDensity();
			const uint8_t pVoxel0px1py0pz = volIter.peekVoxel0px1py0pz().getDensity();
			const uint8_t pVoxel0px1py1pz = volIter.peekVoxel0px1py1pz().getDensity();

			const uint8_t pVoxel1px1ny1nz = volIter.peekVoxel1px1ny1nz().getDensity();
			const uint8_t pVoxel1px1ny0pz = volIter.peekVoxel1px1ny0pz().getDensity();
			const uint8_t pVoxel1px1ny1pz = volIter.peekVoxel1px1ny1pz().getDensity();
			const uint8_t pVoxel1px0py1nz = volIter.peekVoxel1px0py1nz().getDensity();
			const uint8_t pVoxel1px0py0pz = volIter.peekVoxel1px0py0pz().getDensity();
			const uint8_t pVoxel1px0py1pz = volIter.peekVoxel1px0py1pz().getDensity();
			const uint8_t pVoxel1px1py1nz = volIter.peekVoxel1px1py1nz().getDensity();
			const uint8_t pVoxel1px1py0pz = volIter.peekVoxel1px1py0pz().getDensity();
			const uint8_t pVoxel1px1py1pz = volIter.peekVoxel1px1py1pz().getDensity();

			const int xGrad(- weights[0][0][0] * pVoxel1nx1ny1nz -
				weights[1][0][0] * pVoxel1nx1ny0pz - weights[2][0][0] *
				pVoxel1nx1ny1pz - weights[0][1][0] * pVoxel1nx0py1nz -
				weights[1][1][0] * pVoxel1nx0py0pz - weights[2][1][0] *
				pVoxel1nx0py1pz - weights[0][2][0] * pVoxel1nx1py1nz -
				weights[1][2][0] * pVoxel1nx1py0pz - weights[2][2][0] *
				pVoxel1nx1py1pz + weights[0][0][2] * pVoxel1px1ny1nz +
				weights[1][0][2] * pVoxel1px1ny0pz + weights[2][0][2] *
				pVoxel1px1ny1pz + weights[0][1][2] * pVoxel1px0py1nz +
				weights[1][1][2] * pVoxel1px0py0pz + weights[2][1][2] *
				pVoxel1px0py1pz + weights[0][2][2] * pVoxel1px1py1nz +
				weights[1][2][2] * pVoxel1px1py0pz + weights[2][2][2] *
				pVoxel1px1py1pz);

			const int yGrad(- weights[0][0][0] * pVoxel1nx1ny1nz -
				weights[1][0][0] * pVoxel1nx1ny0pz - weights[2][0][0] *
				pVoxel1nx1ny1pz + weights[0][2][0] * pVoxel1nx1py1nz +
				weights[1][2][0] * pVoxel1nx1py0pz + weights[2][2][0] *
				pVoxel1nx1py1pz - weights[0][0][1] * pVoxel0px1ny1nz -
				weights[1][0][1] * pVoxel0px1ny0pz - weights[2][0][1] *
				pVoxel0px1ny1pz + weights[0][2][1] * pVoxel0px1py1nz +
				weights[1][2][1] * pVoxel0px1py0pz + weights[2][2][1] *
				pVoxel0px1py1pz - weights[0][0][2] * pVoxel1px1ny1nz -
				weights[1][0][2] * pVoxel1px1ny0pz - weights[2][0][2] *
				pVoxel1px1ny1pz + weights[0][2][2] * pVoxel1px1py1nz +
				weights[1][2][2] * pVoxel1px1py0pz + weights[2][2][2] *
				pVoxel1px1py1pz);

			const int zGrad(- weights[0][0][0] * pVoxel1nx1ny1nz +
				weights[2][0][0] * pVoxel1nx1ny1pz - weights[0][1][0] *
				pVoxel1nx0py1nz + weights[2][1][0] * pVoxel1nx0py1pz -
				weights[0][2][0] * pVoxel1nx1py1nz + weights[2][2][0] *
				pVoxel1nx1py1pz - weights[0][0][1] * pVoxel0px1ny1nz +
				weights[2][0][1] * pVoxel0px1ny1pz - weights[0][1][1] *
				pVoxel0px0py1nz + weights[2][1][1] * pVoxel0px0py1pz -
				weights[0][2][1] * pVoxel0px1py1nz + weights[2][2][1] *
				pVoxel0px1py1pz - weights[0][0][2] * pVoxel1px1ny1nz +
				weights[2][0][2] * pVoxel1px1ny1pz - weights[0][1][2] *
				pVoxel1px0py1nz + weights[2][1][2] * pVoxel1px0py1pz -
				weights[0][2][2] * pVoxel1px1py1nz + weights[2][2][2] *
				pVoxel1px1py1pz);

			//Note: The above actually give gradients going from low density to high density.
			//For our normals we want the the other way around, so we switch the components as we return them.
			return Vector3DFloat(static_cast<float>(-xGrad),static_cast<float>(-yGrad),static_cast<float>(-zGrad));
	}

	template <typename VoxelType>
	void SurfaceExtractor<VoxelType>::generateIndicesForSlice(const Array2DUint8& pPreviousBitmask,
		const Array2DInt32& m_pPreviousVertexIndicesX,
		const Array2DInt32& m_pPreviousVertexIndicesY,
		const Array2DInt32& m_pPreviousVertexIndicesZ,
		const Array2DInt32& m_pCurrentVertexIndicesX,
		const Array2DInt32& m_pCurrentVertexIndicesY,
		const Array2DInt32& m_pCurrentVertexIndicesZ)
	{
		int32_t indlist[12];
		for(int i = 0; i < 12; i++)
		{
			indlist[i] = -1;
		}

		for(uint16_t uYVolSpace = m_regSlicePrevious.getLowerCorner().getY(); uYVolSpace <= m_regSizeInCells.getUpperCorner().getY(); uYVolSpace++)
		{
			for(uint16_t uXVolSpace = m_regSlicePrevious.getLowerCorner().getX(); uXVolSpace <= m_regSizeInCells.getUpperCorner().getX(); uXVolSpace++)
			{		
				uint16_t uZVolSpace = m_regSlicePrevious.getLowerCorner().getZ();
				m_sampVolume.setPosition(uXVolSpace,uYVolSpace,uZVolSpace);	

				//Current position
				const uint16_t uXRegSpace = m_sampVolume.getPosX() - m_regSizeInVoxels.getLowerCorner().getX();
				const uint16_t uYRegSpace = m_sampVolume.getPosY() - m_regSizeInVoxels.getLowerCorner().getY();
				const uint16_t uZRegSpace = m_sampVolume.getPosZ() - m_regSizeInVoxels.getLowerCorner().getZ();

				//Determine the index into the edge table which tells us which vertices are inside of the surface
				uint8_t iCubeIndex = pPreviousBitmask[uXRegSpace][uYRegSpace];

				/* Cube is entirely in/out of the surface */
				if (edgeTable[iCubeIndex] == 0)
				{
					continue;
				}

				/* Find the vertices where the surface intersects the cube */
				if (edgeTable[iCubeIndex] & 1)
				{
					indlist[0] = m_pPreviousVertexIndicesX[uXRegSpace][uYRegSpace];
					//assert(indlist[0] != -1);
				}
				if (edgeTable[iCubeIndex] & 2)
				{
					indlist[1] = m_pPreviousVertexIndicesY[uXRegSpace+1][uYRegSpace];
					//assert(indlist[1] != -1);
				}
				if (edgeTable[iCubeIndex] & 4)
				{
					indlist[2] = m_pPreviousVertexIndicesX[uXRegSpace][uYRegSpace+1];
					//assert(indlist[2] != -1);
				}
				if (edgeTable[iCubeIndex] & 8)
				{
					indlist[3] = m_pPreviousVertexIndicesY[uXRegSpace][uYRegSpace];
					//assert(indlist[3] != -1);
				}
				if (edgeTable[iCubeIndex] & 16)
				{
					indlist[4] = m_pCurrentVertexIndicesX[uXRegSpace][uYRegSpace];
					//assert(indlist[4] != -1);
				}
				if (edgeTable[iCubeIndex] & 32)
				{
					indlist[5] = m_pCurrentVertexIndicesY[uXRegSpace+1][uYRegSpace];
					//assert(indlist[5] != -1);
				}
				if (edgeTable[iCubeIndex] & 64)
				{
					indlist[6] = m_pCurrentVertexIndicesX[uXRegSpace][uYRegSpace+1];
					//assert(indlist[6] != -1);
				}
				if (edgeTable[iCubeIndex] & 128)
				{
					indlist[7] = m_pCurrentVertexIndicesY[uXRegSpace][uYRegSpace];
					//assert(indlist[7] != -1);
				}
				if (edgeTable[iCubeIndex] & 256)
				{
					indlist[8] = m_pPreviousVertexIndicesZ[uXRegSpace][uYRegSpace];
					//assert(indlist[8] != -1);
				}
				if (edgeTable[iCubeIndex] & 512)
				{
					indlist[9] = m_pPreviousVertexIndicesZ[uXRegSpace+1][uYRegSpace];
					//assert(indlist[9] != -1);
				}
				if (edgeTable[iCubeIndex] & 1024)
				{
					indlist[10] = m_pPreviousVertexIndicesZ[uXRegSpace+1][uYRegSpace+1];
					//assert(indlist[10] != -1);
				}
				if (edgeTable[iCubeIndex] & 2048)
				{
					indlist[11] = m_pPreviousVertexIndicesZ[uXRegSpace][uYRegSpace+1];
					//assert(indlist[11] != -1);
				}

				for (int i=0;triTable[iCubeIndex][i]!=-1;i+=3)
				{
					int32_t ind0 = indlist[triTable[iCubeIndex][i  ]];
					int32_t ind1 = indlist[triTable[iCubeIndex][i+1]];
					int32_t ind2 = indlist[triTable[iCubeIndex][i+2]];

					if((ind0 != -1) && (ind1 != -1) && (ind2 != -1))
					{
						assert(ind0 >= 0);
						assert(ind1 >= 0);
						assert(ind2 >= 0);

						assert(ind0 < 1000000);
						assert(ind1 < 1000000);
						assert(ind2 < 1000000);
						m_meshCurrent->addTriangle(ind0, ind1, ind2);
					}
				}//For each triangle
			}//For each cell
		}
	}
}
