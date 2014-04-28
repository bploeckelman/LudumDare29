package lando.systems.ld29.structures;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;

public class TempleStructure extends Structure {
    private static TextureRegion img = Assets.structures.get("temple");

    public static final Map<String, Integer> buildCost;
    static {
    	Map<String, Integer> aMap = new HashMap<String, Integer>();
    	aMap.put("wood", 15);
    	aMap.put("stone", 10);
    	aMap.put("marble", 6);
    	aMap.put("meteor", 1);
    	buildCost = Collections.unmodifiableMap(aMap);
    }
    
    public TempleStructure(float x, World world) {
        super(x, world);

        setSprite(new Sprite(img));
        name = "temple";
    }
}
