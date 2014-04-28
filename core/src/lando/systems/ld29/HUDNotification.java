package lando.systems.ld29;

import lando.systems.ld29.core.Assets;
import lando.systems.ld29.util.Config;
import lando.systems.ld29.util.Utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.math.Rectangle;

public class HUDNotification {
	
	public float ttl;
	public Rectangle rect;
	public Texture icon;
	public String text;
	public float targetY;
	private float moveSpeed = 50f;
	
	public HUDNotification(Texture icon, String text, float y){
		this.icon = icon;
		this.text = text;
		targetY = y;
		ttl = 5;
		this.rect = new Rectangle(Config.window_width - 200, targetY - 50, 199, 40);
	}

	
	public void update(float dt){
		ttl -= dt;
		float trueTarget = targetY - 50;
		if (rect.y != trueTarget){
			float dist = moveSpeed * dt;
			if (dist > trueTarget - rect.y){
				rect.y = trueTarget;
			} else {
				rect.y +=dist;
			}
		}
	}
	
	public void render (SpriteBatch batch){
		float alpha = Utils.clamp(ttl, 0, 1);
		Assets.panelBrown.setColor(new Color(1,1,1,alpha));
		Assets.HUDFont.setColor(new Color(1,1,1,alpha));
		TextBounds bounds = Assets.HUDFont.getBounds(text);
		Assets.panelBrown.draw(batch, rect.x, rect.y, rect.width, rect.height);
		Assets.HUDFont.draw(batch, text, rect.x + rect.width/2 - bounds.width/2, rect.y +rect.height- 10);
		
		Assets.panelBrown.setColor(Color.WHITE);
		Assets.HUDFont.setColor(Color.WHITE);
	}
}
