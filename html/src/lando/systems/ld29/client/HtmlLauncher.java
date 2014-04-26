package lando.systems.ld29.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import lando.systems.ld29.LudumDare29;
import lando.systems.ld29.util.Config;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(
                        Config.window_width,
                        Config.window_height);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new LudumDare29();
        }
}