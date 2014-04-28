package lando.systems.ld29.scamps;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import lando.systems.ld29.Global;
import lando.systems.ld29.World;
import lando.systems.ld29.blocks.Block;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.resources.Resource;
import lando.systems.ld29.structures.HouseStructure;
import lando.systems.ld29.structures.Structure;

/**
 * Author: Ian McNamara <ian.mcnamara@doit.wisc.edu>
 * Date: 4/26/14 @ 3:58 PM
 */
public class Scamp {

    public static final int SCAMP_SIZE = 32;
    public static final float SCAMP_SPEED = 1.0f;
    public static final float GATHER_RATE = 2f; // in seconds
    public static final float EAT_TIME = 3f; // in seconds
    public static final float BUILD_RATE = 1f; // in seconds
    public static final float BUILD_PERCENT = 0.1f;

    public enum ScampState {
        IDLE,
        STROLLING,
        FOOD,
        WOOD,
        STONE,
        IRON,
        MARBLE,
        GOLD,
        GRAPES,
        FUEL,
        CIRCUITS,
        SPACEROCK,
        STEEL,
        BUILDHOUSE,
        BUILDWAREHOUSE,
        BUILDTEMPLE,
        BUILDFACTORY,
        BUILDSPACESHIP,
        PRAY,
        SLEEP,
        GETONSHIP,
        EATING
    }
    
    static Map<String, ScampState> scampStates = new HashMap<String, ScampState>()
    {{
        put("wood", ScampState.WOOD);
        put("stone", ScampState.STONE);
        put("iron", ScampState.IRON);
        put("marble", ScampState.MARBLE);
        put("gold", ScampState.GOLD);
    }};
    
    static ScampState[] resourceGatherState = {
        ScampState.WOOD,
        ScampState.STONE,
        ScampState.IRON,
        ScampState.MARBLE,
        ScampState.GOLD
    };

    int skinID;
    public float position;
    float targetPosition;
    float gatherAccum;
    float eatAccum;
    float buildAccum;

    boolean walkRight;
    boolean gatherReady;
    boolean inHouse;

    String name;
    Resource workingResource;
    public Structure buildingStructure;
    TextureRegion texture;
    ScampState currentState = ScampState.IDLE;
    boolean isGatheringResources;    
    Color thoughtColor = new Color(1, 1, 1, 0);
    float displayLastState;
    
    float hungerAmount = 0;


    public Scamp(float startingPosition) {
        this.name = Assets.randomName();
        this.position = startingPosition;
        this.targetPosition = Assets.random.nextInt(World.gameWidth);
        this.walkRight = isWalkingRight();

        this.skinID = Assets.random.nextInt(Assets.num_scamps);
        this.texture = Assets.scamps.get(skinID);

        this.workingResource = null;
        this.gatherAccum = 0f;
        this.gatherReady = false;

        this.eatAccum = 0f;
        this.inHouse = false;

        this.buildingStructure = null;
        this.buildAccum = 0f;
    }


    public void update(float dt) {
        if (displayLastState > 0) {
            displayLastState -= dt;
        }

        hungerAmount += dt / 60; // 1 hunger a minute

        // Update based on current state
        // ---------------------------------------------------------------------
        switch(currentState) {

            case SLEEP:      updateSleeping(dt);      break;
            case EATING:     updateEating(dt);        break;
            case BUILDHOUSE: updateBuildingHouse(dt); break;

//            case BUILD_XXXX:
//                move to building target: buildingStructure
//                add a dt to buildingStructure
//                when rendering it, if it is in process of being built, grow the sprite vertically based on build percentage
//                if structure finished, go back to idle
        }

        // Have we reached our target yet?
        if( targetPosition == position ) {
            // If just strolling, go idle
            if(currentState == ScampState.STROLLING) {
               currentState = ScampState.IDLE;
            } else {
                // TODO : MAY BE GATHERING OR BUILDING OR REFINING, so adjust accordingly
               // Update gathering timer/state
                gatherAccum += dt;
                if (gatherAccum > GATHER_RATE) {
                    gatherAccum %= GATHER_RATE;
                    gatherReady = true;
                }
            }
        } else { // we are walking not working yet

        	gatherReady = false;
        	walkRight = isWalkingRight();

            // Move you sluggard!
            float dist= SCAMP_SPEED * dt;
            if (dist > Math.abs(targetPosition - position)){
                position = targetPosition;
            } else {
                position += (walkRight ? SCAMP_SPEED : -SCAMP_SPEED) * dt;
            }
        } 
    }

    private boolean updateSleeping(float dt) {
        if (World.THEWORLD.dayCycle.isDay()) {
            currentState = ScampState.IDLE;
            inHouse = false;
            return true;
        } else {
            if (!inHouse) {
                // If night, find an unoccupied house and enter it
                for (Structure structure : World.THEWORLD.structureManager.structures) {
                    if (structure instanceof HouseStructure && structure.getCapacity() > 0) {
                        targetPosition = structure.x;
                        structure.enter(this);
                        inHouse = true;
                    }
                }
            }
        }
        return false;
    }

    private void updateEating(float dt) {
        if (World.THEWORLD.scampManager.scampResources.getScampResourceCount(ScampResources.ScampResourceType.FOOD) > 0) {
            targetPosition = position;

            // Eat the food if we haven't yet
            if (eatAccum == 0) {
                World.THEWORLD.scampManager.scampResources.removeScampResource(ScampResources.ScampResourceType.FOOD, 1);
            }

            // Wait to finish eating before resetting state
            eatAccum += dt;
            if (eatAccum > EAT_TIME) {
                eatAccum = 0;
                hungerAmount = 0;
                currentState = ScampState.IDLE;
            }
        }
    }

    private void updateBuildingHouse(float dt) {
        if (buildingStructure == null) return;

        targetPosition = buildingStructure.x;
        // If scamp is at building site...
        if (position == targetPosition) {
            // Update build timer, and build if its time
            buildAccum += dt;
            if (buildAccum > BUILD_RATE) {
                buildAccum = 0;
                if (buildingStructure.build(BUILD_PERCENT)) {
                    currentState = ScampState.IDLE;
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture.getTexture(),
                position * Block.BLOCK_WIDTH, Global.GROUND_LEVEL,    // screen position x,y
                SCAMP_SIZE, SCAMP_SIZE,                               // pixel width/height
                texture.getRegionX(), texture.getRegionY(),           // texel x,y
                texture.getRegionWidth(), texture.getRegionHeight(),  // texel w,h
                !walkRight, false);                                   // flipx, flipy
        
        if (isGatheringResources || displayLastState > 0) {
        	thoughtColor.a = (isGatheringResources) ? 1 : (displayLastState / 4f);
        	Assets.thoughtBubble.setColor(thoughtColor);
        	Assets.thoughtBubble.draw(batch, position * Block.BLOCK_WIDTH , Global.GROUND_LEVEL + SCAMP_SIZE, SCAMP_SIZE, 30);
        	
        	if (isGatheringResources) {
        		TextureRegion icon = getResourceIcon();
        		if (icon != null) {   
        			Rectangle resourceBounds = getResourceBounds();
        			batch.draw(icon, resourceBounds.x, resourceBounds.y, resourceBounds.width, resourceBounds.height);
        		}
        	}
        }
    }
    
    public TextureRegion getResourceIcon() {
    	return Assets.icons.get(currentState.toString());
    }
    
    public Rectangle getResourceBounds() {
    	return new Rectangle(position * Block.BLOCK_WIDTH  + 4, Global.GROUND_LEVEL + SCAMP_SIZE + 10, SCAMP_SIZE - 8, 15);
    }

    public boolean isIdle() { return currentState == ScampState.IDLE; }

    public boolean isWalkingRight() { return (targetPosition - position >= 0); }

    public void didGather() { gatherReady = false; }
    public boolean isGatherReady() { return gatherReady; }

    public void setState(ScampState state) { 

        if (currentState == state) return;

        boolean wasGatheringResources = isGatheringResources;
        isGatheringResources = isResourceGather(state);

        currentState = state;

        displayLastState = (wasGatheringResources) ? 2f : 0;
    }
    
    private boolean isResourceGather(ScampState state) {
        boolean isResourceGather = false;
        for (int i = 0; i < resourceGatherState.length; i++) {
            if (resourceGatherState[i] == state) {
                isResourceGather = true;
                break;
            }
        }
        return isResourceGather;
    }
    
    public void setState(String text) {
        ScampState state = scampStates.get(text);
        setState(state != null ? state : ScampState.IDLE);
    }
    public void setTarget(float position) { this.targetPosition = position; }
    public void setTarget(Block block) { this.targetPosition = block.getX(); }

    public int getBlockPosition() { return (int) Math.floor(position); }
    public int getBlockTargetPosition() { return (int) Math.floor(targetPosition); }

    public float getPixelPosition() { return position * Block.BLOCK_WIDTH; }
    public float getPixelTargetPosition() { return targetPosition * Block.BLOCK_WIDTH; }

    public int getSkinID() { return skinID; }
    public TextureRegion getSkin() { return texture; }

    public void setSkin(int skinID) {
        TextureRegion temp = null;
        try {
            temp = Assets.scamps.get(skinID);
        } catch(IndexOutOfBoundsException e) {
            System.err.println("Error: tried to set scamp skin to invalid texture: " + skinID);
            return;
        }

        if(temp == null) {
            System.err.println("Error: tried to set scamp skin to missing texture: " + skinID);
            return;
        }

        this.skinID = skinID;
        this.texture = temp;
    }

    public Resource getWorkingResource() { return workingResource; }
    public void setWorkingResource(Resource resource) { workingResource = resource; }

    public String toString() {
        return name;
    }

    public String getCurrentStateName() {
        switch(currentState) {
            case STROLLING:          return "Strolling";
            case IDLE:               return "Idling";
            case FOOD:               return "Harvesting";
            case WOOD:               return "Chopping wood";
            case STONE:              return "Cutting Stone";
            case IRON:               return "Mining Iron";
            case MARBLE:             return "Cutting Marble";
            case GOLD:               return "Mining Gold";
            case GRAPES:             return "Picking Grapes";
            case FUEL:               return "Making Fuel";
            case CIRCUITS:           return "Making Circuits";
            case SPACEROCK:          return "Collecting Meteor";
            case STEEL:              return "Forging Steel";
            case BUILDHOUSE:         return "Building a House";
            case BUILDWAREHOUSE:     return "Building a Warehouse";
            case BUILDTEMPLE:        return "Building a Temple";
            case BUILDFACTORY:       return "Building a Factory";
            case BUILDSPACESHIP:     return "Building a Spaceship";
            case PRAY:               return "Praying";
            case SLEEP:              return "Sleeping";
            case GETONSHIP:          return "Leaving the Planet";
            case EATING:             return "Eating";
            default:                 return "???";
        }
    }

}
