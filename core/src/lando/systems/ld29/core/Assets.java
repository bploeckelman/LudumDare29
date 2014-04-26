package lando.systems.ld29.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Assets {
    public static Random random;
    public static SpriteBatch batch;
    public static ShapeRenderer shapes;

    public static Texture libgdx;
    public static Map<String,TextureRegion> resources;

    //public static Sound sound;
    //public static Music music;

    public static void load() {
        random = new Random();
        batch  = new SpriteBatch();
        shapes = new ShapeRenderer();

        libgdx = new Texture("badlogic.jpg");
        libgdx.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        Texture resourcesSpritesheet = new Texture("art/resources-spritesheet.png");
        resources = new HashMap<String,TextureRegion>();
        resources.put("dirt"  ,new TextureRegion(resourcesSpritesheet, 0    , 0, 16, 16));
        resources.put("acorn" ,new TextureRegion(resourcesSpritesheet, 16   , 0, 16, 16));
        resources.put("gold"  ,new TextureRegion(resourcesSpritesheet, 32   , 0, 16, 16));
        resources.put("grapes",new TextureRegion(resourcesSpritesheet, 48   , 0, 16, 16));
        resources.put("iron"  ,new TextureRegion(resourcesSpritesheet, 64   , 0, 16, 16));
        resources.put("stone" ,new TextureRegion(resourcesSpritesheet, 80   , 0, 16, 16));
        resources.put("lava"  ,new TextureRegion(resourcesSpritesheet, 96   , 0, 16, 16));
        resources.put("water" ,new TextureRegion(resourcesSpritesheet, 112  , 0, 16, 16));

//        sound = Gdx.audio.newSound("audio/sound.wav");
//        music = Gdx.audio.newMusic("audio/music.mp3");
    }

    public static void dispose() {
        libgdx.dispose();
//        sound.dispose();
//        music.dispose();
        batch.dispose();
    }

}
