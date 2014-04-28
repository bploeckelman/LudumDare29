package lando.systems.ld29.scamps;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import lando.systems.ld29.Global;
import lando.systems.ld29.IResourceGenerator;
import lando.systems.ld29.World;
import lando.systems.ld29.blocks.Block;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.resources.Resource;
import lando.systems.ld29.structures.HouseStructure;
import lando.systems.ld29.structures.Structure;
import lando.systems.ld29.scamps.ScampResources.*;

/**
 * Author: Ian McNamara <ian.mcnamara@doit.wisc.edu>
 * Date: 4/26/14 @ 3:58 PM
 */
public class Scamp implements IResourceGenerator {

    public static final int SCAMP_SIZE = 32;
    public static final float SCAMP_SPEED = 1.0f;
    public static final float GATHER_RATE = 2f; // in seconds
    public static final float EAT_TIME = 3f; // in seconds
    public static final float BUILD_RATE = 1f; // in seconds
    public static final float BUILD_PERCENT = 0.1f;
    // TODO : different refine rates for different items?
    public static final float REFINE_RATE = 3f;

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
        METEOR,
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
        put("food", ScampState.FOOD);
        put("wood", ScampState.WOOD);
        put("stone", ScampState.STONE);
        put("iron", ScampState.IRON);
        put("marble", ScampState.MARBLE);
        put("gold", ScampState.GOLD);
        put("grapes", ScampState.GRAPES);
        put("meteor", ScampState.METEOR);
        
        put("fuel", ScampState.FUEL);
        put("circuits", ScampState.CIRCUITS);
        put("steel", ScampState.STEEL);
    }};
    
    static ScampState[] resourceGatherState = {
        ScampState.FOOD,
        ScampState.WOOD,
        ScampState.STONE,
        ScampState.IRON,
        ScampState.MARBLE,
        ScampState.GOLD,
        ScampState.GRAPES,
        ScampState.METEOR
        // TODO : METEOR??
    };

    int skinID;
    public float position;
    float targetPosition;
    float gatherAccum;
    float eatAccum;
    float buildAccum;
    float refineAccum;

    boolean walkRight;
    boolean inHouse;
    public boolean actuallyinHouse;
    boolean isGathering;
    boolean isBuilding;
    boolean isRefining;
    boolean resourceDepleted;
    boolean onShip;

    boolean dead;

    String name;

    public Resource workingResource;
    public Structure buildingStructure;
    public Structure refiningStructure;

    TextureRegion texture;
    ScampState currentState = ScampState.IDLE;
    Color thoughtColor = new Color(1, 1, 1, 0);
    float displayLastState;
    
    float hungerAmount = 0;
    private float mySpeed;


    public Scamp(float startingPosition) {
        this.name = Assets.randomName();
        this.position = startingPosition;
        this.targetPosition = Assets.random.nextInt(World.gameWidth);
        this.walkRight = isWalkingRight();

        this.skinID = Assets.random.nextInt(Assets.num_scamps);
        this.texture = Assets.scamps.get(skinID);

        this.workingResource = null;
        this.gatherAccum = 0f;

        this.eatAccum = 0f;
        this.inHouse = false;

        this.buildingStructure = null;
        this.buildAccum = 0f;

        this.refineAccum = 0f;
        this.isRefining = false;

        this.mySpeed = SCAMP_SPEED + (Assets.random.nextFloat() * .5f);
        this.onShip= false;
    }


    public void update(float dt) {
    	if (onShip) return;
        if (displayLastState > 0) {
            displayLastState -= dt;
        }

        updateState(dt);
        updateMovement(dt);
    }

    private void updateHunger(float dt) {
        hungerAmount += dt / 60; // 1 hunger a minute
        if (hungerAmount > 10 && World.THEWORLD.scampManager.getCurrentPopulation() > 1){

            World.THEWORLD.scampManager.killScamp(this);

        }
    }

    private void updateState(float dt) {
        updateHunger(dt);

        switch(currentState) {
            // General actions
            case SLEEP:     updateSleeping(dt); break;
            case EATING:    updateEating(dt);   break;
            case STROLLING: updateStrolling(dt); break;

            // Gathering
            case FOOD:
            case WOOD:
            case STONE:
            case IRON:
            case GOLD:
            case GRAPES:
            case MARBLE:
            case METEOR:
                updateGathering(dt);
                break;

            // Building
            case BUILDHOUSE:
            case BUILDWAREHOUSE:
            case BUILDFACTORY:
            case BUILDTEMPLE:
            case BUILDSPACESHIP:
                updateBuilding(dt);
                break;

            // Refining
            case FUEL:
            case STEEL:
            case CIRCUITS:
                updateRefining(dt);
                break;

            // Win condition
            case GETONSHIP:
                updateWin(dt);
                break;
        }
    }

    private void updateWin(float dt){
    	Structure ship = World.THEWORLD.structureManager.findStructure("spaceship");
    	targetPosition = ship.x;
    	if( targetPosition == position ) onShip = true;
    }
    
    private void updateMovement(float dt) {
        // Have we reached our target yet?
        if( targetPosition != position ) {
            // We are walking not working yet
            walkRight = isWalkingRight();

            // Move you sluggard!
            float dist = mySpeed * dt;
            if (dist > Math.abs(targetPosition - position)){
                position = targetPosition;
            } else {
                position += (walkRight ? mySpeed : -mySpeed) * dt;
            }
        }
    }

    private boolean updateSleeping(float dt) {
        if (World.THEWORLD.dayCycle.isDay()) {
            currentState = ScampState.IDLE;
            inHouse = false;
            actuallyinHouse = false;
            return true;
        } else {
            if (!inHouse) {
                // If night, find an unoccupied house and enter it
                for (Structure structure : World.THEWORLD.structureManager.structures) {
                    if (structure != null && structure.name == "house" && structure.getCapacity() > 0) {
                        targetPosition = structure.x;
                        structure.enter(this);
                        inHouse = true;
                        break;
                    }
                }
            }
            if (targetPosition == position){
            	actuallyinHouse = true;
            } else {
            	actuallyinHouse = false;
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

    private void updateStrolling(float dt) {
    	if (!World.THEWORLD.dayCycle.isDay()) {
    		setState(ScampState.SLEEP);
    		return;
    	}
        if (position == targetPosition) {
            currentState = ScampState.IDLE;
        }
    }

    private void updateGathering(float dt) {
    	if (!World.THEWORLD.dayCycle.isDay()) {
    		setState(ScampState.SLEEP);
    		return;
    	}
        if (workingResource == null) return;
        if (!World.THEWORLD.rManager.containsResource(workingResource)) {
        	setState(ScampState.IDLE);
        	return;
        }
        targetPosition = workingResource.getX();
        // If scamp is at gathering site...
        if (position == targetPosition) {
            // Update gathering timer, and gather if its time
            gatherAccum += dt;
            if (gatherAccum > GATHER_RATE) {
                gatherAccum = 0;
                
                ScampResources resources = World.THEWORLD.scampManager.scampResources;
                ScampResourceType type = resources.getType(workingResource.resourceName().toUpperCase());

                // If there's anything to gather, gather one
                int numResourcesGathered = 0;
                
                if (resources.getScampResourceCount(type) < World.THEWORLD.structureManager.getMaxAmount(type)) {
                    numResourcesGathered = World.THEWORLD.rManager.takeResource((int) workingResource.getX(), 1);
                }

                // If we gathered any, display it and store the gathered resource
                if (numResourcesGathered > 0) {
                    World.THEWORLD.displayResourceGather(this, numResourcesGathered);
                    resources.addScampResources(resources.getType(workingResource.resourceName().toUpperCase()), numResourcesGathered);
                    System.out.println("update() | scamp " + name + " gathered " + numResourcesGathered + " resources of type '" + workingResource.resourceName() + "'");
                } else {
                    // Resource used up, go about your business
                    workingResource = null;
                    isGathering = false;
                    currentState = ScampState.IDLE;
                }
            }
        }
    }

    private void updateBuilding(float dt) {
        if (buildingStructure == null) return;

        isBuilding = true;
        targetPosition = buildingStructure.x;
        // If scamp is at building site...
        if (position == targetPosition) {
            // Update build timer, and build if its time
            buildAccum += dt;
            if (buildAccum > BUILD_RATE) {
                buildAccum = 0;
                if (buildingStructure.build(BUILD_PERCENT)) {
                    currentState = ScampState.IDLE;
                    isBuilding = false;
                }
            }
        }
    }

    private void updateRefining(float dt) {
        ScampResources resources = World.THEWORLD.scampManager.scampResources;
        ScampResourceType type, rawType;
        switch (currentState) {
            case FUEL:     type = ScampResourceType.FUEL;     rawType = ScampResourceType.METEOR; break;
            case STEEL:    type = ScampResourceType.STEEL;    rawType = ScampResourceType.IRON;   break;
            case CIRCUITS: type = ScampResourceType.CIRCUITS; rawType = ScampResourceType.GOLD;   break;
            // this shouldn't ever happen, its just to shut up the warning
            default:       type = ScampResourceType.METEOR;   rawType = ScampResourceType.METEOR; break;
        }

        if (!isRefining) {
            // Find and enter nearest open factory
            Structure nearestFactory = World.THEWORLD.structureManager.getNearestStructure("factory", (int) position);
            
            if (nearestFactory == null) {
            	currentState = ScampState.IDLE;
            	return;
            }
            refiningStructure = nearestFactory;
            refiningStructure.enter(this);

            // Remove raw resource if we haven't already
            if (resources.getScampResourceCount(rawType) == 0){
            	// OOPs can't work now
            	currentState = ScampState.IDLE;
            	return;
            }
            resources.removeScampResource(rawType, 1);
        }
        isRefining = true;

        targetPosition = refiningStructure.x;
        // If scamp is at refining site...
        if (position == targetPosition) {
            // Update refine timer, and refine if its time
            refineAccum += dt;
            if (refineAccum > REFINE_RATE) {
                refineAccum = 0;

                // Generate the new refined resource and display
                resources.addScampResources(type, 1);
                World.THEWORLD.displayResourceGather(this, 1);
                System.out.println("update() | scamp " + name + " refined 1 resource of type '" + type.toString() + "'");

                // Done refining
                refiningStructure.leave(this);
                refiningStructure = null;
                isRefining = false;
                currentState = ScampState.IDLE;
            }
        }
    }

    public void render(SpriteBatch batch) {
    	if (onShip) return;
    	if (actuallyinHouse) return;
        batch.draw(texture.getTexture(),
                position * Block.BLOCK_WIDTH, Global.GROUND_LEVEL,    // screen position x,y
                SCAMP_SIZE, SCAMP_SIZE,                               // pixel width/height
                texture.getRegionX(), texture.getRegionY(),           // texel x,y
                texture.getRegionWidth(), texture.getRegionHeight(),  // texel w,h
                !walkRight, false);                                   // flipx, flipy
        
        if (isGathering || isBuilding || displayLastState > 0) {
            thoughtColor.a = (isGathering || isBuilding) ? 1 : (displayLastState / 2f);
        	Assets.thoughtBubble.setColor(thoughtColor);
        	Assets.thoughtBubble.draw(batch, position * Block.BLOCK_WIDTH , Global.GROUND_LEVEL + SCAMP_SIZE, SCAMP_SIZE, 30);
        	
            if (isGathering || isBuilding ) {
        		TextureRegion icon = getResourceIcon();
        		if (icon != null) {   
        			Rectangle resourceBounds = getResourceBounds();
        			batch.draw(icon, resourceBounds.x, resourceBounds.y, resourceBounds.width, resourceBounds.height);
        		}
        	}
        }
    }

    public boolean isIdle() { return currentState == ScampState.IDLE; }
    public boolean isWalkingRight() { return (targetPosition - position >= 0); }

    public TextureRegion getResourceIcon() {
        if (dead) return Assets.icons.get("PEOPLE");
        if (isBuilding) return Assets.icons.get("BUILD");
        else return Assets.icons.get(currentState.toString());
    }
    
    public Rectangle getResourceBounds() {
    	return new Rectangle(position * Block.BLOCK_WIDTH  + 9, Global.GROUND_LEVEL + SCAMP_SIZE + 10, 15, 15);
    }

    public void setState(ScampState state) {
        if (currentState == state) return;

        boolean wasGathering = isGathering;
        boolean wasBuilding  = isBuilding;
        isGathering = isResourceGather(state);

        currentState = state;

        displayLastState = (wasGathering || wasBuilding) ? 2f : 0;
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
            case METEOR:          return "Collecting Meteor";
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


	public void kill() {
		dead = true;
	}

}
