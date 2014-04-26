package lando.systems.ld29;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.*;


public class Block {
	
	static Texture img = new Texture("badlogic.jpg");
	final Sprite sprite;
	float x;
	float y;
	
	float targetX;
	float targetY;
	
	static final float BLOCK_SPEED = 3;
	static final float BLOCK_WIDTH = 64;
	
	public Block(float x, float y) {
		img.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.x = x;
		this.y = y;
		targetX = x;
		targetY = y;
		sprite = new Sprite(img);
		sprite.setSize(BLOCK_WIDTH, BLOCK_WIDTH);
		
	}
	
	public void setNewPosition(float x, float y){
		targetX = x;
		targetY = y;
	}
	
	public void update(float dt){
		float dist = BLOCK_SPEED * dt;
		if (dist > Math.abs(targetX - x)){
			x = targetX;
		} else {
			x = x + dist; // TODO: This can't get backwards
		}
		if (dist > Math.abs(targetY - y)){
			y = targetY;
		} else {
			y = y + dist; // TODO: This can't get backwards
		}
		
	}
	
	public void render(SpriteBatch batch){
		sprite.setPosition(x * BLOCK_WIDTH, 100 + y * BLOCK_WIDTH);
		sprite.draw(batch);
	}
}
