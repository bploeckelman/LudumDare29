package lando.systems.ld29;

import lando.systems.ld29.core.Assets;

public class Firework {

	float x;
	float y;
	float vX;
	float vY;
	float ttl;
	
	public Firework(float x, float y){
		this.x = x;
		this.y = y;
		vX = (Assets.random.nextFloat() - .5f) * 50;
		vY = 120 + Assets.random.nextInt(30);
		this.ttl = 1.0f + Assets.random.nextFloat();
	}
	
	public void update(float dt){
		ttl -= dt;

			x += vX * dt;
			y += vY * dt;
			vY -= 50 * dt;

	}
	
}
