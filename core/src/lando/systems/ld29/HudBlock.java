package lando.systems.ld29;

import lando.systems.ld29.core.Assets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class HudBlock extends Sprite implements IToolTip {
	
	private final String _name;
	private int _cost;
	private boolean _enabled;

	public HudBlock(String icon) {
		this(icon, Assets.blocks.get(icon));
	}
	
	public HudBlock(String icon, TextureRegion textureRegion) {
		super(textureRegion);
		_name = icon.toUpperCase();
		_cost = Assets.costs.get(icon);
	}

	@Override
	public Rectangle getToolTipBounds() {
		return getBoundingRectangle();
	}

	@Override
	public String getTitle() {
		return "ADD BLOCK";
	}

	@Override
	public String getText() {
		return _name;
	}
	
	public boolean isEnabled() {
		return _enabled;
	}
	
	public void update(float beliefLevel) {
		_enabled = (beliefLevel >= _cost);		
		setAlpha((_enabled) ? 1f : 0.2f);
	}	
}
