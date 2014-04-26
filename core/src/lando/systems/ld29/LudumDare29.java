package lando.systems.ld29;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class LudumDare29 extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    float x,y,r,g,b;
    float accum;
    boolean movingRight = true;
    boolean movingUp = true;

    static Random rand = new Random();

    static float THRESHOLD = 0.01667f;
    static float MOVE = 0.75f;

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        x = y = 0;
        r = g = b = 0;
        accum = 0;
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tick...
        accum += Gdx.graphics.getDeltaTime();
        if (accum > THRESHOLD) {
            accum = 0;

            // update horizontal direction
            if ((movingRight && x > (Gdx.graphics.getWidth() - img.getWidth()))
             ||(!movingRight && x < 0)) {
                movingRight = !movingRight;
            }
            // update vertical direction
            if ((movingUp && y > (Gdx.graphics.getHeight() - img.getHeight()))
             ||(!movingUp && y < 0)) {
                movingUp = !movingUp;
            }

            x += movingRight ? MOVE : -MOVE;
            y += movingUp ? MOVE : -MOVE;

            r += rand.nextBoolean() ? (float) Math.random() * 0.01f : 0;
            g += rand.nextBoolean() ? (float) Math.random() * 0.01f : 0;
            b += rand.nextBoolean() ? (float) Math.random() * 0.01f : 0;

            if (r > 1.f) r = 1.f;
            if (g > 1.f) g = 1.f;
            if (b > 1.f) b = 1.f;
        }

        batch.begin();
        batch.draw(img, x, y);
        batch.end();
    }
}
