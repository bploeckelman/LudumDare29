package lando.systems.ld29.structures;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.scamps.ScampResources.ScampResourceType;
import lando.systems.ld29.util.Utils;

public class TempleStructure extends Structure {
    private static TextureRegion img = Assets.structures.get("temple");

    private float grapeTime =0;
    
    public static final Map<String, Integer> buildCost;
    static {
    	Map<String, Integer> aMap = new HashMap<String, Integer>();
    	aMap.put("wood", 15);
    	aMap.put("stone", 10);
    	aMap.put("marble", 6);
    	aMap.put("meteor", 1);
    	aMap.put("grapes", 20);
    	buildCost = Collections.unmodifiableMap(aMap);
    }
    
    public TempleStructure(float x, World world) {
        super(x, world);

        setSprite(new Sprite(img));
        name = "temple";
        grapeTime = 0;
    }
    
    public void update(float dt){
    	super.update(dt);
    	grapeTime = Math.max(grapeTime - dt, 0);
    	if (grapeTime <= 0){
    		if (World.THEWORLD.scampManager.scampResources.getScampResourceCount(ScampResourceType.GRAPES)> 0){
    			World.THEWORLD.player.addBelief(10);
    			World.THEWORLD.scampManager.scampResources.removeScampResource(ScampResourceType.GRAPES, 1);
    			grapeTime = 10f;
    		}
    	}
    }
}
