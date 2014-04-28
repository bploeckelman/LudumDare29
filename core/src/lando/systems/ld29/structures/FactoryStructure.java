package lando.systems.ld29.structures;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;

public class FactoryStructure extends Structure {
    private static TextureRegion dayImg = Assets.structures.get("factory-day");
    private static TextureRegion nightImg = Assets.structures.get("factory-night");
    private Sprite day;
    private Sprite night;
    boolean isDaySprite  = true;

    public FactoryStructure(float x, World world) {
        super(x, world);

        day = new Sprite(dayImg);
        night = new Sprite(nightImg);
        setSprite(day);
    }

    public void update(float dt){
        super.update(dt);

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
