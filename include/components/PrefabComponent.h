#ifndef __PREFAB_COMPONENT_H__
#define __PREFAB_COMPONENT_H__

#include "components/Component.h"
#include "components/PanelComponent.h"

#include "PrefabManager.h"
#include "Prefab.h"

namespace components {

  /**
   * Loads up the entity(s) of the requested prefab for visuals.
   * This is a factory component, and adds a number of PanelComponents
   * to the parent actor.
   */
  class PrefabComponent : public Component
  {
    public:
      PrefabComponent(std::string prefabName)
        : prefabName(prefabName)
      { 
        isFactory = true;
      }

      void initialize() {
        Prefab* prefab = PrefabManager::getInstance()->getPrefab(prefabName);
        for(unsigned i = 0; i < prefab->panels.size(); i++) {
          _actor->addComponent(new PanelComponent(prefab->panels[i]));
        }
      }

      std::string prefabName;

      FACTORY_COMPONENT
  };

}

#endif // __PREFAB_COMPONENT_H__
