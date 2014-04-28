package lando.systems.ld29.structures;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lando.systems.ld29.Global;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;

public class SpaceshipStructure extends Structure {
    private static TextureRegion img = Assets.structures.get("spaceship");

    public float y;
    public float yVel =0;
    
    public static final Map<String, Integer> buildCost;
    static {
    	Map<String, Integer> aMap = new HashMap<String, Integer>();
    	aMap.put("steel", 30);
    	aMap.put("fuel", 3);
    	aMap.put("circuits", 30);
    	
    	buildCost = Collections.unmodifiableMap(aMap);
    }
    
    public SpaceshipStructure(float x, World world) {
        super(x, world);

        setSprite(new Sprite(img));
        name = "spaceship";
        y = Global.GROUND_LEVEL;
    }
    
    public void liftOff(float dt) {
    	y += yVel*dt;
    	yVel += 10 * dt;
    	getSprite().setPosition(x*64, y);
    	World.THEWORLD.particleSystem.fuckingCrazy(x*64 + 32, y, 40, Color.YELLOW);
    }
}
