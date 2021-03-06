package lando.systems.ld29.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import lando.systems.ld29.LudumDare29;
import lando.systems.ld29.World;
import lando.systems.ld29.blocks.Block;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.scamps.ScampManager;
import lando.systems.ld29.util.Config;
import lando.systems.ld29.util.Utils;

public class GameScreen implements Screen {

	public static final OrthographicCamera camera  = new OrthographicCamera();;
    public static final OrthographicCamera hudCamera = new OrthographicCamera();
    private final LudumDare29 game;
    private final World world;
    private float accum = 0.f;
    private SpriteBatch batch;
    private SpriteBatch hudBatch;
    private SpriteBatch shaderBatch;
    ShaderProgram shader;
    ShaderProgram defaultShader;

    public GameScreen(LudumDare29 game) {
        super();

        this.game = game;
        world = new World();
        
        camera.setToOrtho(false, Config.window_width, Config.window_height);
        hudCamera.setToOrtho(false, Config.window_width, Config.window_height);

        batch = Assets.batch;
        hudBatch = Assets.hudBatch;
        //ShaderProgram.pedantic = false;
        
        
        final String vertexShader = Gdx.files.internal("shaders/vertex.glsl").readString();
        final String fragmentShader = Gdx.files.internal("shaders/fragment.glsl").readString();
        shader = new ShaderProgram(vertexShader, fragmentShader);
        defaultShader = SpriteBatch.createDefaultShader();
        shaderBatch = new SpriteBatch(1000, shader);
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
        camera.position.x = Utils.clamp((world.player.xPos+.5f) * Block.BLOCK_WIDTH,
                Config.window_half_width, world.gameWidth * Block.BLOCK_WIDTH - Config.window_half_width);
        camera.update();
        accum += dt;
    }

    private FrameBuffer m_fbo = null;
    private TextureRegion m_fboRegion = null;
    
    @Override
    public void render(float delta) {
    	delta = Math.min(delta, 1/20.0f); // Limit big lag spikes
    	//delta *=3;
        update(delta);



        if(m_fbo == null)
        {
            // m_fboScaler increase or decrease the antialiasing quality

            m_fbo = new FrameBuffer(Format.RGBA4444, Config.window_width, Config.window_height, false);
            m_fboRegion = new TextureRegion(m_fbo.getColorBufferTexture(), Config.window_width, Config.window_height );
            m_fboRegion.flip(false, true);
        }

        m_fbo.begin();
        
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
      
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.render(batch, hudBatch);
        batch.end();
        
        if(m_fbo != null)
        {
            m_fbo.end();
            shaderBatch.setProjectionMatrix(hudCamera.combined);
            
            shaderBatch.begin();         
            shaderBatch.draw(m_fboRegion, 0, 0, Config.window_width, Config.window_height);               
            shaderBatch.end();
            
            //ShaderProgram.POSITION_ATTRIBUTE
        }   
        
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
