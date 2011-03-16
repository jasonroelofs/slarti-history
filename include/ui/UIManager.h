#ifndef __UI_MANAGER_H__
#define __UI_MANAGER_H__

#include "Utils.h"

#include "RenderInterfaceOgre3D.h"
#include "SystemInterfaceOgre3D.h"

#include <OgreMatrix4.h>
#include <OgreRenderQueueListener.h>

namespace Ogre {
  class RenderTarget;
}

namespace ui {
  class UIManager : public Ogre::RenderQueueListener {
    public:
      UIManager(Ogre::RenderTarget* rt);

      ~UIManager();

      /// Called from Ogre before a queue group is rendered.
      virtual void renderQueueStarted(uint8 queueGroupId, const Ogre::String& invocation, bool& skipThisInvocation);
      /// Called from Ogre after a queue group is rendered.
      virtual void renderQueueEnded(uint8 queueGroupId, const Ogre::String& invocation, bool& repeatThisInvocation);

    protected:
  
      void configureRenderer();

      void buildProjectionMatrix(Ogre::Matrix4& matrix);

      RenderInterfaceOgre3D* mRenderInterface;
      SystemInterfaceOgre3D* mSystemInterface;

      Rocket::Core::Context* mRocketContext;

      Ogre::RenderTarget* mTarget;
  };
}

#endif // __UI_MANAGER_H__
