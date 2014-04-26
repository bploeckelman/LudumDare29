package lando.systems.ld29;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.ld29.blocks.Block;

public class GameGrid {

	World parentWorld; // so we can access the world
	int width;
	int height;
	
	Block[] blocks;
	
	public GameGrid(World world){
		parentWorld = world;
		blocks = new Block[parentWorld.gameWidth * parentWorld.gameHeight];
		width = world.gameWidth;
		height = world.gameHeight;
		// TODO: make this more awesome?
		for (int y = 0; y < parentWorld.gameHeight ; y++){
			for (int x =0; x < parentWorld.gameWidth; x++)
			{
				blocks[x +(parentWorld.gameWidth * y)] = new Block(x, y);
			}
		}
	}
	
	public void update(float dt){
		for (Block block : blocks){
			block.update(dt);
		}
	}
	
	public void render(SpriteBatch batch){
		for (Block block : blocks){
			block.render(batch);
		}
	}
	
	public Block getBlock(int x, int y){
		return blocks[x + (width * y)];
	}
	
	public boolean pushUp(Block newBlock, int x){
		// Pop up what was on the top
		Block pushedOutBLock = blocks[x + (height -1) * width];
		// TODO:  Do Magic on pushedOutBLock
		
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
