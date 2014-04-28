package lando.systems.ld29.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import lando.systems.ld29.LudumDare29;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.util.Config;

public class TitleScreen implements Screen {

    private final OrthographicCamera camera;
    private final LudumDare29 game;

    private float accum = 0.f;

    public TitleScreen(LudumDare29 game) {
        super();

        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Config.window_width, Config.window_height);
    }

    public void update(float dt) {
        if (game.input.isKeyDown(Keys.ESCAPE)) {
            Gdx.app.exit();
        } else if (Gdx.input.justTouched()) {
            game.setScreen(game.gameScreen);
            Assets.dayAmbient.play();
        }

        accum += dt;
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl20.glClearColor(0.53f, 0.81f, 0.92f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Assets.batch.begin();
        Assets.batch.draw(Assets.libgdx, 0, 0, Config.window_width,
                Config.window_height);
        Assets.panelBrown.draw(Assets.batch, 540, 150, 200, 80);
        Assets.gameFont.draw(Assets.batch, "Click To Start", 560, 200);
        Assets.batch.end();

//        Assets.shapes.begin(ShapeType.Filled);

//        drawShapes();

//        Assets.shapes.identity();
//        Assets.shapes.end();
    }

    private float rot = 0f;
    private Color bgcolor = new Color(0.53f, 0.81f, 0.92f, 1);
    private void drawShapes() {
        Rectangle r = new Rectangle(Config.window_half_width / 2, -40,
                Config.window_half_width, Config.window_height + 80);

        Assets.shapes.identity();
        Assets.shapes.setColor(bgcolor);
        Assets.shapes.rect(r.x, r.y, r.width, r.height);

        float boxWidth = 100;
        float boxHeight = 100;
        float x = r.x + (r.width/2);
        float y = r.y + (r.height/2);
        rot += 5f;

        Assets.shapes.translate(x, y, 0);
        Assets.shapes.rotate(0,0,1,rot);

        Assets.shapes.setColor(Color.RED);
        Assets.shapes.rect(-boxWidth/2, -boxHeight/2, boxWidth, boxHeight);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        game.input.reset();
    }

    @Override
    public void hide() {
        game.input.reset();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}
