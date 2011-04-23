#ifndef __PANEL_COMPONENT_H__
#define __PANEL_COMPONENT_H__

#include "components/MeshComponent.h"

#include "Panel.h"

namespace Ogre {
  class Entity;
}

namespace components {

  /**
   * This component is responsible for taking a single Panel
   * and making sure it appears on screen for this actor
   */
  class PanelComponent : public MeshComponent 
  {
    public:
      PanelComponent(Panel* panel)
        : MeshComponent(""),
          _panel(panel)
      {
        position = panel->position;
        rotation = panel->rotation;
        _entity = panel->_entity;
      }

      Panel* _panel;
  };

}

#endif // __PANEL_COMPONENT_H__
