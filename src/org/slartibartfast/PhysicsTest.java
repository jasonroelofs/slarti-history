package org.slartibartfast;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

/**
 *
 * @author roelofs
 */
public class PhysicsTest extends SimpleApplication {
    public static void main(String args[]) {
        PhysicsTest app = new PhysicsTest();
        app.start();
    }
    
    private BulletAppState bulletAppState;
    
    BasicShadowRenderer bsr;
    
    Material wall_mat;
    Material stone_mat;
    Material floor_mat;
    
    private RigidBodyControl brick_phy;
    private static final Box box;
    
    private RigidBodyControl ball_phy;
    private static final Sphere sphere;
    
    private RigidBodyControl floor_phy;
    private static final Box floor;
    
    private static final float brickLength = 0.48f;
    private static final float brickWidth = 0.24f;
    private static final float brickHeight = 0.12f;
    
    static {
        sphere = new Sphere(32, 32, 0.4f, true, false);
        sphere.setTextureMode(TextureMode.Projected);
        
        box = new Box(Vector3f.ZERO, brickLength, brickHeight, brickWidth);
        box.scaleTextureCoordinates(new Vector2f(1f, 0.5f));
        
        floor = new Box(Vector3f.ZERO, 10f, 0.1f, 5f);
        floor.scaleTextureCoordinates(new Vector2f(3, 6));
    }
    
    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        cam.setLocation(new Vector3f(0, 6f, 6f));
        cam.lookAt(Vector3f.ZERO, new Vector3f(0, 1, 0));
        cam.setFrustumFar(15);
        
        inputManager.addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "shoot");
        
        initMaterials();
        initWall();
        initFloor();
        initCrossHairs();
        initShadows();
    }
    
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if(name.equals("shoot") && !isPressed) {
                makeCannonBall();
            }
        }
    };

    private void initMaterials() {
        wall_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key = new TextureKey("Textures/Terrain/BrickWall/BrickWall.jpg");
        key.setGenerateMips(true);
        Texture tex = assetManager.loadTexture(key);
        wall_mat.setTexture("ColorMap", tex);

        stone_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
        key2.setGenerateMips(true);
        Texture tex2 = assetManager.loadTexture(key2);
        stone_mat.setTexture("ColorMap", tex2);

        floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.png");
        key3.setGenerateMips(true);
        Texture tex3 = assetManager.loadTexture(key3);
        tex3.setWrap(WrapMode.Repeat);
        floor_mat.setTexture("ColorMap", tex3);
    }

    private void initWall() {
        float startpt = brickLength / 4;
        float height = 0;
        for(int j = 0; j < 15; j++) {
            for(int i = 0; i < 4; i++) {
                Vector3f vt = new Vector3f(i * brickLength * 2 + startpt, brickHeight + height, 0);
                makeBrick(vt);
            }
            startpt = -startpt;
            height += 2 * brickHeight;
        }
    }

    private void initFloor() {
        Geometry floor_geo = new Geometry("Floor", floor);
        floor_geo.setMaterial(floor_mat);
        floor_geo.setShadowMode(ShadowMode.Receive);
        floor_geo.setLocalTranslation(0, -0.1f, 0);
        this.rootNode.attachChild(floor_geo);
        
        floor_phy = new RigidBodyControl(0.0f);
        floor_geo.addControl(floor_phy);
        bulletAppState.getPhysicsSpace().add(floor_phy);
        
    }

    private void initCrossHairs() {
        //guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");
        ch.setLocalTranslation(settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

    private void initShadows() {
        bsr = new BasicShadowRenderer(assetManager, 256);
        bsr.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
        viewPort.addProcessor(bsr);
        rootNode.setShadowMode(ShadowMode.Off);
        
    }

    private void makeBrick(Vector3f vt) {
        Geometry brick_geo = new Geometry("brick", box);
        brick_geo.setMaterial(wall_mat);
        rootNode.attachChild(brick_geo);
        
        brick_geo.setLocalTranslation(vt);
        brick_geo.setShadowMode(ShadowMode.CastAndReceive);
        
        brick_phy = new RigidBodyControl(2f);
        brick_geo.addControl(brick_phy);
        bulletAppState.getPhysicsSpace().add(brick_phy);
        
    }
    
    private void makeCannonBall() {
        Geometry ball_geo = new Geometry("cannon ball", sphere);
        ball_geo.setMaterial(stone_mat);
        rootNode.attachChild(ball_geo);
        
        ball_geo.setLocalTranslation(cam.getLocation());
        ball_geo.setShadowMode(ShadowMode.CastAndReceive);
        
        ball_phy = new RigidBodyControl(1f);
        ball_geo.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);
        ball_phy.setLinearVelocity(cam.getDirection().mult(50));
        
    }
    
    
}
