package lando.systems.ld29;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Particle {

	static Texture partTex = new Texture("art/white.png");
	
	Sprite sprite;
	float x;
	float y;
	float vX;
	float vY;
	float ttl;
	float startTTL;
	boolean gravity;
	Color startColor;
	Color endColor;
	Color currentColor;
	
	public Particle(){
		sprite = new Sprite(partTex);
	}
	
	public void init(float x, float y, float vX, float vY, float ttl, Color startColor, Color endColor, boolean useGravity){
		this.x = x;
		this.y = y;
		this.vX = vX;
		this.vY = vY;
		this.startColor = startColor;
		this.endColor = endColor;
		this.ttl = ttl;
		this.startTTL = ttl;
		this.gravity = useGravity;
		currentColor = startColor.cpy();
		
	}
	
	public void reset() {
        ttl = 0;
    }
	
	public void update(float dt){
		ttl -= dt;
		x += vX * dt;
		y += vY * dt;
		if (gravity){
			vX *= .99;
			vY -= 10 * dt;
		}
		currentColor = startColor.cpy();
		currentColor.lerp(endColor, 1 - ttl/startTTL);
	}
	
	public void render(SpriteBatch batch){
		sprite.setPosition(x, y);
		sprite.setColor(currentColor);
		sprite.draw(batch);
	}
}
