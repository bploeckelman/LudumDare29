package lando.systems.ld29;

import lando.systems.ld29.core.Assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class ParticleSystem {

	private final Array<Firework> fireworks = new Array<Firework>();
	
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
	
	public void addFirework(float x, float y){
		fireworks.add(new Firework(x, y));
	}
	
	public void fuckingCrazy(float x, float y, int size){
		for (int i = 0; i < size; i ++){
			Particle item = particlePool.obtain();
			float dir = (float) (Assets.random.nextFloat() * Math.PI * 2.0);
			item.init(x, y, (float)Math.cos(dir) * Assets.random.nextFloat() * 50, (float)Math.sin(dir)*  Assets.random.nextFloat() *50, 1.0f + Assets.random.nextFloat(), new Color(Assets.random.nextFloat(),Assets.random.nextFloat(),Assets.random.nextFloat(),1), Color.CLEAR, true);
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
        
		Firework firework;
        int flen = fireworks.size;
        for (int i = flen; --i >= 0;) {
        	firework = fireworks.get(i);
        	firework.update(dt);
            if (firework.vY < 0) {
            	fireworks.removeIndex(i);
            	fuckingCrazy(firework.x, firework.y, 100);
            } else {
    			Particle trail = particlePool.obtain();
    			float dir = (float) (Assets.random.nextFloat() * Math.PI * 2.0);
    			trail.init(firework.x, firework.y, (float)Math.cos(dir) * Assets.random.nextFloat() * 2, (float)Math.sin(dir)*  Assets.random.nextFloat() *2, Assets.random.nextFloat(), Color.YELLOW, Color.RED, true);
    			activeParticles.add(trail);
            }
        }
	}
	
	public void render(SpriteBatch batch){
		for (Particle part : activeParticles){
			part.render(batch);
		}
	}
}
