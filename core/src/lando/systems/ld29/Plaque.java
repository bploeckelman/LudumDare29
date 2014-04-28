package lando.systems.ld29;

import lando.systems.ld29.core.Assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Plaque {
	private int _value;
	private String _textValue = "0";
	
	private final Rectangle _bounds;
	private final String _icon;
	
	public Plaque(String resource, float width, float height) {
		_icon = resource.toString();
		_bounds = new Rectangle(0, 0, width, height);
	}
	
	public void setPosition(float x, float y) {
		_bounds.x = x;
		_bounds.y = y;
	}
	
	public void setValue(int value) {
		_value = value;
		_textValue = "" + value;
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
		Assets.HUDFont.draw(batch, _textValue, 
				_bounds.x + _bounds.height, _bounds.y + _bounds.height - background.getPadTop()/2 - 2);
		
		
		/*
		Assets.HUDFont.setColor(Color.WHITE);
		Assets.HUDFont.draw(batch, _header, 
				_bounds.x + background.getPadLeft()/2, _bounds.y + _bounds.height - background.getPadTop()/2 - 2);
		
		float min = Assets.panelGreen.getPadLeft() + Assets.panelGreen.getPadRight();
		float width = min + (_bounds.width - min - ((background.getPadLeft() + background.getPadRight())/2 + 50)) * _value;
		
		Assets.panelGreen.draw(batch,
				_bounds.x + 50, _bounds.y + background.getPadBottom()/2, width, 
				_bounds.height - ((background.getPadTop() + background.getPadBottom())/2));
		*/
		
	}
}
