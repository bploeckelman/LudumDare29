package lando.systems.ld29;

import lando.systems.ld29.blocks.Block;
import lando.systems.ld29.util.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class Player {
	
	static Texture img = new Texture("badlogic.jpg");
	final Sprite sprite;
	
	public float xPos;
	private World world;
	private final float SPEED = 5;
	
	public Player(World world) {
		this.world = world;
		sprite = new Sprite(img);
		sprite.setSize(64,64);
		xPos = 10;
	}
	
	boolean justClicked = true;
	float inputDelay = 0;
	public void update(float dt){
		if (Gdx.input.isKeyPressed(Keys.A)){
			xPos -= SPEED * dt;
		}
		if (Gdx.input.isKeyPressed(Keys.D)){
			xPos += SPEED * dt;
		}
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && inputDelay <=0){
			if (justClicked == false){
				int x = (int)(xPos + .5f);
				world.grid.pushUp(new Block(x, 0), x);
				inputDelay = 1; // Seconds until we can act again.
			}
			justClicked = true;
		} else {
			justClicked = false;
		}
		
		inputDelay = Math.max(0, inputDelay - dt);
		xPos = Utils.clamp(xPos, 0, world.gameWidth);
	}
	
	
	public void render(SpriteBatch batch){
		sprite.setPosition(xPos * 64, 10);
		sprite.draw(batch);
	}
	
}
