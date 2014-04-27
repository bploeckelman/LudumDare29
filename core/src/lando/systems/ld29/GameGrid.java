package lando.systems.ld29;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.ld29.blocks.*;

public class GameGrid {

	World parentWorld; // so we can access the world
	int width;
	int height;
	Texture caveTex = new Texture("art/cave.png");

	Block[] blocks;
	Block pushedOutBlock;
	float pushedTimer;
	float pushedDelay = .5f;

	public GameGrid(World world){
		parentWorld = world;
		blocks = new Block[parentWorld.gameWidth * parentWorld.gameHeight];
		width = world.gameWidth;
		height = world.gameHeight;
		caveTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		// TODO: make this more awesome?
		for (int y = 0; y < parentWorld.gameHeight ; y++){
			for (int x =0; x < parentWorld.gameWidth; x++)
			{
				blocks[x +(parentWorld.gameWidth * y)] = Block.getRandomBlock(x, y);
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
	}

	public void render(SpriteBatch batch){
		for (Block block : blocks){
			block.render(batch);
		}
		if (pushedTimer > 0){
			pushedOutBlock.setAlpha(pushedTimer/pushedDelay);
			pushedOutBlock.render(batch);

		}
		batch.draw(caveTex, 0, 0, 64 * World.gameWidth, 100);
	}

	public Block getBlock(int x, int y){
		return blocks[x + (width * y)];
	}

	public boolean pushUp(Block newBlock, int x){
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
