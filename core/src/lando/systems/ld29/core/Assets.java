package lando.systems.ld29.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import lando.systems.ld29.blocks.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Assets {
    public static Random random;
    public static SpriteBatch batch;
    public static SpriteBatch hudBatch;
    public static ShapeRenderer shapes;

    public static Texture libgdx;
    public static Map<String,TextureRegion> blocks;
    public static Map<String,TextureRegion> resources;
    public static Map<String,TextureRegion> structures;
    public static Map<String,TextureRegion> icons;
    public static Map<String,Integer> costs;

    public static Texture scamps_spritesheet;
    public static Array<TextureRegion> scamps;
    public static int num_scamps;

    public static NinePatch panelBrown;
    public static NinePatch thoughtBubble;
    public static NinePatch panelGreen;

    public static BitmapFont gameFont;
    public static BitmapFont TooltipHeaderFont;
    public static BitmapFont TooltipTextFont;

    public static BitmapFont HUDFont;
    
    public static BitmapFont gameOverFont;

    public static String[] scampNames;

    //public static Sound sound;
    public static Sound addBlock;
    public static Sound newDay;
    public static Sound earthquake;
    public static Sound gatherFood;
    public static Sound gatherOther;
    public static Sound build;
    public static Sound fireworkLaunch;
    public static Sound fireworkPop;
    public static Sound babyBorn;
    public static Sound meteorCrash;
    public static Sound launch;

    public static Music titleMusic;
    public static Music gameMusic;
    public static Music dayAmbient;
    public static Music nightAmbient;
    
    public static String introText;

    public static void load() {
    	introText = "Welcome to our LD29 (Beneath the Surface) Game. " +
    			"Your goal is to get these happy people off the planet.  " +
    			"Help them by raising resources through the earth for " +
    			"them to harvest and build a spaceship.  You need faith to " +
    			"create blocks, and gain it from the people above.  " +
    			"You control movement with A and D " +
    			"or move your mouse to the edge.\n\n" +
                "....................Click to Continue.................... ";
        random = new Random();
        batch  = new SpriteBatch();
        hudBatch = new SpriteBatch();
        shapes = new ShapeRenderer();

        libgdx = new Texture("art/title_screen.png");
        libgdx.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        Texture blocksSpritesheet = new Texture("art/blocks-spritesheet.png");
        blocks = new HashMap<String,TextureRegion>();
        blocks.put("dirt",   new TextureRegion(blocksSpritesheet, 0, 0, 16, 16));
        blocks.put("acorn",  new TextureRegion(blocksSpritesheet, 16, 0, 16, 16));
        blocks.put("gold",   new TextureRegion(blocksSpritesheet, 32, 0, 16, 16));
        blocks.put("grapes", new TextureRegion(blocksSpritesheet, 48, 0, 16, 16));
        blocks.put("iron",   new TextureRegion(blocksSpritesheet, 64, 0, 16, 16));
        blocks.put("stone",  new TextureRegion(blocksSpritesheet, 80, 0, 16, 16));
        //blocks.put("lava",   new TextureRegion(blocksSpritesheet, 96, 0, 16, 16));
        //blocks.put("water",  new TextureRegion(blocksSpritesheet, 112, 0, 16, 16));
        blocks.put("wheat",  new TextureRegion(blocksSpritesheet, 128, 0, 16, 16));
        blocks.put("marble",  new TextureRegion(blocksSpritesheet, 144, 0, 16, 16));
        
        costs = new HashMap<String, Integer>();
        costs.put("dirt", new Integer(3));
        costs.put("acorn", new Integer(6));
        costs.put("gold", new Integer(20));
        costs.put("grapes", new Integer(10));        
        costs.put("iron", new Integer(10));
        costs.put("stone", new Integer(6));
        costs.put("marble", new Integer(10));
//        costs.put("lava", new Integer(20));
//        costs.put("water", new Integer(20));
        costs.put("wheat", new Integer(3));
        

        scamps_spritesheet = new Texture("art/scamps-spritesheet.png");
        scamps = new Array<TextureRegion>();
        TextureRegion[][] scamp_regions = TextureRegion.split(scamps_spritesheet, 16, 16);
        for(int i = 0; i < scamp_regions.length; ++i) {
            scamps.addAll(scamp_regions[i], 0, scamp_regions[i].length);
        }
        num_scamps = 72; // 9 rows with 8 images per row

        Texture resourceSpritesheet = new Texture("art/resource_spritesheet.png");
        resources = new HashMap<String,TextureRegion>();
        resources.put("barren",         new TextureRegion(resourceSpritesheet, 0, Block.BLOCK_WIDTH * 0, 64, 64));
        resources.put("field",          new TextureRegion(resourceSpritesheet, 0, Block.BLOCK_WIDTH * 1, 64, 64));
        resources.put("forrest",        new TextureRegion(resourceSpritesheet, 0, Block.BLOCK_WIDTH * 2, 64, 128));
        resources.put("marblequarry",   new TextureRegion(resourceSpritesheet, 0, Block.BLOCK_WIDTH * 4, 64, 64));
        resources.put("meteor",         new TextureRegion(resourceSpritesheet, 0, Block.BLOCK_WIDTH * 5, 64, 64));
        resources.put("goldmine",       new TextureRegion(resourceSpritesheet, 0, Block.BLOCK_WIDTH * 6, 64, 128));
        resources.put("mountain",       new TextureRegion(resourceSpritesheet, 0, Block.BLOCK_WIDTH * 8, 64, 128));
        resources.put("quarry",         new TextureRegion(resourceSpritesheet, 0, Block.BLOCK_WIDTH * 10, 64, 64));
        resources.put("vinyard",        new TextureRegion(resourceSpritesheet, 0, Block.BLOCK_WIDTH * 11, 64, 64));

        Texture structureSpritesheet = new Texture("art/structures-spritesheet.png");
        structures = new HashMap<String,TextureRegion>();
        structures.put("house-day"  , new TextureRegion(structureSpritesheet,   0, 192, 64, 64));
        structures.put("house-night", new TextureRegion(structureSpritesheet,  64, 192, 64, 64));
        structures.put("temple"     , new TextureRegion(structureSpritesheet, 128, 192, 64, 64));
        structures.put("warehouse"  , new TextureRegion(structureSpritesheet, 192, 192, 64, 64));
        structures.put("factory-day", new TextureRegion(structureSpritesheet, 256, 192, 64, 64));
        structures.put("factory-night", new TextureRegion(structureSpritesheet, 320, 192, 64, 64));
        structures.put("spaceship"  , new TextureRegion(structureSpritesheet, 384,   0, 64, 256));

        Texture iconSpritesheet = new Texture("art/icon-spritesheet.png");
        icons = new HashMap<String,TextureRegion>();
        TextureRegion[][] iconRegions = TextureRegion.split(iconSpritesheet, 16, 16);
        icons.put("FOOD",     iconRegions[0][0]);
        icons.put("WOOD",     iconRegions[0][1]);
        icons.put("STONE",    iconRegions[0][2]);
        icons.put("IRON",     iconRegions[0][3]);
        icons.put("MARBLE",   iconRegions[0][4]);
        icons.put("GOLD",     iconRegions[0][5]);
        icons.put("GRAPES",   iconRegions[1][0]);
        icons.put("FUEL",     iconRegions[1][1]);
        icons.put("CIRCUITS", iconRegions[1][2]);
        icons.put("METEOR",   iconRegions[1][3]);
        icons.put("STEEL",    iconRegions[1][4]);
        icons.put("PEOPLE",   iconRegions[1][5]);
        icons.put("BUILD",    iconRegions[2][0]);

        panelBrown = new NinePatch(
                new Texture("art/panel_brown.png"),
                10, 10, 10 , 10
        );


        panelBrown.setColor(new Color(153, 102, 51, 1));

        panelGreen = new NinePatch(
                new Texture("art/greenbar.png"),
                7, 7, 7, 7
        );

        thoughtBubble = new NinePatch(
                new Texture("art/thought.png"),
                4, 5, 4 , 9
        );

        gameFont = new BitmapFont(Gdx.files.internal("fonts/ariel.fnt"),Gdx.files.internal("fonts/ariel.png"),false);
        TooltipHeaderFont = new BitmapFont(Gdx.files.internal("fonts/ariel.fnt"),Gdx.files.internal("fonts/ariel.png"),false);

        TooltipTextFont = new BitmapFont(Gdx.files.internal("fonts/ariel.fnt"),Gdx.files.internal("fonts/ariel.png"),false);
        TooltipTextFont.setScale(.7f);

        HUDFont = new BitmapFont(Gdx.files.internal("fonts/ariel.fnt"),Gdx.files.internal("fonts/ariel.png"),false);
        HUDFont.setScale(.5f);
        
        gameOverFont = new BitmapFont(Gdx.files.internal("fonts/ariel.fnt"),Gdx.files.internal("fonts/ariel.png"),false);
        gameOverFont.setScale(3f);
        gameOverFont.setColor(1,1,1,.7f);

        scampNames = Gdx.files.internal("text/scamp-names.txt").readString().split("\r\n|\r|\n");


        addBlock = Gdx.audio.newSound(Gdx.files.internal("audio/add_block.wav"));
        newDay = Gdx.audio.newSound(Gdx.files.internal("audio/new_day.wav"));
        earthquake = Gdx.audio.newSound(Gdx.files.internal("audio/earthquake.wav"));
        gatherFood = Gdx.audio.newSound(Gdx.files.internal("audio/gather_food.wav"));
        gatherOther = Gdx.audio.newSound(Gdx.files.internal("audio/gather_other.wav"));
        build = Gdx.audio.newSound(Gdx.files.internal("audio/build.wav"));
        fireworkLaunch = Gdx.audio.newSound(Gdx.files.internal("audio/firework_launch.wav"));
        fireworkPop = Gdx.audio.newSound(Gdx.files.internal("audio/firework_pop.wav"));
        babyBorn = Gdx.audio.newSound(Gdx.files.internal("audio/baby_born.wav"));
        meteorCrash = Gdx.audio.newSound(Gdx.files.internal("audio/meteor_crash.wav"));
        launch = Gdx.audio.newSound(Gdx.files.internal("audio/launch.wav"));

        titleMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/title_music.mp3"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/game_music.mp3"));
        dayAmbient = Gdx.audio.newMusic(Gdx.files.internal("audio/day_birds.mp3"));
        nightAmbient = Gdx.audio.newMusic(Gdx.files.internal("audio/night_frogs.mp3"));

        titleMusic.setLooping(true);
        titleMusic.setVolume(0.5f);
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.2f);
        dayAmbient.setLooping(true);
        nightAmbient.setLooping(true);
    }

    public static void dispose() {
        libgdx.dispose();
        // todo : dispose the rest of the things
        batch.dispose();
        hudBatch.dispose();
    }

    public static String randomName() {
        return scampNames[random.nextInt(scampNames.length)];
    }

}
