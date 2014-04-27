package lando.systems.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import lando.systems.ld29.blocks.*;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.screens.GameScreen;
import lando.systems.ld29.util.Config;

public class Hud {
    private static String[] blockNames = {
        "dirt", "stone", "iron", "acorn", "grapes"
    };

    static final float HUD_BLOCK_WIDTH = 32;
    static final float HUDX = Config.window_half_width -
                              ((blockNames.length * HUD_BLOCK_WIDTH) / 2);
    static final float HUDY = 0;

    private Sprite[] blocks;
    private World world;

    public Hud(World world){
        this.world = world;

        blocks = new Sprite[blockNames.length];
        float newX = HUDX;
        for(int i = 0; i < blockNames.length; i++){
            blocks[i] = new Sprite(Assets.blocks.get(blockNames[i]));
            blocks[i].setSize(HUD_BLOCK_WIDTH, HUD_BLOCK_WIDTH);
            blocks[i].setPosition(newX, HUDY);
            newX += HUD_BLOCK_WIDTH;
        }
    }

    boolean justClicked = true;
    float inputDelay = 0;
    public void update(float dt, Player player){
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && inputDelay <=0){
            if (justClicked == false){
                int x = (int)(player.xPos + .5f);
                Block block = getBlockForCoords(x);
                if(null != block) {
                    world.grid.pushUp(block, x);
                }
                inputDelay = 1; // Seconds until we can act again.
            }
            justClicked = true;
        } else {
            justClicked = false;
        }

        inputDelay = Math.max(0, inputDelay - dt);
    }

    public void render(SpriteBatch batch){
        for(Sprite block : blocks){
            block.draw(batch);
        }
    }

    private Block getBlockForCoords(int column){
        Vector3 clickPoint = GameScreen.hudCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        Block ret = null;
        for(int i = 0; i < blocks.length; i++){
            if(blocks[i].getBoundingRectangle().contains(clickPoint.x, clickPoint.y)){
                if (blockNames[i].equals("dirt")) {
                    ret = new DirtBlock(column, 0);

                } else if (blockNames[i].equals("stone")) {
                    ret = new StoneBlock(column, 0);

                } else if (blockNames[i].equals("iron")) {
                    ret = new IronBlock(column, 0);

                } else if (blockNames[i].equals("acorn")) {
                    ret = new AcornBlock(column, 0);

                } else if (blockNames[i].equals("grapes")) {
                    ret = new GrapesBlock(column, 0);

                }
            }
        }

        return ret;
    }

}
