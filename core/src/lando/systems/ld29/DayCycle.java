package lando.systems.ld29;

import lando.systems.ld29.core.Assets;
import lando.systems.ld29.util.Config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
	
	public float Scale = 1.0f;
	
	private World _world;

	public DayCycle(World world) {
		_world = world;
		setTime(60); // 7 am
	}
	
	public void setTime(float time) {
		_time = time;
		calcColor(time);
	}
	
	public void update(float dt) {
		
		float time = _time + (dt * Scale);
		if (time > 1440) { 
			time = 0;
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

		batch.end();
		
        Assets.shapes.begin(ShapeType.Filled);
	 
        Assets.shapes.setColor(_color);
	    Assets.shapes.rect(0, Config.window_half_height, Config.window_width, Config.window_half_height);

		Assets.shapes.end();
		
		batch.begin();
	}
}
