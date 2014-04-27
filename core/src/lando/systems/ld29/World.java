package lando.systems.ld29;

import lando.systems.ld29.core.Input;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World {

	public GameGrid grid;
	public Player player;
    public Hud hud;

	DayCycle dayCycle;
    public ResourceManager rManager;

	public final int gameWidth = 30;
	public final int gameHeight = 6;


	public World() {
		grid = new GameGrid(this);
		dayCycle = new DayCycle(this);
		player = new Player(this);
        hud = new Hud(this);
		dayCycle.Scale = 50;
        this.rManager = new ResourceManager(this);
	}

	public void update(float dt){
		grid.update(dt);
		dayCycle.update(dt);
		player.update(dt);
        hud.update(dt, player);
	}
	
	public void render(SpriteBatch batch, SpriteBatch hudBatch){
		dayCycle.render(batch);
	
		// Draw Resource Layer
		rManager.render(batch);
		
		// Draw AI Layer
		// TODO: draw the AI guys and their huts etc
		
		// Draw Grid
		grid.render(batch);
		
		// Draw Player
		player.render(batch);

        batch.end();
        hudBatch.begin();
        // Draw Hud
        hud.render(hudBatch);

	    hudBatch.end();
        batch.begin();
	}
	
}
