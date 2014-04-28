package lando.systems.ld29.structures;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.scamps.Scamp;

import java.util.ArrayList;

public class HouseStructure extends Structure {
    private static TextureRegion dayImg = Assets.structures.get("house-day");
    private static TextureRegion nightImg = Assets.structures.get("house-night");
    private Sprite day;
    private Sprite night;
    boolean isDaySprite  = true;

    public HouseStructure(float x, World world){
        super(x, world);

        setMaxCapacity(5);
        scamps = new ArrayList<Scamp>(getMaxCapacity());
        day = new Sprite(dayImg);
        night = new Sprite(nightImg);
        setSprite(day);
    }

    public void update(float dt){
        super.update(dt);

        if(getWorld().dayCycle.hasNightEnded()){
            evict();
        }

        if(getWorld().dayCycle.isDay() != isDaySprite) {
            if (isDaySprite) {
                setSprite(night);
                isDaySprite = false;
            } else {
                setSprite(day);
                isDaySprite = true;
            }
        }
    }
}
