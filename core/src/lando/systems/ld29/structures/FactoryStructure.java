package lando.systems.ld29.structures;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    public static final Map<String, Integer> buildCost;
    static {
    	Map<String, Integer> aMap = new HashMap<String, Integer>();
    	aMap.put("wood", 15);
    	aMap.put("stone", 15);
    	aMap.put("iron", 10);
    	buildCost = Collections.unmodifiableMap(aMap);
    }
    
    public FactoryStructure(float x, World world) {
        super(x, world);

        day = new Sprite(dayImg);
        night = new Sprite(nightImg);
        setSprite(day);
        name = "factory";
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
