#include "ui/UIManager.h"

#include <Rocket/Core.h>
#include <Rocket/Controls.h>
#include <Rocket/Debugger.h>

#include <OgreRenderTarget.h>
#include <OgreRenderSystem.h>
#include <OgreTextureUnitState.h>
#include <OgreArchive.h>

namespace ui {
  UIManager::UIManager(Ogre::RenderTarget* rt) 
    : mTarget(rt)
  {
    OGRE_LOG("Initializing UI Manager");

    mRenderInterface = new RenderInterfaceOgre3D(mTarget->getWidth(), mTarget->getHeight());
    Rocket::Core::SetRenderInterface(mRenderInterface);

    mSystemInterface = new SystemInterfaceOgre3D();
    Rocket::Core::SetSystemInterface(mSystemInterface);

    Rocket::Core::Initialise();
    Rocket::Controls::Initialise();

    // Load up all fonts in media/ui/fonts
    Ogre::FileInfoListPtr fonts = Ogre::ResourceGroupManager::getSingleton().findResourceFileInfo("Font", "*.otf");
    Ogre::String archive, filename;

    Ogre::FileInfoList::iterator start = fonts->begin();
    for(; start < fonts->end(); start++) {
      archive = (*start).archive->getName();
      filename = (*start).filename;

      Rocket::Core::FontDatabase::LoadFontFace((archive + "/" + filename).c_str());
    }

    mRocketContext = Rocket::Core::CreateContext("main", 
        Rocket::Core::Vector2i(mTarget->getWidth(), mTarget->getHeight()));

    Rocket::Debugger::Initialise(mRocketContext);

    // Load our debugger page
    Ogre::FileInfoListPtr templ = Ogre::ResourceGroupManager::getSingleton().findResourceFileInfo("UI", "debug.rml");
    archive = (*(templ->begin())).archive->getName();
    filename = (*(templ->begin())).filename;

    Rocket::Core::ElementDocument* document = mRocketContext->LoadDocument((archive + "/" + filename).c_str());

    if (document)
    {
      document->Show();
      document->RemoveReference();
    }
  }

  UIManager::~UIManager() {
    OGRE_LOG("Shutting down UI Manager");

    mRocketContext->RemoveReference();
    Rocket::Core::Shutdown();

    delete mRenderInterface;
    delete mSystemInterface;
  }

  void UIManager::update() {

  }

  void UIManager::updatePosition(const Ogre::Vector3& newPos) {
    Rocket::Core::Element* e = mRocketContext->GetDocument("game_window")->GetElementById("position");
    if(e) {
      e->SetInnerRML(Rocket::Core::String(128, "Position: %0.2f, %0.2f, %0.2f", newPos.x, newPos.y, newPos.z).CString());
    }
  }

  void UIManager::updateVelocity(const Ogre::Real& newV) {
    Rocket::Core::Element* e = mRocketContext->GetDocument("game_window")->GetElementById("velocity");
    if(e) {
      e->SetInnerRML(Rocket::Core::String(128, "Speed: %0.2f km/s", newV).CString());
    }
  }

  void UIManager::updateAcceleration(const Ogre::Real& newA) {
    Rocket::Core::Element* e = mRocketContext->GetDocument("game_window")->GetElementById("acceleration");
    if(e) {
      e->SetInnerRML(Rocket::Core::String(128, "Accel: %0.2f km/s^2", newA).CString());
    }
  }

  void UIManager::updateCruise(int newCruise) {
    Rocket::Core::Element* e = mRocketContext->GetDocument("game_window")->GetElementById("overdrive");
    if(e) {
      e->SetInnerRML(Rocket::Core::String(128, "Cruise: %d%%", newCruise).CString());
    }
  }

  void UIManager::updateElement(const std::string& id, const std::string& key, int value)
  {
    Rocket::Core::Element* e = mRocketContext->GetDocument("game_window")->GetElementById(id.c_str());
    if(e) {
      e->SetInnerRML(Rocket::Core::String(128, "%s %d", key.c_str(), value).CString());
    }
  }

  void UIManager::renderQueueStarted(uint8 queueGroupId, const Ogre::String& invocation, bool&)
  {
    if (queueGroupId == Ogre::RENDER_QUEUE_OVERLAY)
    {
      mRocketContext->Update();

      configureRenderer();
      mRocketContext->Render();
    }
  }

  void UIManager::renderQueueEnded(uint8, const Ogre::String&, bool& )
  {
  }

  void UIManager::configureRenderer() {
    Ogre::RenderSystem* render_system = Ogre::Root::getSingleton().getRenderSystem();

    // Set up the projection and view matrices.
    Ogre::Matrix4 projection_matrix;
    buildProjectionMatrix(projection_matrix);
    render_system->_setProjectionMatrix(projection_matrix);
    render_system->_setViewMatrix(Ogre::Matrix4::IDENTITY);

    // Disable lighting, as all of Rocket's geometry is unlit.
    render_system->setLightingEnabled(false);
    // Disable depth-buffering; all of the geometry is already depth-sorted.
    render_system->_setDepthBufferParams(false, false);
    // Rocket generates anti-clockwise geometry, so enable clockwise-culling.
    render_system->_setCullingMode(Ogre::CULL_CLOCKWISE);
    // Disable fogging.
    render_system->_setFog(Ogre::FOG_NONE);
    // Enable writing to all four channels.
    render_system->_setColourBufferWriteEnabled(true, true, true, true);
    // Unbind any vertex or fragment programs bound previously by the application.
    render_system->unbindGpuProgram(Ogre::GPT_FRAGMENT_PROGRAM);
    render_system->unbindGpuProgram(Ogre::GPT_VERTEX_PROGRAM);

    // Set texture settings to clamp along both axes.
    Ogre::TextureUnitState::UVWAddressingMode addressing_mode;
    addressing_mode.u = Ogre::TextureUnitState::TAM_CLAMP;
    addressing_mode.v = Ogre::TextureUnitState::TAM_CLAMP;
    addressing_mode.w = Ogre::TextureUnitState::TAM_CLAMP;
    render_system->_setTextureAddressingMode(0, addressing_mode);

    // Set the texture coordinates for unit 0 to be read from unit 0.
    render_system->_setTextureCoordSet(0, 0);
    // Disable texture coordinate calculation.
    render_system->_setTextureCoordCalculation(0, Ogre::TEXCALC_NONE);
    // Enable linear filtering; images should be rendering 1 texel == 1 pixel, so point filtering could be used
    // except in the case of scaling tiled decorators.
    render_system->_setTextureUnitFiltering(0, Ogre::FO_LINEAR, Ogre::FO_LINEAR, Ogre::FO_POINT);
    // Disable texture coordinate transforms.
    render_system->_setTextureMatrix(0, Ogre::Matrix4::IDENTITY);
    // Reject pixels with an alpha of 0.
    render_system->_setAlphaRejectSettings(Ogre::CMPF_GREATER, 0, false);
    // Disable all texture units but the first.
    render_system->_disableTextureUnitsFrom(1);

    // Enable simple alpha blending.
    render_system->_setSceneBlending(Ogre::SBF_SOURCE_ALPHA, Ogre::SBF_ONE_MINUS_SOURCE_ALPHA);

    // Disable depth bias.
    render_system->_setDepthBias(0, 0);
  }

  void UIManager::buildProjectionMatrix(Ogre::Matrix4& projection_matrix)
  {
    float z_near = -1;
    float z_far = 1;

    projection_matrix = Ogre::Matrix4::ZERO;

    // Set up matrices.
    projection_matrix[0][0] = 2.0f / mTarget->getWidth();
    projection_matrix[0][3]= -1.0000000f;
    projection_matrix[1][1]= -2.0f / mTarget->getHeight();
    projection_matrix[1][3]= 1.0000000f;
    projection_matrix[2][2]= -2.0f / (z_far - z_near);
    projection_matrix[3][3]= 1.0000000f;
  }
    
}
