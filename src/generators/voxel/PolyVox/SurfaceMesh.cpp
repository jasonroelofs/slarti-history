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

#include "PolyVox/SurfaceMesh.h"

#include <cstdlib>
#include <list>
#include <algorithm>

using namespace std;

namespace PolyVox
{
	SurfaceMesh::SurfaceMesh()
	{
		m_iTimeStamp = -1;
	}

	SurfaceMesh::~SurfaceMesh()	  
	{
	}

	const std::vector<uint32_t>& SurfaceMesh::getIndices(void) const
	{
		return m_vecTriangleIndices;
	}

	uint32_t SurfaceMesh::getNoOfIndices(void) const
	{
		return m_vecTriangleIndices.size();
	}	

	uint32_t SurfaceMesh::getNoOfNonUniformTrianges(void) const
	{
		uint32_t result = 0;
		for(uint32_t i = 0; i < m_vecTriangleIndices.size() - 2; i += 3)
		{
			if((m_vecVertices[m_vecTriangleIndices[i]].getMaterial() == m_vecVertices[m_vecTriangleIndices[i+1]].getMaterial())
			&& (m_vecVertices[m_vecTriangleIndices[i]].getMaterial() == m_vecVertices[m_vecTriangleIndices[i+2]].getMaterial()))
			{
			}
			else
			{
				result++;
			}
		}
		return result;
	}

	uint32_t SurfaceMesh::getNoOfUniformTrianges(void) const
	{
		uint32_t result = 0;
		for(uint32_t i = 0; i < m_vecTriangleIndices.size() - 2; i += 3)
		{
			if((m_vecVertices[m_vecTriangleIndices[i]].getMaterial() == m_vecVertices[m_vecTriangleIndices[i+1]].getMaterial())
			&& (m_vecVertices[m_vecTriangleIndices[i]].getMaterial() == m_vecVertices[m_vecTriangleIndices[i+2]].getMaterial()))
			{
				result++;
			}
		}
		return result;
	}

	uint32_t SurfaceMesh::getNoOfVertices(void) const
	{
		return m_vecVertices.size();
	}

	std::vector<SurfaceVertex>& SurfaceMesh::getRawVertexData(void)
	{
		return m_vecVertices;
	}

	const std::vector<SurfaceVertex>& SurfaceMesh::getVertices(void) const
	{
		return m_vecVertices;
	}		

	void SurfaceMesh::addTriangle(uint32_t index0, uint32_t index1, uint32_t index2)
	{
		m_vecTriangleIndices.push_back(index0);
		m_vecTriangleIndices.push_back(index1);
		m_vecTriangleIndices.push_back(index2);

		if((m_vecVertices[index0].material == m_vecVertices[index1].material) && (m_vecVertices[index0].material == m_vecVertices[index2].material))
		{
			m_mapUsedMaterials.insert(m_vecVertices[index0].material);			
		}
		else
		{
			m_vecVertices[index0].setOnMaterialEdge(true);
			m_vecVertices[index1].setOnMaterialEdge(true);
			m_vecVertices[index2].setOnMaterialEdge(true);
		}
	}

	uint32_t SurfaceMesh::addVertex(const SurfaceVertex& vertex)
	{
		m_vecVertices.push_back(vertex);
		return m_vecVertices.size() - 1;
	}

	void SurfaceMesh::clear(void)
	{
		m_vecVertices.clear();
		m_vecTriangleIndices.clear();
		m_vecLodRecords.clear();
		m_mapUsedMaterials.clear();
	}

	const bool SurfaceMesh::isEmpty(void) const
	{
		return (getNoOfVertices() == 0) || (getNoOfIndices() == 0);
	}

	////////////////////////////////////////////////////////////////////////////////
	/// The function works on a per triangle basis without any need for connectivity
	/// information. It determines whether a triangle is lying on a flat or curved
	/// section of the surface patch by examining the normals - therefore these
	/// normals must hve been set to something sensible before this functions is called.
	/// \param fAmount A factor controlling how much the vertices move by. Find a good
	/// value by experimentation, starting with something small such as 0.1f.
	/// \param bIncludeGeometryEdgeVertices Indicates whether vertices on the edge of an
	/// SurfaceMesh should be smoothed. This can cause dicontinuities between
	/// neighbouring patches.
	////////////////////////////////////////////////////////////////////////////////
	void SurfaceMesh::smoothPositions(float fAmount, bool bIncludeGeometryEdgeVertices)
	{
		if(m_vecVertices.size() == 0) //FIXME - I don't think we should need this test, but I have seen crashes otherwise...
		{
			return;
		}

		//This will hold the new positions, and is initialised with the current positions.
		std::vector<Vector3DFloat> newPositions(m_vecVertices.size());
		for(uint32_t uIndex = 0; uIndex < newPositions.size(); uIndex++)
		{
			newPositions[uIndex] = m_vecVertices[uIndex].getPosition();
		}

		//Iterate over each triangle
		for(vector<uint32_t>::iterator iterIndex = m_vecTriangleIndices.begin(); iterIndex != m_vecTriangleIndices.end();)
		{
			//Get the vertex data for the triangle
			SurfaceVertex& v0 = m_vecVertices[*iterIndex];
			Vector3DFloat& v0New = newPositions[*iterIndex];
			iterIndex++;
			SurfaceVertex& v1 = m_vecVertices[*iterIndex];
			Vector3DFloat& v1New = newPositions[*iterIndex];
			iterIndex++;
			SurfaceVertex& v2 = m_vecVertices[*iterIndex];
			Vector3DFloat& v2New = newPositions[*iterIndex];
			iterIndex++;

			//Find the midpoint
			Vector3DFloat v3dMidpoint = (v0.position + v1.position + v2.position) / 3.0f;

			//Vectors from vertex to midpoint
			Vector3DFloat v0ToMidpoint = v3dMidpoint - v0.position;
			Vector3DFloat v1ToMidpoint = v3dMidpoint - v1.position;
			Vector3DFloat v2ToMidpoint = v3dMidpoint - v2.position;
			
			//Get the vertex normals
			Vector3DFloat n0 = v0.getNormal();
			Vector3DFloat n1 = v1.getNormal();			
			Vector3DFloat n2 = v2.getNormal();				

			//I don't think these normalisation are necessary... and could be slow.
			//Normals should be normalised anyway, and as long as all triangles are
			//about the same size the distances to midpoint should be similar too.
			//v0ToMidpoint.normalise();
			//v1ToMidpoint.normalise();
			//v2ToMidpoint.normalise();
			//n0.normalise();
			//n1.normalise();
			//n2.normalise();
			
			//If the dot product is zero the the normals are perpendicular
			//to the triangle, hence the positions do not move.
			v0New += (n0 * (n0.dot(v0ToMidpoint)) * fAmount);
			v1New += (n1 * (n1.dot(v1ToMidpoint)) * fAmount);
			v2New += (n2 * (n2.dot(v2ToMidpoint)) * fAmount);
		}

		//Update with the new positions
		for(uint32_t uIndex = 0; uIndex < newPositions.size(); uIndex++)
		{
			if((bIncludeGeometryEdgeVertices) || (m_vecVertices[uIndex].isOnGeometryEdge() == false))
			{
				m_vecVertices[uIndex].setPosition(newPositions[uIndex]);
			}
		}
	}	

	////////////////////////////////////////////////////////////////////////////////
	/// This function can help improve the visual appearance of a surface patch by
	/// smoothing normals with other nearby normals. It iterates over each triangle
	/// in the surface patch and determines the sum of its corners normals. For any
	/// given vertex, these sums are in turn summed for any triangles which use the
	/// vertex. Usually, the resulting normals should be renormalised afterwards.
	/// Note: This function can cause lighting discontinuities accross region boundaries.
	////////////////////////////////////////////////////////////////////////////////
	void SurfaceMesh::sumNearbyNormals(bool bNormaliseResult)
	{
		if(m_vecVertices.size() == 0) //FIXME - I don't think we should need this test, but I have seen crashes otherwise...
		{
			return;
		}

		std::vector<Vector3DFloat> summedNormals(m_vecVertices.size());

		//Initialise all normals to zero. Should be ok as the vector should store all elements contiguously.
		memset(&summedNormals[0], 0, summedNormals.size() * sizeof(Vector3DFloat));

		for(vector<uint32_t>::iterator iterIndex = m_vecTriangleIndices.begin(); iterIndex != m_vecTriangleIndices.end();)
		{
			SurfaceVertex& v0 = m_vecVertices[*iterIndex];
			Vector3DFloat& v0New = summedNormals[*iterIndex];
			iterIndex++;
			SurfaceVertex& v1 = m_vecVertices[*iterIndex];
			Vector3DFloat& v1New = summedNormals[*iterIndex];
			iterIndex++;
			SurfaceVertex& v2 = m_vecVertices[*iterIndex];
			Vector3DFloat& v2New = summedNormals[*iterIndex];
			iterIndex++;

			Vector3DFloat sumOfNormals = v0.getNormal() + v1.getNormal() + v2.getNormal();

			v0New += sumOfNormals;
			v1New += sumOfNormals;
			v2New += sumOfNormals;
		}

		for(uint32_t uIndex = 0; uIndex < summedNormals.size(); uIndex++)
		{
			if(bNormaliseResult)
			{
				summedNormals[uIndex].normalise();
			}
			m_vecVertices[uIndex].setNormal(summedNormals[uIndex]);
		}
	}

	void SurfaceMesh::generateAveragedFaceNormals(bool bNormalise, bool bIncludeEdgeVertices)
	{
		Vector3DFloat offset = static_cast<Vector3DFloat>(m_Region.getLowerCorner());

		//Initially zero the normals
		for(vector<SurfaceVertex>::iterator iterVertex = m_vecVertices.begin(); iterVertex != m_vecVertices.end(); iterVertex++)
		{
			if(m_Region.containsPoint(iterVertex->getPosition() + offset, 0.001))
			{
				iterVertex->setNormal(Vector3DFloat(0.0f,0.0f,0.0f));
			}
		}

		for(vector<uint32_t>::iterator iterIndex = m_vecTriangleIndices.begin(); iterIndex != m_vecTriangleIndices.end();)
		{
			SurfaceVertex& v0 = m_vecVertices[*iterIndex];
			iterIndex++;
			SurfaceVertex& v1 = m_vecVertices[*iterIndex];
			iterIndex++;
			SurfaceVertex& v2 = m_vecVertices[*iterIndex];
			iterIndex++;

			Vector3DFloat triangleNormal = (v1.getPosition()-v0.getPosition()).cross(v2.getPosition()-v0.getPosition());

			if(m_Region.containsPoint(v0.getPosition() + offset, 0.001))
			{
				v0.setNormal(v0.getNormal() + triangleNormal);
			}
			if(m_Region.containsPoint(v1.getPosition() + offset, 0.001))
			{
				v1.setNormal(v1.getNormal() + triangleNormal);
			}
			if(m_Region.containsPoint(v2.getPosition() + offset, 0.001))
			{
				v2.setNormal(v2.getNormal() + triangleNormal);
			}
		}

		if(bNormalise)
		{
			for(vector<SurfaceVertex>::iterator iterVertex = m_vecVertices.begin(); iterVertex != m_vecVertices.end(); iterVertex++)
			{
				Vector3DFloat normal = iterVertex->getNormal();
				normal.normalise();
				iterVertex->setNormal(normal);
			}
		}
	}

	//This function looks at every vertex in the mesh and determines
	//how many of it's neighbours have the same material.
	void SurfaceMesh::countNoOfNeighboursUsingMaterial(void)
	{
		//Find all the neighbouring vertices for each vertex
		std::vector< std::set<int> > neighbouringVertices(m_vecVertices.size());
		for(int triCt = 0; triCt < m_vecTriangleIndices.size() / 3; triCt++)
		{
			int v0 = m_vecTriangleIndices[(triCt * 3 + 0)];
			int v1 = m_vecTriangleIndices[(triCt * 3 + 1)];
			int v2 = m_vecTriangleIndices[(triCt * 3 + 2)];

			neighbouringVertices[v0].insert(v1);
			neighbouringVertices[v0].insert(v2);

			neighbouringVertices[v1].insert(v0);
			neighbouringVertices[v1].insert(v2);

			neighbouringVertices[v2].insert(v0);
			neighbouringVertices[v2].insert(v1);
		}

		//For each vertex, check how many neighbours have the same material
		m_vecNoOfNeighboursUsingMaterial.resize(m_vecVertices.size());
		for(int vertCt = 0; vertCt < m_vecVertices.size(); vertCt++)
		{
			m_vecNoOfNeighboursUsingMaterial[vertCt] = 0;
			for(std::set<int>::iterator iter = neighbouringVertices[vertCt].begin(); iter != neighbouringVertices[vertCt].end(); iter++)
			{
				if(m_vecVertices[vertCt].getMaterial() == m_vecVertices[*iter].getMaterial())
				{
					m_vecNoOfNeighboursUsingMaterial[vertCt]++;
				}
			}
		}
	}

	polyvox_shared_ptr<SurfaceMesh> SurfaceMesh::extractSubset(std::set<uint8_t> setMaterials)
	{
		polyvox_shared_ptr<SurfaceMesh> result(new SurfaceMesh);

		if(m_vecVertices.size() == 0) //FIXME - I don't think we should need this test, but I have seen crashes otherwise...
		{
			return result;
		}

		assert(m_vecLodRecords.size() == 1);
		if(m_vecLodRecords.size() != 1)
		{
			//If we have done progressive LOD then it's too late to split into subsets.
			return result;
		}

		std::vector<int32_t> indexMap(m_vecVertices.size());
		std::fill(indexMap.begin(), indexMap.end(), -1);

		for(uint32_t triCt = 0; triCt < m_vecTriangleIndices.size(); triCt += 3)
		{

			SurfaceVertex& v0 = m_vecVertices[m_vecTriangleIndices[triCt]];
			SurfaceVertex& v1 = m_vecVertices[m_vecTriangleIndices[triCt + 1]];
			SurfaceVertex& v2 = m_vecVertices[m_vecTriangleIndices[triCt + 2]];

			if(
				(setMaterials.find(v0.getMaterial()) != setMaterials.end()) || 
				(setMaterials.find(v1.getMaterial()) != setMaterials.end()) || 
				(setMaterials.find(v2.getMaterial()) != setMaterials.end()))
			{
				uint32_t i0;
				if(indexMap[m_vecTriangleIndices[triCt]] == -1)
				{
					indexMap[m_vecTriangleIndices[triCt]] = result->addVertex(v0);
				}
				i0 = indexMap[m_vecTriangleIndices[triCt]];

				uint32_t i1;
				if(indexMap[m_vecTriangleIndices[triCt+1]] == -1)
				{
					indexMap[m_vecTriangleIndices[triCt+1]] = result->addVertex(v1);
				}
				i1 = indexMap[m_vecTriangleIndices[triCt+1]];

				uint32_t i2;
				if(indexMap[m_vecTriangleIndices[triCt+2]] == -1)
				{
					indexMap[m_vecTriangleIndices[triCt+2]] = result->addVertex(v2);
				}
				i2 = indexMap[m_vecTriangleIndices[triCt+2]];

				result->addTriangle(i0,i1,i2);
			}
		}

		result->m_vecLodRecords.clear();
		LodRecord lodRecord;
		lodRecord.beginIndex = 0;
		lodRecord.endIndex = result->getNoOfIndices();
		result->m_vecLodRecords.push_back(lodRecord);

		return result;
	}

	/*int SurfaceMesh::countMaterialBoundary(void)
	{
		int count = 0;
		for(int ct = 0; ct < m_vecVertices.size(); ct++)
		{
			if(m_vecVertices[ct].m_bIsMaterialEdgeVertex)
			{
				count++;
			}
		}
		return count;
	}

	void SurfaceMesh::growMaterialBoundary(void)
	{
		std::vector<SurfaceVertex> vecNewVertices = m_vecVertices;

		for(vector<uint32_t>::iterator iterIndex = m_vecTriangleIndices.begin(); iterIndex != m_vecTriangleIndices.end();)
		{
			SurfaceVertex& v0 = m_vecVertices[*iterIndex];
			SurfaceVertex& v0New = vecNewVertices[*iterIndex];
			iterIndex++;
			SurfaceVertex& v1 = m_vecVertices[*iterIndex];
			SurfaceVertex& v1New = vecNewVertices[*iterIndex];
			iterIndex++;
			SurfaceVertex& v2 = m_vecVertices[*iterIndex];
			SurfaceVertex& v2New = vecNewVertices[*iterIndex];
			iterIndex++;

			if(v0.m_bIsMaterialEdgeVertex || v1.m_bIsMaterialEdgeVertex || v2.m_bIsMaterialEdgeVertex)
			{
				v0New.m_bIsMaterialEdgeVertex = true;
				v1New.m_bIsMaterialEdgeVertex = true;
				v2New.m_bIsMaterialEdgeVertex = true;
			}
		}

		m_vecVertices = vecNewVertices;
	}*/

	void SurfaceMesh::decimate(float fMinDotProductForCollapse)
	{
		// We will need the information from this function to
		// determine when material boundary edges can collapse.
		countNoOfNeighboursUsingMaterial();

		uint32_t noOfEdgesCollapsed;
		do
		{
			noOfEdgesCollapsed = performDecimationPass(fMinDotProductForCollapse);
			removeDegenerateTris();			
		}while(noOfEdgesCollapsed > 0);

		//Decimation will have invalidated LOD levels.
		m_vecLodRecords.clear();
		LodRecord lodRecord;
		lodRecord.beginIndex = 0;
		lodRecord.endIndex = getNoOfIndices();
		m_vecLodRecords.push_back(lodRecord);
	}

	// Returns true if every bit which is set in 'a' is also set in 'b'. The reverse does not need to be true.
	bool SurfaceMesh::isSubset(std::bitset<VF_NO_OF_FLAGS> a, std::bitset<VF_NO_OF_FLAGS> b)
	{
		bool result = true;

		for(int ct = 1; ct < 7; ct++) //Start at '1' to skip material flag
		{
			if(a.test(ct))
			{
				if(b.test(ct) == false)
				{
					result = false;
					break;
				}
			}
		}

		return result;
	}

	uint32_t SurfaceMesh::performDecimationPass(float fMinDotProductForCollapse)
	{
		// I'm using a vector of lists here, rather than a vector of sets,
		// because I don't believe that duplicaes should occur. But this
		// might be worth checking if we have problems in the future.
		vector< list<uint32_t> > trianglesUsingVertex(m_vecVertices.size());
		for(int ct = 0; ct < m_vecTriangleIndices.size(); ct++)
		{
			int triangle = ct / 3;

			trianglesUsingVertex[m_vecTriangleIndices[ct]].push_back(triangle);
		}

		// Count how many edges we have collapsed
		uint32_t noOfEdgesCollapsed = 0;

		// The vertex mapper track whick vertices collapse onto which.
		vector<uint32_t> vertexMapper(m_vecVertices.size());

		// Once a vertex is involved in a collapse (either because it
		// moves onto a different vertex, or because a different vertex
		// moves onto it) it is forbidden to take part in another collapse
		// this pass. We enforce this by setting the vertex locked flag.
		vector<bool> vertexLocked(m_vecVertices.size());

		// Initialise the vectors
		for(uint32_t ct = 0; ct < m_vecVertices.size(); ct++)
		{
			// Initiall all vertices points to themselves
			vertexMapper[ct] = ct;
			// All vertices are initially unlocked
			vertexLocked[ct] = false;
		}

		
		// Each triangle exists in this vector once.
		vector<int> vecOfTriCts(m_vecTriangleIndices.size() / 3);
		for(int triCt = 0; triCt < vecOfTriCts.size(); triCt++)
		{
			vecOfTriCts[triCt] = triCt;
		}

		// It *may* be beneficial to randomise the order in which triangles
		// are processed to get a more uniform distribution off collapses and 
		// more equally sized triangles at the end. This need more testing really.
		random_shuffle(vecOfTriCts.begin(), vecOfTriCts.end());

		//For each triange...
		for(int ctIter = 0; ctIter < vecOfTriCts.size(); ctIter++)
		{
			int triCt = vecOfTriCts[ctIter];

			//For each edge in each triangle
			for(int edgeCt = 0; edgeCt < 3; edgeCt++)
			{
				int v0 = m_vecTriangleIndices[triCt * 3 + (edgeCt)];
				int v1 = m_vecTriangleIndices[triCt * 3 + ((edgeCt +1) % 3)];

				//A vertex will be locked if it has already been involved in a collapse this pass.
				if(vertexLocked[v0] || vertexLocked[v1])
				{
					continue;
				}

				if(m_vecVertices[v0].getMaterial() != m_vecVertices[v1].getMaterial())
				{
					continue;
				}

				//For now, don't collapse vertices on material edges...
				if(m_vecVertices[v0].isOnMaterialEdge() || m_vecVertices[v1].isOnMaterialEdge())
				{
					if(true)
					{
						bool pass = false;						

						bool allMatch = false;

						// On the original undecimated mesh a material boundary vertex on a straight edge will
						// have four neighbours with the same material. If it's on a corner it will have a
						// different number. We only collapse straight edges to avoid changingthe shape of the
						// material boundary.
						if(m_vecNoOfNeighboursUsingMaterial[v0] == m_vecNoOfNeighboursUsingMaterial[v1])
						{
							if(m_vecNoOfNeighboursUsingMaterial[v0] == 4)
							{
								allMatch = true;
							}
						}

						bool movementValid = false;
						Vector3DFloat movement = m_vecVertices[v1].getPosition() - m_vecVertices[v0].getPosition();
						movement.normalise();
						if(movement.dot(Vector3DFloat(0,0,1)) > 0.999)
						{
							movementValid = true;
						}

						if(movement.dot(Vector3DFloat(0,1,0)) > 0.999)
						{
							movementValid = true;
						}

						if(movement.dot(Vector3DFloat(1,0,0)) > 0.999)
						{
							movementValid = true;
						}

						if(movement.dot(Vector3DFloat(0,0,-1)) > 0.999)
						{
							movementValid = true;
						}

						if(movement.dot(Vector3DFloat(0,-1,0)) > 0.999)
						{
							movementValid = true;
						}

						if(movement.dot(Vector3DFloat(-1,0,0)) > 0.999)
						{
							movementValid = true;
						}

						if(movementValid && allMatch)
						{
							pass = true;
						}

						if(!pass)
						{
							continue;
						}
					}
					else //Material collapses not allowed
					{
						continue;
					}
				}

				// Vertices on the geometrical edge of surface meshes need special handling. 
				// We check for this by whether any of the edge flags are set.
				if(m_vecVertices[v0].m_bFlags.any() || m_vecVertices[v1].m_bFlags.any())
				{
					// Assume we can't collapse until we prove otherwise...
					bool bCollapseGeometryEdgePair = false;

					// We can collapse normal vertices onto edge vertices, and edge vertices
					// onto corner vertices, but not vice-versa. Hence we check whether all
					// the edge flags in the source vertex are also set in the destination vertex.
					if(isSubset(m_vecVertices[v0].m_bFlags, m_vecVertices[v1].m_bFlags))
					{
						// In general adjacent regions surface meshes may collapse differently
						// and this can cause cracks. We solve this by only allowing the collapse
						// is the normals are exactly the same. We do not use the user provided
						// tolerence here (but do allow for floating point error).
						if(m_vecVertices[v0].getNormal().dot(m_vecVertices[v1].getNormal()) > 0.999)
						{
							// Ok, this pair can collapse.
							bCollapseGeometryEdgePair = true;
						}
					}

					// Use the result.
					if(!bCollapseGeometryEdgePair)
					{
						continue;
					}
				}

				//Check the normals are within the threashold.
				if(m_vecVertices[v0].getNormal().dot(m_vecVertices[v1].getNormal()) < fMinDotProductForCollapse)
				{
					continue;
				}

				////////////////////////////////////////////////////////////////////////////////
				//The last test is whether we will flip any of the faces

				bool faceFlipped = false;
				list<uint32_t> triangles = trianglesUsingVertex[v0];
				/*set<uint32_t> triangles;
				std::set_union(trianglesUsingVertex[v0].begin(), trianglesUsingVertex[v0].end(),
					trianglesUsingVertex[v1].begin(), trianglesUsingVertex[v1].end(),
					std::inserter(triangles, triangles.begin()));*/

				for(list<uint32_t>::iterator triIter = triangles.begin(); triIter != triangles.end(); triIter++)
				{
					uint32_t tri = *triIter;
					
					uint32_t v0Old = m_vecTriangleIndices[tri * 3];
					uint32_t v1Old = m_vecTriangleIndices[tri * 3 + 1];
					uint32_t v2Old = m_vecTriangleIndices[tri * 3 + 2];

					//Check if degenerate
					if((v0Old == v1Old) || (v1Old == v2Old) || (v2Old == v0Old))
					{
						continue;
					}

					uint32_t v0New = v0Old;
					uint32_t v1New = v1Old;
					uint32_t v2New = v2Old;

					if(v0New == v0)
						v0New = v1;
					if(v1New == v0)
						v1New = v1;
					if(v2New == v0)
						v2New = v1;

					//Check if degenerate
					if((v0New == v1New) || (v1New == v2New) || (v2New == v0New))
					{
						continue;
					}

					Vector3DFloat v0OldPos = m_vecVertices[vertexMapper[v0Old]].getPosition();
					Vector3DFloat v1OldPos = m_vecVertices[vertexMapper[v1Old]].getPosition();
					Vector3DFloat v2OldPos = m_vecVertices[vertexMapper[v2Old]].getPosition();

					Vector3DFloat v0NewPos = m_vecVertices[vertexMapper[v0New]].getPosition();
					Vector3DFloat v1NewPos = m_vecVertices[vertexMapper[v1New]].getPosition();
					Vector3DFloat v2NewPos = m_vecVertices[vertexMapper[v2New]].getPosition();

					/*Vector3DFloat v0OldPos = m_vecVertices[v0Old].getPosition();
					Vector3DFloat v1OldPos = m_vecVertices[v1Old].getPosition();
					Vector3DFloat v2OldPos = m_vecVertices[v2Old].getPosition();

					Vector3DFloat v0NewPos = m_vecVertices[v0New].getPosition();
					Vector3DFloat v1NewPos = m_vecVertices[v1New].getPosition();
					Vector3DFloat v2NewPos = m_vecVertices[v2New].getPosition();*/

					Vector3DFloat OldNormal = (v1OldPos - v0OldPos).cross(v2OldPos - v1OldPos);
					Vector3DFloat NewNormal = (v1NewPos - v0NewPos).cross(v2NewPos - v1NewPos);

					OldNormal.normalise();
					NewNormal.normalise();

					// Note for after holiday - We are still getting faces flipping despite the following test. I tried changing
					// the 0.0 to 0.9 (which should still let coplanar faces merge) but oddly nothing then merged. Investigate this.
					float dotProduct = OldNormal.dot(NewNormal);
					//cout << dotProduct << endl;
					if(dotProduct < 0.9f)
					{
						//cout << "   Face flipped!!" << endl;

						faceFlipped = true;

						/*vertexLocked[v0] = true;
						vertexLocked[v1] = true;*/

						break;
					}
				}

				if(faceFlipped == true)
				{
					continue;
				}

				////////////////////////////////////////////////////////////////////////////////

				//Move v0 onto v1
				vertexMapper[v0] = v1; //vertexMapper[v1];
				vertexLocked[v0] = true;
				vertexLocked[v1] = true;

				//Increment the counter
				++noOfEdgesCollapsed;
			}
		}

		if(noOfEdgesCollapsed > 0)
		{
			//Fix up the indices
			for(int triCt = 0; triCt < m_vecTriangleIndices.size(); triCt++)
			{
				uint32_t before = m_vecTriangleIndices[triCt];
				uint32_t after = vertexMapper[m_vecTriangleIndices[triCt]];
				if(before != after)
				{
					m_vecTriangleIndices[triCt] = vertexMapper[m_vecTriangleIndices[triCt]];
				}
			}
		}

		return noOfEdgesCollapsed;
	}

	int SurfaceMesh::noOfDegenerateTris(void)
	{
		int count = 0;
		for(int triCt = 0; triCt < m_vecTriangleIndices.size();)
		{
			int v0 = m_vecTriangleIndices[triCt];
			triCt++;
			int v1 = m_vecTriangleIndices[triCt];
			triCt++;
			int v2 = m_vecTriangleIndices[triCt];
			triCt++;

			if((v0 == v1) || (v1 == v2) || (v2 == v0))
			{
				count++;
			}
		}
		return count;
	}

	void SurfaceMesh::removeDegenerateTris(void)
	{
		int noOfNonDegenerate = 0;
		int targetCt = 0;
		for(int triCt = 0; triCt < m_vecTriangleIndices.size();)
		{
			int v0 = m_vecTriangleIndices[triCt];
			triCt++;
			int v1 = m_vecTriangleIndices[triCt];
			triCt++;
			int v2 = m_vecTriangleIndices[triCt];
			triCt++;

			if((v0 != v1) && (v1 != v2) & (v2 != v0))
			{
				m_vecTriangleIndices[targetCt] = v0;
				targetCt++;
				m_vecTriangleIndices[targetCt] = v1;
				targetCt++;
				m_vecTriangleIndices[targetCt] = v2;
				targetCt++;

				noOfNonDegenerate++;
			}
		}

		m_vecTriangleIndices.resize(noOfNonDegenerate * 3);
	}
}
