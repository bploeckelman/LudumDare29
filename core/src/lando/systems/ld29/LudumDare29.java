package lando.systems.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.core.Input;
import lando.systems.ld29.screens.GameScreen;
import lando.systems.ld29.screens.TitleScreen;


public class LudumDare29 extends Game {

    public Input input;

    public TitleScreen titleScreen;
    public GameScreen gameScreen;

    @Override
    public void create () {
        Assets.load();

        input = new Input();
        Gdx.input.setInputProcessor(input);
        Gdx.input.setCursorCatched(false);

        titleScreen = new TitleScreen(this);
        gameScreen  = new GameScreen(this);
        setScreen(titleScreen);
    }

    @Override
    public void dispose() {
        Assets.dispose();
    }

//    @Override
//    public void render () {
//        if (input.isKeyDown(com.badlogic.gdx.Input.Keys.ESCAPE)) {
//            Gdx.app.exit();
//        }
//
//        Gdx.gl.glClearColor(r, g, b, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        // tick...
//        accum += Gdx.graphics.getDeltaTime();
//        if (accum > THRESHOLD) {
//            accum = 0;
//
//            // update horizontal direction
//            if ((movingRight && x > (Gdx.graphics.getWidth() - img.getWidth()))
//             ||(!movingRight && x < 0)) {
//                movingRight = !movingRight;
//            }
//            // update vertical direction
//            if ((movingUp && y > (Gdx.graphics.getHeight() - img.getHeight()))
//             ||(!movingUp && y < 0)) {
//                movingUp = !movingUp;
//            }
//
//            x += movingRight ? MOVE : -MOVE;
//            y += movingUp ? MOVE : -MOVE;
//
//            r += rand.nextBoolean() ? (float) Math.random() * 0.01f : 0;
//            g += rand.nextBoolean() ? (float) Math.random() * 0.01f : 0;
//            b += rand.nextBoolean() ? (float) Math.random() * 0.01f : 0;
//
//            if (r > 1.f) r = 1.f;
//            if (g > 1.f) g = 1.f;
//            if (b > 1.f) b = 1.f;
//        }
//
//        batch.begin();
//        batch.draw(img, x, y);
//        batch.end();
//    }
}
