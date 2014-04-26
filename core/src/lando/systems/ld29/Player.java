package lando.systems.ld29;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
	
	public float xPos;
	private float targetX;
	private World world;
	private final float SPEED = 10;
	
	public Player(World world) {
		this.world = world;
	}
	
	
	public void MoveLeft(){
		targetX = Math.max(0,  targetX - 1);
		
	}
	
	public void MoveRight(){
		targetX++;
		targetX = Math.min(world.gameWidth,  targetX + 1);
	}
	
	public void update(float dt){
		float dist = SPEED * dt;
		if (dist > Math.abs(targetX - xPos)){
			xPos = targetX;
		} else {
			float sign = 1;
			if (xPos > targetX) {
				sign = -1;
			}
			xPos = xPos + (sign * dist); // TODO: This can't get backwards
		}
	}
	
	
	public void render(SpriteBatch batch){
		
	}
	
}
