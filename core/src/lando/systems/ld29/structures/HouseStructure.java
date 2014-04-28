package lando.systems.ld29.structures;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.scamps.Scamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HouseStructure extends Structure {
    private static TextureRegion dayImg = Assets.structures.get("house-day");
    private static TextureRegion nightImg = Assets.structures.get("house-night");
    private Sprite day;
    private Sprite night;
    boolean isDaySprite  = true;
    
    public static final Map<String, Integer> buildCost;
    static {
    	Map<String, Integer> aMap = new HashMap<String, Integer>();
    	aMap.put("wood", 3);
    	aMap.put("stone", 3);
    	buildCost = Collections.unmodifiableMap(aMap);
    }
    
    public HouseStructure(float x, World world){
        super(x, world);

        setMaxCapacity(5);
        scamps = new ArrayList<Scamp>(getMaxCapacity());
        day = new Sprite(dayImg);
        night = new Sprite(nightImg);
        setSprite(day);
    }


    public void evict(){
        // Maybe create a baby?
        if(getCapacity() >= 2 && Assets.random.nextFloat() > .75f){
            getWorld().scampManager.scamps.add(new Scamp(x));
        }

        super.evict();
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
