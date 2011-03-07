#ifndef __MOUSE_LOOK_COMPONENT_H__
#define __MOUSE_LOOK_COMPONENT_H__

#include "managers/InputManager.h"
#include "components/InputComponent.h"
#include "components/TransformComponent.h"
#include "Event.h"

#include <iostream>
using namespace std;

namespace components {

  /**
   * This component hooks up the mouse to rotate the view in an
   * FPS manner, as well as informing the Transform that "forward"
   * should now mean "forward where I'm pointing", and likewise
   * for strafing.
   */
  class MouseLookComponent : public InputComponent {

    public:

      virtual void mapEvents(managers::InputManager* manager) {
        manager->map(Event::MouseMoved, this, &MouseLookComponent::rotate);

        // Inform the actor's Transform to always follow orientation
        _actor->transform->moveRelativeToRotation = true;
      }

      void rotate(InputEvent event) {
        _actor->transform->yaw(Ogre::Degree(-event.xDiff * 0.30f));
        _actor->transform->pitch(Ogre::Degree(-event.yDiff * 0.30f));
      }
  };

}

#endif // __MOUSE_LOOK_COMPONENT_H__
