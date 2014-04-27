package lando.systems.ld29;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.ld29.scamps.ScampManager;

public class World {

	public GameGrid grid;
	public Player player;

	DayCycle dayCycle;
    public ResourceManager rManager;
    public ScampManager scampManager;

	public static final int gameWidth = 30;
	public static final int gameHeight = 6;


	public World() {
		grid = new GameGrid(this);
		dayCycle = new DayCycle(this);
		player = new Player(this);
		dayCycle.Scale = 50;
        rManager = new ResourceManager(this);
        scampManager = new ScampManager(this);
	}

	public void update(float dt){
		grid.update(dt);
		dayCycle.update(dt);
		player.update(dt);
        scampManager.update(dt);
	}

	public void render(SpriteBatch batch){
		dayCycle.render(batch);

		// Draw Resource Layer
		rManager.render(batch);

		// Draw AI Layer
        scampManager.renderScamps(batch);

		// Draw Grid
		grid.render(batch);

		// Draw Player
		player.render(batch);

	}

}
