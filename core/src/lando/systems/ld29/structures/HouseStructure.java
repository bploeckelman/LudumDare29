package lando.systems.ld29.structures;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import lando.systems.ld29.Global;
import lando.systems.ld29.IResourceGenerator;
import lando.systems.ld29.World;
import lando.systems.ld29.blocks.Block;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.scamps.Scamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HouseStructure extends Structure implements IResourceGenerator {
    private static TextureRegion dayImg = Assets.structures.get("house-day");
    private static TextureRegion nightImg = Assets.structures.get("house-night");
    private Sprite day;
    private Sprite night;
    boolean isDaySprite  = true;
    public static int HouseLimit =2;

    
    public static final Map<String, Integer> buildCost;
    static {
    	Map<String, Integer> aMap = new HashMap<String, Integer>();
    	aMap.put("wood", 3);
    	aMap.put("stone", 3);
    	buildCost = Collections.unmodifiableMap(aMap);
    	
    }
    
    public HouseStructure(float x, World world){
        super(x, world);

        setMaxCapacity(HouseLimit);
        scamps = new ArrayList<Scamp>(getMaxCapacity());
        day = new Sprite(dayImg);
        night = new Sprite(nightImg);
        setSprite(day);
        name = "house";
    }


    public void evict(){
        // Maybe create a baby? 
    	if(getWorld().scampManager.spaceForMoreScamps()){   		
        	World world = getWorld();
            world.scampManager.addScamps(new Scamp(x));
            world.displayResourceGather(this,  1);
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


	@Override
	public TextureRegion getResourceIcon() {
		return Assets.icons.get("PEOPLE");
	}


	@Override
	public Rectangle getResourceBounds() {
		Rectangle bounds = getSprite().getBoundingRectangle();
		
		float width = 15;
		float height = 15;
		
		return new Rectangle(bounds.x + ((bounds.width - width)/2), bounds.y - height, width, height);
	}
}
