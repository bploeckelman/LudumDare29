package lando.systems.ld29.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class Assets {
    public static Random random;
    public static SpriteBatch batch;
    public static ShapeRenderer shapes;

    public static Texture libgdx;

    //public static Sound sound;
    //public static Music music;

    public static void load() {
        random = new Random();
        batch  = new SpriteBatch();
        shapes = new ShapeRenderer();

        libgdx = new Texture("badlogic.jpg");
        libgdx.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

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
