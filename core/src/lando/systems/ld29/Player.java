package lando.systems.ld29;

import lando.systems.ld29.blocks.*;
import lando.systems.ld29.screens.GameScreen;
import lando.systems.ld29.util.Config;
import lando.systems.ld29.util.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.*;


public class Player {
	
	static Texture img = new Texture("badlogic.jpg");
//	final Sprite sprite;
	
	public float xPos;
	public int xTarget;
	private World world;
	private final float SPEED = 4;
	
	Array<Event> events = new Array();

	SkeletonRenderer renderer;
	SkeletonRendererDebug debugRenderer;

	SkeletonData skeletonData;
	Skeleton skeleton;
	Animation idleAnimation;
	Animation walkLeftAnimation;
	Animation walkRightAnimation;
	Animation pickAnimation;
	float animationTime = 0;
	
	public float belief;
	
	public Player(World world) {
		renderer = new SkeletonRenderer();
		renderer.setPremultipliedAlpha(false);
		
		final String name = "art/animation/anim";

		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(name + ".atlas"));

		
		SkeletonJson json = new SkeletonJson(atlas);
		 json.setScale(.2f);
		skeletonData = json.readSkeletonData(Gdx.files.internal(name + ".json"));

		idleAnimation = skeletonData.findAnimation("idle");
		walkLeftAnimation = skeletonData.findAnimation("walkLeft");
		walkRightAnimation = skeletonData.findAnimation("walkRight");
		pickAnimation = skeletonData.findAnimation("pick");
		
		skeleton = new Skeleton(skeletonData);
		skeleton.updateWorldTransform();
		skeleton.setX(xPos);
		skeleton.setY(Global.UNDERGROUND_LEVEL + 4);
		
		this.world = world;
//		sprite = new Sprite(img);
//		sprite.setSize(64,64);
		xPos = 15;
		xTarget = 15;
	}
	
	float inputDelay = 0;
	
	public void pushBlock(Block block, int x){
		if (inputDelay > 0) return;
        if(null != block) {
        	block.setNewPosition(x, 0);
        	float cost = block.cost;
            if (belief > cost && world.grid.pushUp(block, x)){
            	belief -= cost;
            	inputDelay = .5f; // Seconds until we can act again.
            	animationTime = 0;
            }
        }
	}
	
	public void handleKeyBlocks(){
		Block block = null;
		if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
			block = new DirtBlock(xTarget, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
			block = new WheatBlock(xTarget, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_3)) {
			block = new StoneBlock(xTarget, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_4)) {
			block = new AcornBlock(xTarget, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_5)) {
			block = new GrapesBlock(xTarget, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_6)) {
			block = new MarbleBlock(xTarget, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_7)) {
			block = new IronBlock(xTarget, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.NUM_8)) {
			block = new GoldBlock(xTarget, 0);
		}
		pushBlock(block, (int) xTarget);
	}
	
	public void update(float dt){
		handleKeyBlocks();
		
		Vector3 hudClickPoint = GameScreen.hudCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

		if (xPos == xTarget){
			if (inputDelay <= 0){
				if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A) || hudClickPoint.x < 100){
					xTarget--;
					animationTime = 0;
				}
				if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D) ||hudClickPoint.x > Config.window_width - 100){
					xTarget++;
					animationTime = 0;
				}
				xTarget = Math.min(World.gameWidth-1, Math.max(xTarget, 0));
			}
		} else {
			float dist = SPEED * dt;
			if (dist >= Math.abs(xTarget - xPos)){
				xPos = xTarget;
				animationTime = 0;
			} else {
				float sign = 1;
				if (xTarget< xPos){
					sign = -1;
				}
				xPos += sign * dist;
			}
		}
		
		inputDelay = Math.max(0, inputDelay - dt);
		xPos = Utils.clamp(xPos, 0, world.gameWidth-1);
		animationTime += dt;
		
		float beliefDelta = (.5f + world.scampManager.getCurrentPopulation() / 10.0f) * dt;
		if (World.THEWORLD.rManager.CountofType("temple")>0){
			beliefDelta *= 2;
		}
		belief += beliefDelta;
		belief = Utils.clamp(belief, 0, 100);
		
	}
	
	public void addBelief(float amount) {
		belief = Math.min(100, belief + amount);
	}
	
	public void render(SpriteBatch batch){
//		sprite.setPosition(xPos * 64, 10);
//		sprite.draw(batch);
		if (inputDelay > 0){
			pickAnimation.apply(skeleton, animationTime, animationTime, true, events);
		} else if (xTarget == xPos){
			idleAnimation.apply(skeleton, animationTime/2, animationTime/2, true, events);
		} else if (xTarget < xPos){
			walkLeftAnimation.apply(skeleton, animationTime*3, animationTime*3, true, events);
		} else {
			walkRightAnimation.apply(skeleton, animationTime*3, animationTime*3, true, events);

		}
		skeleton.setX((xPos + .5f) * 64);
		skeleton.updateWorldTransform();
		skeleton.update(Gdx.graphics.getDeltaTime());
		
		renderer.draw(batch, skeleton);
	}
	
}
