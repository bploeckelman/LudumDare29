package lando.systems.ld29;

import lando.systems.ld29.core.Input;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World {

	public GameGrid grid;
	public Player player;

	DayCycle dayCycle;

	public final int gameWidth = 30;
	public final int gameHeight = 6;

	
	public World() {
		grid = new GameGrid(this);
		dayCycle = new DayCycle(this);
		player = new Player(this);
		dayCycle.Scale = 50;
	}
	
	public void update(float dt){
		grid.update(dt);
		dayCycle.update(dt);
		player.update(dt);
	}
	
	public void render(SpriteBatch batch){
		dayCycle.render(batch);
	
		// Draw Resource Layer
		// TODO: Draw the resources behind the AI guys
		
		// Draw AI Layer
		// TODO: draw the AI guys and their huts etc
		
		// Draw Grid
		grid.render(batch);
		
		// Draw Player
		player.render(batch);
		
	}
	
}
