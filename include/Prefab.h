#ifndef __PREFAB_H__
#define __PREFAB_H__

#include <string>
#include <vector>

#include "Panel.h"

/**
 * A Prefab is a previously defined series of Panels
 * to make a structure that can be used, duplicated, modified, etc
 */
class Prefab
{
  public:
    Prefab() { }

    ~Prefab() 
    {
      for(unsigned i = 0; i < panels.size(); i++) {
        delete panels[i];
      }
    }

    std::string name;

    // Dimensions of the prefab in build-space
    int height, width, depth;

    std::vector<Panel*> panels;

};

#endif //__PREFAB_H__
