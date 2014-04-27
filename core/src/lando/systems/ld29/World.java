package lando.systems.ld29;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.ld29.scamps.ScampManager;

public class World {

	public GameGrid grid;
	public Player player;
    public Hud hud;

	DayCycle dayCycle;
    public ResourceManager rManager;
    public ScampManager scampManager;
    public ParticleSystem particleSystem;

	public static final int gameWidth = 30;
	public static final int gameHeight = 6;


	public World() {
		grid = new GameGrid(this);
		dayCycle = new DayCycle(this);
		player = new Player(this);
        hud = new Hud(this);
		dayCycle.Scale = 50;
        rManager = new ResourceManager(this);
        scampManager = new ScampManager(this);
        particleSystem = new ParticleSystem();
	}

	public void update(float dt){
		grid.update(dt);
		dayCycle.update(dt);
		player.update(dt);
        scampManager.update(dt);
        rManager.update(dt);
        hud.update(dt, player);
        particleSystem.update(dt);
	}
	
	public void render(SpriteBatch batch, SpriteBatch hudBatch){
		dayCycle.render(batch);
	
		// Draw Resource Layer
		rManager.render(batch);
		
		// Draw AI Layer
        scampManager.renderScamps(batch);

		// Draw Grid
		grid.render(batch);
		
		// Draw Player
		player.render(batch);
		
		particleSystem.render(batch);

        batch.end();
        hudBatch.begin();
        // Draw Hud
        hud.render(hudBatch);

	    hudBatch.end();
        batch.begin();
	}
	
}
