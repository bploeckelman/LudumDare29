package lando.systems.ld29;

import lando.systems.ld29.core.Assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class ParticleSystem {

	private final Array<Particle> activeParticles = new Array<Particle>();
	
	private final Pool<Particle> particlePool = new Pool<Particle>() {
        @Override
        protected Particle newObject() {
                return new Particle();
        }
    };
	
	public ParticleSystem(){
		
	}
	
	public void createFountain(float x, float y, Color color){
		for (int i = 0; i < 40; i ++){
			Particle item = particlePool.obtain();
			float dir = (float) (Assets.random.nextFloat() * Math.PI);
			item.init(x, y, (float)Math.cos(dir) * Assets.random.nextFloat() * 50, (float)Math.sin(dir)* ( 0.5f + Assets.random.nextFloat()) *50, 2.0f + Assets.random.nextFloat(), color, Color.CLEAR, true);
        	activeParticles.add(item);
		}
	}
	
	public void update(float dt){
        // if you want to free dead bullets, returning them to the pool:
		Particle item;
        int len = activeParticles.size;
        for (int i = len; --i >= 0;) {
            item = activeParticles.get(i);
            item.update(dt);
            if (item.ttl <= 0) {
            	activeParticles.removeIndex(i);
            	particlePool.free(item);
            }
        }
	}
	
	public void render(SpriteBatch batch){
		for (Particle part : activeParticles){
			part.render(batch);
		}
	}
}
