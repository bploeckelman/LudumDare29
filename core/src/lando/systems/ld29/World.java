package lando.systems.ld29;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World {

	GameGrid grid;
	
	public World() {
		grid = new GameGrid(this);
	}
	
	public void update(float dt){
		grid.update(dt);
	}
	
	public void render(SpriteBatch batch){
		// Draw background
		// TODO: daynight and other backgrounds
		
		// Draw Resource Layer
		// TODO: Draw the resources behind the AI guys
		
		// Draw AI Layer
		// TODO: draw the AI guys and their huts etc
		
		// Draw Grid
		grid.render(batch);
		
		// Draw Player
		// TODO: Draw Vishnoob and shit below grid
		
	}
	
}
