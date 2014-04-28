package lando.systems.ld29.resources;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import lando.systems.ld29.Global;
import lando.systems.ld29.IToolTip;
import lando.systems.ld29.blocks.Block;
import lando.systems.ld29.core.Assets;

/**
 * Created by jhoopes on 4/26/14.
 */
public abstract class Resource implements IToolTip {

    protected Sprite sprite;
    float x;
    float y;
    static final float RESOURCE_WIDTH = Block.BLOCK_WIDTH;
    public static final float RESOURCE_GROUND_LEVEL_OFFSET = (RESOURCE_WIDTH * 5) / 16;
    protected int resourceCount;
    float alpha = 0;

    public Resource(float x){
    	
        // set pixel x and y
    	this.x = x * Block.BLOCK_WIDTH;
    	this.y = Global.GROUND_LEVEL - RESOURCE_GROUND_LEVEL_OFFSET;
        
        alpha = 0;
        // Generate resource count
        this.resourceCount = 2 + Assets.random.nextInt(8);
    }

    public void createSprite(TextureRegion img){
        sprite = new Sprite(img);
        sprite.setSize(RESOURCE_WIDTH, RESOURCE_WIDTH);
    }

    public void remove(){

    }

    public Sprite getSprite(){
        return this.sprite;
    }

    public void update(float dt){
    	alpha = Math.min(alpha + dt, 1);
    	
    }
    
    public void render(SpriteBatch batch){
        getSprite().setPosition(x, y);
        getSprite().setAlpha(alpha);
        getSprite().draw(batch);
    }

    public int getResourceCount(){
        return this.resourceCount;
    }

    public void setResourceCount(int newRCount){
        this.resourceCount = newRCount;
    }

    public float getX() { return x / RESOURCE_WIDTH; }
    public float getY() { return y / RESOURCE_WIDTH; }

    public abstract String getName();
    public abstract String resourceName();
    

	@Override
	public Rectangle getToolTipBounds() {
		return (sprite != null) ? sprite.getBoundingRectangle() : new Rectangle();
	}

	@Override
	public String getTitle() {
		return "RESOURCE";
	}

	@Override
	public String getText() {
		return getName().toUpperCase();
	}
}
