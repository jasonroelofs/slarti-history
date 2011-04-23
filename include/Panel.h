#ifndef __PANEL_H__
#define __PANEL_H__

#include <OgreVector3.h>
#include <OgreQuaternion.h>

#include <string>

/**
 * A panel is a 1x1x0.5 wall.
 * It knows where it is relative to whatever local context owns
 * it (for example, in a Prefab)
 */
class Panel
{
  public:
    Panel() { }

    Ogre::Vector3 position;

    Ogre::Quaternion rotation;

    // More of a material name than anything
    std::string type;

    std::string meshName, materialName;

};

#endif // __PANEL_H__
