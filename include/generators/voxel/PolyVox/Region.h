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

#ifndef __PolyVox_Region_H__
#define __PolyVox_Region_H__

#pragma region Headers
#include "PolyVox/PolyVoxImpl/TypeDef.h"
#include "PolyVox/Vector.h"
#pragma endregion

namespace PolyVox
{
	class POLYVOXCORE_API Region
	{
	public:
		Region();
		Region(const Vector3DInt16& v3dLowerCorner, const Vector3DInt16& v3dUpperCorner);

		const Vector3DInt16& getLowerCorner(void) const;
		const Vector3DInt16& getUpperCorner(void) const;

		void setLowerCorner(const Vector3DInt16& v3dLowerCorner);
		void setUpperCorner(const Vector3DInt16& v3dUpperCorner);

		bool containsPoint(const Vector3DFloat& pos, float boundary) const;
		bool containsPoint(const Vector3DInt16& pos, uint8_t boundary) const;
		void cropTo(const Region& other);
		int16_t depth(void) const;
		int16_t height(void) const;
		void shift(const Vector3DInt16& amount);
		void shiftLowerCorner(const Vector3DInt16& amount);
		void shiftUpperCorner(const Vector3DInt16& amount);
		Vector3DInt16 dimensions(void);
		int16_t width(void) const;

	private:
		Vector3DInt16 m_v3dLowerCorner;
		Vector3DInt16 m_v3dUpperCorner;

		//FIXME - This variable is unused, but without it the OpenGL example crashes in release mode
		//when the volume size is 128^3 and the level of detail is 2. Very strange, but consistant.
		//Presubablly some kind of alignment issue? It started after this class was changed to use
		//int16's rather than int32's. To be investigated.
		uint8_t dummy; 
	};
}

#endif
