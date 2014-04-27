package lando.systems.ld29;

import lando.systems.ld29.blocks.*;
import lando.systems.ld29.util.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.*;


public class Player {
	
	static Texture img = new Texture("badlogic.jpg");
//	final Sprite sprite;
	
	public float xPos;
	private World world;
	private final float SPEED = 5;
	
	Array<Event> events = new Array();

	SkeletonRenderer renderer;
	SkeletonRendererDebug debugRenderer;

	SkeletonData skeletonData;
	Skeleton skeleton;
	Animation walkAnimation;
	Animation jumpAnimation;
	float animationTime = 0;
	
	public Player(World world) {
		renderer = new SkeletonRenderer();
		renderer.setPremultipliedAlpha(false);
		
		final String name = "art/animation/anim";

		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(name + ".atlas"));

		
		SkeletonJson json = new SkeletonJson(atlas);
		 json.setScale(.2f);
		skeletonData = json.readSkeletonData(Gdx.files.internal(name + ".json"));

		walkAnimation = skeletonData.findAnimation("idle");
		jumpAnimation = skeletonData.findAnimation("jump");

		skeleton = new Skeleton(skeletonData);
		skeleton.updateWorldTransform();
		skeleton.setX(xPos);
		skeleton.setY(20);
		
		this.world = world;
//		sprite = new Sprite(img);
//		sprite.setSize(64,64);
		xPos = 15;
	}
	
	public void update(float dt){
		if (Gdx.input.isKeyPressed(Keys.A)){
			xPos -= SPEED * dt;
		}
		if (Gdx.input.isKeyPressed(Keys.D)){
			xPos += SPEED * dt;
		}
		xPos = Utils.clamp(xPos, 0, world.gameWidth-1);
		animationTime += dt;
	}
	
	
	public void render(SpriteBatch batch){
//		sprite.setPosition(xPos * 64, 10);
//		sprite.draw(batch);
		walkAnimation.apply(skeleton, animationTime, animationTime, true, events);
		skeleton.setX((xPos + .5f) * 64);
		skeleton.updateWorldTransform();
		skeleton.update(Gdx.graphics.getDeltaTime());
		
		renderer.draw(batch, skeleton);
	}
	
}
