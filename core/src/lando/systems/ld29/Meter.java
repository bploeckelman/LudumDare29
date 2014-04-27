package lando.systems.ld29;

import lando.systems.ld29.core.Assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Meter {
	private float _value;
	
	private final Rectangle _bounds;
	private final String _header;
	
	public Meter(String header, float width, float height) {
		_header = header;
		_bounds = new Rectangle(0, 0, width, height);
	}
	
	public void setPosition(float x, float y) {
		_bounds.x = x;
		_bounds.y = y;
	}
	
	public void setValue(float value) {
		_value = value / 100f;
	}
	
	public void render(SpriteBatch batch) {
		NinePatch background = Assets.panelBrown;
		
		background.draw(
				batch,
	            _bounds.x,
	            _bounds.y,
	            _bounds.width,
	            _bounds.height
	        );
		
		Assets.HUDFont.setColor(Color.WHITE);
		Assets.HUDFont.draw(batch, _header, 
				_bounds.x + background.getPadLeft()/2, _bounds.y + _bounds.height - background.getPadTop()/2 - 2);
		
		float min = Assets.panelGreen.getPadLeft() + Assets.panelGreen.getPadRight();
		float width = min + (_bounds.width - min - (background.getPadLeft() + background.getPadRight() + 50)) * _value;
		
		Assets.panelGreen.draw(batch,
				_bounds.x + 50, _bounds.y + background.getPadBottom()/2, width, 
				_bounds.height - ((background.getPadTop() + background.getPadBottom())/2));
		
		
	}
}
