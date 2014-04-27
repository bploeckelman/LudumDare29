package lando.systems.ld29;

import lando.systems.ld29.blocks.Block;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.scamps.Scamp;
import lando.systems.ld29.screens.GameScreen;
import lando.systems.ld29.util.Config;
import lando.systems.ld29.util.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Tooltip {

	World world;
	String header;
	String text;
	Rectangle rect;
	boolean show;
	float alpha = -1;
	NinePatch panel;

	
	
	public Tooltip(World world){
		this.world = world;
		rect = new Rectangle();
		panel = new NinePatch(Assets.panelBrown);

	}
	
	float offset = 10;
	Rectangle targetRect = new Rectangle();
	public void update(float dt){
		show = false;
		Vector3 hudClickPoint = GameScreen.hudCamera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		Vector3 gameClickPoint = GameScreen.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		
		Block under = world.grid.getBlockFromPos(gameClickPoint.x, gameClickPoint.y);
		
		if (under != null){
			header = under.blockType.toUpperCase();
			text = under.toolTipString.toUpperCase();
			show = true;
		} else {
            Scamp scamp = world.scampManager.getScampFromPos(gameClickPoint.x, gameClickPoint.y);
            if (scamp != null) {
                header = scamp.toString();
                text = scamp.getCurrentStateName();
                show = true;
            }
        }

		if (show){
			alpha = Math.min(1, alpha + 2*dt);
			
			TextBounds headerBounds = Assets.TooltipHeaderFont.getBounds(header);
			TextBounds textBounds = Assets.TooltipTextFont.getBounds(text);
			float x = hudClickPoint.x + offset;
			float y = hudClickPoint.y + offset;
			float width = Math.max(textBounds.width, headerBounds.width) + 40;
			float height = 100;
			if (hudClickPoint.y > Config.window_half_height){
				y -= height + 2* offset;
			}
			if (hudClickPoint.x > Config.window_half_width){
				x -= width + 2* offset;
			}
			
	
			
			rect.set(x , y, width, height);
		} else {
			alpha -= dt * 3;
		}
		alpha = Utils.clamp(alpha, 0, 1);
	}
	
	public void render(SpriteBatch batch){
		if (true){
			Color col = new Color(1,1,1,alpha);
			panel.setColor(col);
			panel.draw(batch, rect.x, rect.y, rect.width, rect.height);
			Assets.TooltipHeaderFont.setColor(col);
			Assets.TooltipHeaderFont.draw(batch, header, rect.x + 20, rect.y + rect.height - 20);
			Assets.TooltipTextFont.setColor(col);
			Assets.TooltipTextFont.draw(batch, text, rect.x + 20, rect.y +  40);

		}
	}
	
}
