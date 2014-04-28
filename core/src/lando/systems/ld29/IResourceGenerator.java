package lando.systems.ld29;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public interface IResourceGenerator {

	TextureRegion getResourceIcon();
	Rectangle getResourceBounds();
}
