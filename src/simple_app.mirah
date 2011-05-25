import com.jme3.app.SimpleApplication
import com.jme3.material.Material
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.jme3.math.ColorRGBA
import com.jme3.asset.AssetManager

class HelloJME3 < SimpleApplication

  def simpleInitApp():void
    b = Box.new(Vector3f.ZERO, 1, 1, 1)
    geom = Geometry.new("Box", b)
    mat = Material.new(getAssetManager, "Common/MatDefs/Misc/Unshaded.j3md")
    geom.setMaterial(mat)
    getRootNode.attachChild(geom)
  end

end

HelloJME3.new.start
