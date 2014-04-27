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
    
    public Tooltip tooltip;

    public Hud(World world){
        this.world = world;
        tooltip = new Tooltip(world);
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
    
    public void update(float dt, Player player){
        tooltip.update(dt);
    	if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && player.inputDelay <=0 && player.xPos == player.xTarget){
            if (justClicked == false){
                int x = (int)(player.xPos + .5f);
                Block block = getBlockForCoords(x);
                if(null != block) {
                	block.setNewPosition(x, 0);
                	float cost = block.cost;
                    if (player.belief > cost && world.grid.pushUp(block, x)){
                    	player.belief -= cost;
                    	player.inputDelay = .5f; // Seconds until we can act again.
                    	player.animationTime = 0;
                    }
                }
                
            }
            justClicked = true;
        } else {
            justClicked = false;
        }

    }

    public void render(SpriteBatch batch){
        // Draw belief meter
        // TODO: Draw belief meter!

        // Draw block picker
        Assets.panelBrown.draw(
            batch,
            HUDX - Assets.panelBrown.getPadLeft(),
            HUDY - Assets.panelBrown.getPadBottom(),
            (blockNames.length * HUD_BLOCK_WIDTH) + (Assets.panelBrown.getPadRight() * 2),
            (Assets.panelBrown.getPadTop() * 2) + HUD_BLOCK_WIDTH
        );
        for(Sprite block : blocks){
            block.draw(batch);
        }
        
        tooltip.render(batch);
    }

    private Block getBlockForCoords(int column){
        Vector3 clickPoint = GameScreen.hudCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        Block ret = null;
        for(int i = 0; i < blocks.length; i++){
            if(blocks[i].getBoundingRectangle().contains(clickPoint.x, clickPoint.y)){
                if (blockNames[i].equals("dirt")) {
                    ret = new DirtBlock(column, -1);

                } else if (blockNames[i].equals("stone")) {
                    ret = new StoneBlock(column, -1);

                } else if (blockNames[i].equals("iron")) {
                    ret = new IronBlock(column, -1);

                } else if (blockNames[i].equals("acorn")) {
                    ret = new AcornBlock(column, -1);

                } else if (blockNames[i].equals("grapes")) {
                    ret = new GrapesBlock(column, -1);

                }
            }
        }

        return ret;
    }

}
