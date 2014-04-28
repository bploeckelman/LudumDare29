package lando.systems.ld29;

import com.badlogic.gdx.math.Rectangle;

public interface IToolTip {
	Rectangle getToolTipBounds();
	String getTitle();
	String getText();
}
