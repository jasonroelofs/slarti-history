import com.jme3.app.SimpleApplication
import com.jme3.asset.TextureKey
import com.jme3.bullet.BulletAppState
import com.jme3.bullet.control.RigidBodyControl
import com.jme3.font.BitmapText
import com.jme3.input.MouseInput
import com.jme3.input.controls.ActionListener
import com.jme3.input.controls.MouseButtonTrigger
import com.jme3.material.Material
import com.jme3.math.Vector2f
import com.jme3.math.Vector3f
import com.jme3.renderer.queue.RenderQueue.ShadowMode
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.jme3.scene.shape.Sphere
import com.jme3.scene.shape.Sphere.TextureMode
import com.jme3.shadow.BasicShadowRenderer
import com.jme3.texture.Texture
import com.jme3.texture.Texture.WrapMode

class HelloPhysics < SimpleApplication

  def simpleInitApp():void
    @brickLength = 0.48
    @brickHeight = 0.12
    @brickWidth = 0.24

    @sphere = Sphere.new(32, 32, 0.4, true, false)
    @sphere.setTextureMode(TextureMode.Projected)

    @box = Box.new(Vector3f.ZERO, @brickLength, @brickHeight, @brickWidth)
    @box.scaleTextureCoordinates(Vector2f.new(1.0, 0.5))

    @floor = Box.new(Vector3f.ZERO, 10.0, 0.1, 5.0)
    @floor.scaleTextureCoordinates(Vector2f.new(3, 6))

    @bulletAppState = BulletAppState.new
    getStateManager().attach(@bulletAppState)

    cam = getCamera()
    cam.setLocation(Vector3f.new(0, 6.0, 6.0))
    cam.lookAt(Vector3f.ZERO, Vector3f.new(0, 1, 0))
    cam.setFrustumFar(15)

    getInputManager().addMapping("shoot", MouseButtonTrigger.new(MouseInput.BUTTON_LEFT))
    getInputManager.addListener("shoot") do |name, keyPressed, tpf|
      if name == "shoot" && !keyPressed
        makeCannonBall
      end
    end

    @assetManager = getAssetManager

    initMaterials
    initWall
    initFloor
    initCrossHairs
    initShadows
  end

  def initMaterials
    @wallMat = Material.new(@assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
    key = TextureKey.new("Textures/Terrain/BrickWall/BrickWall.jpg")
    key.setGenerateMips(true)

    tex = @assetManager.loadTexture(key)
    @wallMat.setTexture("ColorMap", text)

    @stoneMat = Material.new(@assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
    key2 = TextureKey.new("Textures/Terrain/Rock/Rock.PNG")
    key2.setGenerateMips(true)

    tex = @assetManager.loadTexture(key2)
    @stoneMat.setTexture("ColorMap", text)

    @floorMat = Material.new(@assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
    key3 = TextureKey.new("Textures/Terrain/Pond/Pond.png")
    key3.setGenerateMips(true)

    tex = @assetManager.loadTexture(key3)
    tex3.setWrap(WrapMode.Repeat)
    @floorMat.setTexture("ColorMap", text)
  end

  def initFloor
    floorGeo = Geometry.new("Floor", @floor)
    floorGeo.setMaterial(@floorMat)
    floorGeo.setShadowMode(ShadowMode.Receive)
    floorGeo.setLocalTranslation(0, -0.1, 0)
    getRootNode.attachChild(floorGeo)

    floorPhys = RigidBodyControl.new(0.0)
    floorGeo.addControl(floorPhys)
    @bulletAppState.getPhysicsSpace.add(floorPhys)
  end

  def initWall
  end

  def initCrossHairs
  end

  def initShadows
  end

  def makeCannonBall
  end

end

HelloPhysics.new.start
