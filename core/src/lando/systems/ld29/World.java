package lando.systems.ld29;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import lando.systems.ld29.blocks.Block;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.resources.ResourceManager;
import lando.systems.ld29.scamps.Scamp;
import lando.systems.ld29.scamps.ScampManager;
import lando.systems.ld29.screens.GameScreen;
import lando.systems.ld29.structures.SpaceshipStructure;
import lando.systems.ld29.structures.Structure;
import lando.systems.ld29.structures.StructureManager;
import lando.systems.ld29.util.Config;

public class World {

    public GameGrid grid;
    public Player player;
    public Hud hud;

	public DayCycle dayCycle;
    public ResourceManager rManager;
    public ScampManager scampManager;
    public StructureManager structureManager;
    public ParticleSystem particleSystem;
    public boolean preGame;

    public static final int gameWidth = 30;
    public static final int gameHeight = 5;
    public static World THEWORLD;
    public boolean gameWon = false;
    
    private final ArrayList<ResourceIndicator> _resIndicators = new ArrayList<ResourceIndicator>(10);

    public World() {
    	THEWORLD = this;
        grid = new GameGrid(this);
        dayCycle = new DayCycle(this);
        player = new Player(this);
        hud = new Hud(this);
        dayCycle.Scale = 10;
        rManager = new ResourceManager(this);
        scampManager = new ScampManager(this);
        structureManager = new StructureManager(this);
        particleSystem = new ParticleSystem();
        preGame = true;
    }
    
    public void displayResourceGather(IResourceGenerator resGen, int numResourcesGathered)
    {
    	TextureRegion icon = resGen.getResourceIcon();
    	Rectangle resourcePos = resGen.getResourceBounds();
    	_resIndicators.add(new ResourceIndicator(icon, resourcePos, numResourcesGathered));
    }

    public void update(float dt){
    	

        hud.update(dt, player);
        particleSystem.update(dt);
    	
    	if (preGame){
    		if (Gdx.input.justTouched())
    		preGame = false;
    		return;
    	}
        
    	if (scampManager.allInShip()){
            if (!gameWon) {
                Assets.launch.play();
            }
    		gameWon = true;
    		SpaceshipStructure space = (SpaceshipStructure) structureManager.findStructure("spaceship");
    		space.liftOff(dt);
    		
    		return;
    	}
        // It is getting too late in the night  remove all of this
//        Vector3 clickPoint = GameScreen.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
//        particleSystem.fuckingCrazy(clickPoint.x, clickPoint.y, 30);
//        if (Assets.random.nextFloat() > .99){
//            particleSystem.addFirework(Assets.random.nextFloat() * World.gameWidth * 64, 100 + Block.BLOCK_WIDTH * 6);
//        }
        // To here
    	for (ResourceIndicator resIndicator : _resIndicators) {
    		resIndicator.update(dt);
    	}
    	
    	for (int i = _resIndicators.size() - 1; i >= 0; i--) {
    		_resIndicators.get(i).update(dt);
    		if (_resIndicators.get(i).isComplete()) {
    			_resIndicators.remove(i);
    		}
    	}
    	
        grid.update(dt);
        dayCycle.update(dt);
        player.update(dt);
        scampManager.update(dt);
        rManager.update(dt);
        structureManager.update(dt);

	}
	
	public void render(SpriteBatch batch, SpriteBatch hudBatch){
		dayCycle.render(batch);
	
		// Draw Resource Layer
		rManager.render(batch);

        // Draw Structure Layer
        structureManager.render(batch);
		
     // Draw AI Layer
        scampManager.renderScamps(batch);

        for (ResourceIndicator resIndicator : _resIndicators) {
    		resIndicator.render(batch);
    	}
		
        // Draw Grid
        grid.render(batch);

        // Draw Player
        player.render(batch);

        particleSystem.render(batch);

        batch.end();
        hudBatch.begin();
        // Draw Hud
        hud.render(hudBatch);
        
        if (preGame){
        	Assets.panelBrown.draw(hudBatch, Config.window_half_width - 300, Config.window_half_height - 200, 600, 400);
        	Assets.gameFont.drawWrapped(hudBatch, Assets.introText, Config.window_half_width - 275, Config.window_half_height + 150, 550);
        }
        
        hudBatch.end();
        batch.begin();
    }

	public IToolTip getToolTipItemFromPos(Vector3 gameClickPoint, Vector3 hudClickPoint) {
		// check hud, then structures, then resources
		IToolTip item = hud.getToolTipItemFromPos(hudClickPoint.x, hudClickPoint.y);
        if (item == null) {
           item = structureManager.getStructureFromPos(gameClickPoint.x, gameClickPoint.y);
        }
        
        if (item == null) {
            item = rManager.getResourceFromPos(gameClickPoint.x, gameClickPoint.y);
        }
        return item;
	}

    public void causeEarthquake() {
        grid.startEarthquake();
    }
}
