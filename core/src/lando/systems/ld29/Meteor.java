package lando.systems.ld29;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import lando.systems.ld29.core.Assets;

public class Meteor {

	public int targetBlock;
	public float xPos;
	public float yPos;
	
	private Vector2 vel;
	private float speed = 300;
	float targetY;
	float targetX;
	
	public Meteor(int targetBlock){
		xPos =0;
		if (targetBlock < World.gameWidth/2) xPos = World.gameWidth * 64;
		this.targetBlock = targetBlock;
		yPos = 800;
		targetX = targetBlock * 64 + 32;
		targetY = Global.GROUND_LEVEL;
		vel = new Vector2(targetX - xPos, targetY - yPos);
		vel.nor();
	}
	
	public void update(float dt){
		World.THEWORLD.particleSystem.fuckingCrazy(xPos, yPos, 30, Color.WHITE);
		float dist = speed * dt;
		Vector2 move = vel.cpy();
		move.scl(dist);
		
		Vector2 vDist = new Vector2(targetX - xPos, targetY - yPos);
		if (vDist.len2() < move.len2()){
			// Do stuff
            Assets.meteorCrash.play();
			World.THEWORLD.rManager.makeMeteorRes(targetBlock);
			World.THEWORLD.grid.meteor = null;
            World.THEWORLD.causeEarthquake();
		}
		
		xPos += move.x;
		yPos += move.y;
	}
	
	public void update(SpriteBatch batch){
		
	}
}
