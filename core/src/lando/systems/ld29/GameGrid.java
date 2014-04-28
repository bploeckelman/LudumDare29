package lando.systems.ld29;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.ld29.blocks.*;
import lando.systems.ld29.core.Assets;

public class GameGrid {

	World parentWorld; // so we can access the world
	int width;
	int height;
	Texture caveTex = new Texture("art/cave.png");

	Block[] blocks;
	Block pushedOutBlock;
	float pushedTimer;
	float pushedDelay = .5f;
	float earthQuakeTime = 0;
	float EARTHQUAKEMAXTIME = 3;

	public GameGrid(World world){
		parentWorld = world;
		blocks = new Block[World.gameWidth * World.gameHeight];
		width = World.gameWidth;
		height = World.gameHeight;
		caveTex.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		// TODO: make this more awesome?
		for (int y = 0; y < World.gameHeight ; y++){
			for (int x =0; x < World.gameWidth; x++)
			{
				blocks[x +(World.gameWidth * y)] = Block.getRandomBlock(x, y);
			}
		}
	}

	public void update(float dt){
		for (Block block : blocks){
			block.update(dt);
		}

		if (pushedTimer > 0){
			pushedTimer = Math.max(0, pushedTimer - dt);
			pushedOutBlock.update(dt);

		}
		
		if (earthQuakeTime > EARTHQUAKEMAXTIME/2 && earthQuakeTime - dt < EARTHQUAKEMAXTIME/2){
			for (int i = 0; i < height; i++){
				switch (Assets.random.nextInt(3)){
				case 0:
					shiftLeft(i);
					break;
				case 1: 
					shiftRight(i);
					break;
				}
			}
		}
		
		
		if (Assets.random.nextFloat() > .9995 && earthQuakeTime <= -3){
			earthQuakeTime = EARTHQUAKEMAXTIME;
		}
		earthQuakeTime -= dt;
	}

	public void render(SpriteBatch batch){
		float xShift = 0;
		if (earthQuakeTime > EARTHQUAKEMAXTIME/2){
			float amp = 1 - 2 * Math.abs(.5f - earthQuakeTime/EARTHQUAKEMAXTIME);
			xShift = (float) (Math.sin(earthQuakeTime * 23) * amp * 20);
		}
		for (Block block : blocks){
			block.render(batch, xShift);
		}
		if (pushedTimer > 0){
			pushedOutBlock.setAlpha(pushedTimer/pushedDelay);
			pushedOutBlock.render(batch, xShift);

		}
		batch.draw(caveTex, 0, 0, 64 * World.gameWidth, Global.UNDERGROUND_HEIGHT);
	}

	public Block getBlock(int x, int y){
		return blocks[x + (width * y)];
	}
	
	public Block getBlockFromPos(float x, float y){
		if (y > Global.UNDERGROUND_HEIGHT && y < Global.UNDERGROUND_HEIGHT + 64 * World.gameHeight){
			return getBlock((int)x/64, (int)(y-Global.UNDERGROUND_HEIGHT)/64);
		}
		
		return null;
	}

	public void shiftLeft(int y){
		Block first = blocks[y * width];
		for (int x = 1; x < width; x++){
			Block next = blocks[(x) + (y*width)];
			next.setNewPosition(x-1, y);
			blocks[(x-1) + (y*width)] = next;
		}
		
		first.setRealPosition(width , y);
		first.setNewPosition(width -1, y);
		blocks[(width-1) + (y*width)] = first;
	}
	
	public void shiftRight(int y){
		Block last = blocks[(width-1) + y * width];
		for (int x = width - 2; x >= 0; x--){
			Block next = blocks[(x) + (y*width)];
			next.setNewPosition(x+1, y);
			blocks[(x+1) + (y*width)] = next;
		}
		
		last.setRealPosition(-1 , y);
		last.setNewPosition(0, y);
		blocks[y*width] = last;
	}
	
	public boolean pushUp(Block newBlock, int x){
		if (earthQuakeTime > EARTHQUAKEMAXTIME/2) return false;
		// Pop up what was on the top
		pushedOutBlock = blocks[x + (height -1) * width];
		pushedTimer = pushedDelay;
		pushedOutBlock.setNewPosition(x, height);
		parentWorld.rManager.createResource(pushedOutBlock, x);

		// Now we push everything up
		for (int i = height -2; i >= 0; i--){
			Block top = blocks[x+(width * i)];
			top.setNewPosition(x, i + 1);
			blocks[x+(width * (i + 1))] = top;
		}

		// Now add it to the bottom
		blocks[x] = newBlock;

		return true;
	}
}
