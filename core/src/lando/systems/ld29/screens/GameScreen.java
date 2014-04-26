package lando.systems.ld29.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import lando.systems.ld29.LudumDare29;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.util.Config;
import lando.systems.ld29.util.Utils;

public class GameScreen implements Screen {

    private final OrthographicCamera camera;
    private final LudumDare29 game;
    private final World world;
    private float accum = 0.f;

    public GameScreen(LudumDare29 game) {
        super();

        this.game = game;
        world = new World();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Config.window_width, Config.window_height);
    }

    public void update(float dt) {
        if (game.input.isKeyDown(Keys.ESCAPE)) {
            Gdx.app.exit();
        } else if (Gdx.input.justTouched()) {
            // do things
        }
        world.update(dt);
        
        // Upate Camera
        //camera.position.x = world.player.xPos * 64;
        camera.position.x = Utils.clamp((world.player.xPos+.5f) * 64, Config.window_half_width, world.gameWidth * 64 - Config.window_half_width);
        camera.update();
        accum += dt;
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl20.glClearColor(0.53f, 0.81f, 0.92f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Assets.shapes.begin(ShapeType.Filled);

        drawShapes();

        Assets.shapes.identity();
        Assets.shapes.end();
        
        SpriteBatch batch = Assets.batch;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.render(batch);
        batch.end();
    }

    private float rot = 0f;
    private Color bgcolor = new Color(0.53f, 0.81f, 0.92f, 1);
    private void drawShapes() {
        Rectangle r = new Rectangle(Config.window_half_width / 2, -40,
                Config.window_half_width, Config.window_height + 80);

        float boxWidth = 50;
        float boxHeight = 50;
        float x = r.x + (r.width/2);
        float y = r.y + (r.height/2);
        rot -= 15f;

        Assets.shapes.identity();

        Assets.shapes.setColor(bgcolor);
        Assets.shapes.rect(r.x, r.y, r.width, r.height);

        Assets.shapes.setColor(Color.RED);
        Assets.shapes.translate(x, y, 0);
        Assets.shapes.rotate(0,0,1,rot);
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
