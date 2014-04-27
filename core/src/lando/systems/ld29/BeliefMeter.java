package lando.systems.ld29;

import lando.systems.ld29.core.Assets;
import lando.systems.ld29.util.Config;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class BeliefMeter {
	private float _value;
	
	private final Rectangle _bounds;
	
	public BeliefMeter(float width, float height) {
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
		
		Assets.HUDFont.setColor(Color.WHITE);
		Assets.HUDFont.draw(batch, "Belief", _bounds.x, _bounds.y + _bounds.height);
		
		// measure string length	
		int textWidth = 40;
		batch.end();
		
		Assets.shapes.begin(ShapeType.Filled);
		Assets.shapes.setColor(Color.BLACK);
		Assets.shapes.rect(_bounds.x + textWidth, _bounds.y, _bounds.width - textWidth, _bounds.height);
    
		Assets.shapes.setColor(Color.BLUE);
		Assets.shapes.rect(_bounds.x + textWidth, _bounds.y, (_bounds.width - textWidth) * _value, _bounds.height);
        
        Assets.shapes.identity();
        Assets.shapes.end();
		
		batch.begin();
	}
}
