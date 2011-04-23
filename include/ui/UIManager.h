#ifndef __UI_MANAGER_H__
#define __UI_MANAGER_H__

#include "Utils.h"

#include "RenderInterfaceOgre3D.h"
#include "SystemInterfaceOgre3D.h"

#include <OgreVector3.h>
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

      void update();
      void updatePosition(const Ogre::Vector3& newPos);
      void updateVelocity(const Ogre::Real& newV);
      void updateAcceleration(const Ogre::Real& newA);
      void updateCruise(int newCruise);

      void updateElement(const std::string& id, const std::string& key, int value);

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
