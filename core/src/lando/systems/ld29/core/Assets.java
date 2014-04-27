package lando.systems.ld29.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

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

    public static String[] scampNames;
    
    //public static Sound sound;
    //public static Music music;

    public static void load() {
        random = new Random();
        batch  = new SpriteBatch();
        hudBatch = new SpriteBatch();
        shapes = new ShapeRenderer();

        libgdx = new Texture("badlogic.jpg");
        libgdx.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        Texture blocksSpritesheet = new Texture("art/blocks-spritesheet.png");
        blocks = new HashMap<String,TextureRegion>();
        blocks.put("dirt",   new TextureRegion(blocksSpritesheet, 0, 0, 16, 16));
        blocks.put("acorn",  new TextureRegion(blocksSpritesheet, 16, 0, 16, 16));
        blocks.put("gold",   new TextureRegion(blocksSpritesheet, 32, 0, 16, 16));
        blocks.put("grapes", new TextureRegion(blocksSpritesheet, 48, 0, 16, 16));
        blocks.put("iron",   new TextureRegion(blocksSpritesheet, 64, 0, 16, 16));
        blocks.put("stone",  new TextureRegion(blocksSpritesheet, 80, 0, 16, 16));
        blocks.put("lava",   new TextureRegion(blocksSpritesheet, 96, 0, 16, 16));
        blocks.put("water",  new TextureRegion(blocksSpritesheet, 112, 0, 16, 16));

        scamps_spritesheet = new Texture("art/scamps-spritesheet.png");
        scamps = new Array<TextureRegion>();
        TextureRegion[][] scamp_regions = TextureRegion.split(scamps_spritesheet, 16, 16);
        for(int i = 0; i < scamp_regions.length; ++i) {
            scamps.addAll(scamp_regions[i], 0, scamp_regions[i].length);
        }
        num_scamps = 81; // 9 rows of unusable scamp images at the moment (9 images per row)

        Texture resourceSpritesheet = new Texture("art/resources-spritesheet.png");
        resources = new HashMap<String,TextureRegion>();
        resources.put("field",   new TextureRegion(resourceSpritesheet, 0, 0, 32, 32));
        resources.put("forrest",  new TextureRegion(resourceSpritesheet, 32, 0, 32, 32));
        resources.put("mountain",   new TextureRegion(resourceSpritesheet, 64, 0, 32, 32));
        resources.put("quarry", new TextureRegion(resourceSpritesheet, 96, 0, 32, 32));
        resources.put("vinyard",   new TextureRegion(resourceSpritesheet, 128, 0, 32, 32));

        Texture structureSpritesheet = new Texture("art/structures-spritesheet.png");
        structures = new HashMap<String,TextureRegion>();
        structures.put("house-day"  , new TextureRegion(structureSpritesheet,   0, 0, 64, 64));
        structures.put("house-night", new TextureRegion(structureSpritesheet,  64, 0, 64, 64));
        structures.put("temple"     , new TextureRegion(structureSpritesheet, 128, 0, 64, 64));
        structures.put("warehouse"  , new TextureRegion(structureSpritesheet, 192, 0, 64, 64));

        Texture iconSpritesheet = new Texture("art/icon-spritesheet.png");
        icons = new HashMap<String,TextureRegion>();
        TextureRegion[][] iconRegions = TextureRegion.split(iconSpritesheet, 12, 12);
        icons.put("food",     iconRegions[0][0]);
        icons.put("wood",     iconRegions[0][1]);
        icons.put("stone",    iconRegions[0][2]);
        icons.put("iron",     iconRegions[0][3]);
        icons.put("marble",   iconRegions[0][4]);
        icons.put("gold",     iconRegions[0][5]);
        icons.put("grapes",   iconRegions[0][6]);
        icons.put("fuel",     iconRegions[1][0]);
        icons.put("circuits", iconRegions[1][1]);
        icons.put("meteor",   iconRegions[1][2]);
        icons.put("steel",    iconRegions[1][3]);
        icons.put("people",   iconRegions[1][4]);

//        sound = Gdx.audio.newSound("audio/sound.wav");
//        music = Gdx.audio.newMusic("audio/music.mp3");

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
        
        scampNames = Gdx.files.internal("text/scamp-names.txt").readString().split("\r\n|\r|\n");
    }

    public static void dispose() {
        libgdx.dispose();
        // todo : dispose the rest of the things
//        sound.dispose();
//        music.dispose();
        batch.dispose();
        hudBatch.dispose();
    }

    public static String randomName() {
        return scampNames[random.nextInt(scampNames.length)];
    }

}
