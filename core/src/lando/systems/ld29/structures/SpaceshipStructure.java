package lando.systems.ld29.structures;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;

public class SpaceshipStructure extends Structure {
    private static TextureRegion img = Assets.structures.get("spaceship");

    public static final Map<String, Integer> buildCost;
    static {
    	Map<String, Integer> aMap = new HashMap<String, Integer>();
    	aMap.put("wood", 15);
    	aMap.put("stone", 10);
    	aMap.put("marble", 6);
    	buildCost = Collections.unmodifiableMap(aMap);
    }
    
    public SpaceshipStructure(float x, World world) {
        super(x, world);

        setSprite(new Sprite(img));
        name = "spaceship";
    }
}
