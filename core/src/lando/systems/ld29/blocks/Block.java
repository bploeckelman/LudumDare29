package lando.systems.ld29.blocks;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;

import lando.systems.ld29.Global;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.resources.Resource;

public class Block {

    public static Block getRandomBlock(float x, float y){
        switch (Assets.random.nextInt(20)) {
            case 0:
                return new WheatBlock(x, y);
            case 1:
                return new StoneBlock(x, y);
            case 2:
                return new IronBlock(x, y);
            case 3:
                return new AcornBlock(x, y);
            case 4:
                return new GrapesBlock(x, y);
            default:
            	return new DirtBlock(x, y);
            	
        }
        
    }

	private Sprite sprite;
	float x;
	float y;
	
	float targetX;
	float targetY;

	public static final int BLOCK_SPEED = 3;
	public static final int BLOCK_WIDTH = 64;

    public String blockType = "F*CK";
    public String toolTipString = "YOU MISSED ME!";
    public Color fountainColor;
    public float cost;

	public Block(float x, float y) {
		this.x = x;
		this.y = y;
        setNewPosition(x, y);
        fountainColor = Color.WHITE;
        cost = -100000;
	}
	
	public void setCost()
	{			
		Integer value = Assets.costs.get(blockType);
		cost = (value != null) ? value.intValue() : 0;
	}

    public float getX() { return x / BLOCK_WIDTH; }
    public float getY() { return y / BLOCK_WIDTH; }

	public void setNewPosition(float x, float y){
		targetX = x;
		targetY = y;
	}
	
	public void setRealPosition(float x, float y){
		targetX = x;
		targetY = y;
		this.x = x;
		this.y = y;
	}

    public void setSprite(Sprite sprite){
        sprite.setSize(BLOCK_WIDTH, BLOCK_WIDTH);
        this.sprite = sprite;
        setCost();
    }
    public Sprite getSprite(){
        return this.sprite;
    }

	public void update(float dt){
		float dist = BLOCK_SPEED * dt;
		if (dist > Math.abs(targetX - x)){
			x = targetX;
		} else {
			float sign = 1;
			if (targetX < x) sign = -1;
			x = x + sign * dist; // TODO: This can't get backwards
		}
		if (dist > Math.abs(targetY - y)){
			y = targetY;
		} else {
			y = y + dist; // TODO: This can't get backwards
		}
		
	}
	
	public void setAlpha(float amount){
		sprite.setAlpha(amount);
	}
	
	public void render(SpriteBatch batch, float xShift){
		if (y <= 0){
			sprite.setAlpha(1+ y);
		}
		getSprite().setPosition(x * BLOCK_WIDTH + xShift, Global.UNDERGROUND_HEIGHT + y * BLOCK_WIDTH);
		getSprite().draw(batch);
	}
	
	public Resource MakeResource(){
		return null;
	}
	
}
