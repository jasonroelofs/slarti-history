/*
-----------------------------------------------------------------------------
This source file is part of ogre-procedural

For the latest info, see http://code.google.com/p/ogre-procedural/

Copyright (c) 2010 Michael Broutin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
-----------------------------------------------------------------------------
*/
#ifndef PROCEDURAL_TUBE_GENERATOR_INCLUDED
#define PROCEDURAL_TUBE_GENERATOR_INCLUDED
#include "Ogre.h"
#include "ProceduralMeshGenerator.h"
#include "ProceduralPlatform.h"

namespace Procedural
{

class _ProceduralExport TubeGenerator : public MeshGenerator<TubeGenerator>
{
    int numSegBase;
    int numSegHeight;
    float outerRadius;
    float innerRadius;
    float height;

public:
    TubeGenerator() : numSegBase(16),
        numSegHeight(1),
        outerRadius(2.f),
        innerRadius(1.f),
        height(1.f) {}

    void addToManualObject(Ogre::ManualObject* manual, int& offset, float& boundingRadius, Ogre::AxisAlignedBox& aabb);

    inline TubeGenerator & setNumSegBase(int numSegBase)
    {
        this->numSegBase = numSegBase;
        return *this;
    }

    inline TubeGenerator & setNumSegHeight(int numSegHeight)
    {
        this->numSegHeight = numSegHeight;
        return *this;
    }

    inline TubeGenerator & setOuterRadius(float outerRadius)
    {
        this->outerRadius = outerRadius;
        return *this;
    }

    inline TubeGenerator & setInnerRadius(float innerRadius)
    {
        this->innerRadius = innerRadius;
        return *this;
    }

    inline TubeGenerator & setHeight(float height)
    {
        this->height = height;
        return *this;
    }
};
}
#endif
