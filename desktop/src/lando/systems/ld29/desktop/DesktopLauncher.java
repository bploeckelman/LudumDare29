package lando.systems.ld29.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import lando.systems.ld29.LudumDare29;
import lando.systems.ld29.util.Config;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Config.window_width;
        config.height = Config.window_height;
        new LwjglApplication(new LudumDare29(), config);
	}
}
