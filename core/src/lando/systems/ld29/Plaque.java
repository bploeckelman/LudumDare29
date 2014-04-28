package lando.systems.ld29;

import lando.systems.ld29.core.Assets;
import lando.systems.ld29.scamps.ScampManager;
import lando.systems.ld29.scamps.ScampResources.ScampResourceType;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Plaque implements IToolTip {
	private int _value;
	private String _textValue = "0";
	
	private final ScampResourceType _resource;
	private final Rectangle _bounds;
	private final String _icon;
	
	public Plaque(ScampResourceType resource, float width, float height) {
		_resource = resource;
		_icon = resource.toString();
		_bounds = new Rectangle(0, 0, width, height);
	}
	
	public void setPosition(float x, float y) {
		_bounds.x = x;
		_bounds.y = y;
	}
	
	public void setValue(int value) {
		if (_value != value) {
			_value = value;
			_textValue = "" + value;
		}
	}
	
	public void update(ScampManager scampManager) {
		setValue(scampManager.scampResources.getScampResourceCount(_resource));
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
		batch.draw(Assets.icons.get(_icon), _bounds.x + background.getPadLeft()/2, _bounds.y + background.getPadRight()/2);
	}

	@Override
	public Rectangle getToolTipBounds() {
		return _bounds;
	}
}
