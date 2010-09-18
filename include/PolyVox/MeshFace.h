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

#ifndef __PolyVox_MeshFace_H__
#define __PolyVox_MeshFace_H__

#include "PolyVox/MeshEdge.h"

#include "PolyVox/PolyVoxImpl/TypeDef.h"

namespace PolyVox
{
	class POLYVOXCORE_API MeshFace
	{
	public:
		MeshFace();
		bool isSane(void);

		MeshEdge* m_pEdge;

		Vector3DFloat getNormal(void);
		bool collapseFlipsFace(MeshEdge* pEdgeToCollapse);
		bool collapseFlipsFaceImpl(Vector3DFloat fixed0, Vector3DFloat fixed1, Vector3DFloat oldPos, Vector3DFloat newPos);
	};
}

#endif
