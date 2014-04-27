package lando.systems.ld29.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.*;

import java.util.Random;


public class Block {

    private static Random random = new Random();
    public static Block getRandomBlock(float x, float y){
        switch (random.nextInt(4)) {
            case 0:
                return new DirtBlock(x, y);
            case 1:
                return new StoneBlock(x, y);
            case 2:
                return new IronBlock(x, y);
            case 3:
                return new AcornBlock(x, y);
            case 4:
                return new GrapesBlock(x, y);
        }
        return null;
    }

	private Sprite sprite;
	float x;
	float y;

	float targetX;
	float targetY;

	static final float BLOCK_SPEED = 3;
	static final float BLOCK_WIDTH = 64;

    public String blockType;

	public Block(float x, float y) {
		this.x = x;
		this.y = y;
        setNewPosition(x, y);
	}

	public void setNewPosition(float x, float y){
		targetX = x;
		targetY = y;
	}

    public void setSprite(Sprite sprite){
        sprite.setSize(BLOCK_WIDTH, BLOCK_WIDTH);
        this.sprite = sprite;
    }
    public Sprite getSprite(){
        return this.sprite;
    }

	public void update(float dt){
		float dist = BLOCK_SPEED * dt;
		if (dist > Math.abs(targetX - x)){
			x = targetX;
		} else {
			x = x + dist; // TODO: This can't get backwards
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

	public void render(SpriteBatch batch){
		getSprite().setPosition(x * BLOCK_WIDTH, 100 + y * BLOCK_WIDTH);
		getSprite().draw(batch);
	}
}
