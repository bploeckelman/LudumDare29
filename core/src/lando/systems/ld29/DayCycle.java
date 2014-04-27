package lando.systems.ld29;

import lando.systems.ld29.core.Assets;
import lando.systems.ld29.util.Config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class DayCycle 
{
	private final float Dawn = 0;
	private final float Daylight = 120;
	private final float Dusk = 780;
	private final float Night = 900;
	
	private final Color DayColor = new Color(0.53f, 0.81f, 0.92f, 1);
	private final Color NightColor = Color.BLACK;
		
	private Color _color = new Color(NightColor);	
	private float _time = 0;
    private boolean dayNightChanged = false;
	
	public float Scale = 1.0f;
	
	private static Texture sunTex = new Texture("art/sky/sun.png");
	private final Sprite sunSprite;
	
	static Texture skyTex = new Texture("art/white.png");
	private final Sprite skySprite;
	
	private World _world;

	public DayCycle(World world) {
		_world = world;
		setTime(60); // 7 am
		sunSprite = new Sprite(sunTex);
		sunSprite.setSize(64,64);
		skySprite = new Sprite(skyTex);
		skySprite.setSize(World.gameWidth * 64, Config.window_height);
	}
	
	public void setTime(float time) {
		_time = time;
		calcColor(time);
	}

    public boolean isDay() {
        return _time >= Dawn && _time < Night;
    }
    public boolean hasNightEnded() {
        return dayNightChanged && isDay();
    }
    public boolean hasDayEnded() {
        return dayNightChanged && !isDay();
    }
	
	public void update(float dt) {
		
		float time = _time + (dt * Scale);
        dayNightChanged = false;
		if (time > 1440) { 
			time = 0;
            dayNightChanged = true;
		} else if(_time <= Night && time >= Night) {
            dayNightChanged = true;
        }

		setTime(time);
	}
	
	private void calcColor(float time)
	{
		Color dayColor = NightColor;
		
		if (time < Daylight) {
			dayColor = new Color(DayColor);
			dayColor.lerp(NightColor, (Daylight - time) / (Daylight - Dawn));
		} else if (time < Dusk) {
			dayColor = DayColor;
		} else if (time < Night) {
			dayColor = new Color(NightColor);
			dayColor.lerp(DayColor, (Night - time) / (Night - Dusk));
		}
		
		_color = dayColor;
	}
	
	public void render(SpriteBatch batch) {
 
		skySprite.setColor(_color);
	    skySprite.draw(batch);
		
		float sunX = ((_time - 120) / 640) * _world.gameWidth * 64;
		sunSprite.setPosition(sunX, 600);
		sunSprite.draw(batch);
	}
}
