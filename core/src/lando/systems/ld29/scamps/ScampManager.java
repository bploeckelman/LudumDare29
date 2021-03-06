package lando.systems.ld29.scamps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import lando.systems.ld29.Global;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.resources.Resource;
import lando.systems.ld29.scamps.ScampResources.ScampResourceType;
import lando.systems.ld29.scamps.Scamp.*;
import lando.systems.ld29.structures.*;
import lando.systems.ld29.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Ian McNamara <ian.mcnamara@doit.wisc.edu>
 * Date: 4/26/14 @ 3:58 PM
 */
public class ScampManager {

//    private static enum ScampPriority {
//        FOOD,
//        WOOD,
//        STONE,
//        IRON,
//        MARBLE,
//        GOLD,
//        GRAPES,
//        FUEL,
//        CIRCUITS,
//        SPACEROCK,
//        STEEL,
//        BUILDHOUSE,
//        BUILDWAREHOUSE,
//        BUILDTEMPLE,
//        BUILDFACTORY,
//        BUILDSPACESHIP,
//        PRAY,
//        SLEEP,
//        GETONSHIP
//        
//    }

    private final static int INITIAL_SCAMP_COUNT = 3;
//    private final static float DEFAULT_SCAMP_PRIORITY_SCORE = 10;
//    private final static float PRIORITY_RECOMPUTE_TIME = 10; // in seconds

    World world;
    // use add/removeScamp(..) so scampResources are acurate
    private Array<Scamp> scamps;
    public ScampResources scampResources;

    float accum = 0;

//    private Map<ScampPriority, Float> scampPriorityScores = new HashMap<ScampPriority, Float>(
//            ScampPriority.values().length
//    );

    public ScampManager(World world) {
        this.world = world;
        this.scampResources = new ScampResources();
        this.placeScamps();
    }

    /**
     * Places the starting number of scamps in the world.
     * Clears any already existing Scamps, so this should only really be called on startup.
     */
    private void placeScamps() {
        scamps = new Array<>();
        for (int i = 0; i < INITIAL_SCAMP_COUNT; i++) {
            addScamps(new Scamp( Assets.random.nextInt(world.gameWidth) ));
        }
    }
    
    public void killScamp(Scamp scamp){
    	scamp.kill();
    	removeScamps(scamp);
    }

    public Scamp getScampFromPos(float x, float y) {
        Scamp scamp = null;
        if (Utils.isBetween(y, Global.GROUND_LEVEL, Global.GROUND_LEVEL + Scamp.SCAMP_SIZE)) {
            for(Scamp s : scamps) {
                if (Utils.isBetween(x, s.getPixelPosition(), s.getPixelPosition() + Scamp.SCAMP_SIZE)) {
                    scamp = s;
                    break;
                }
            }
        }
        return scamp;
    }
    
    public boolean allInShip(){
    	boolean ready = true;
    	for (Scamp scamp: scamps){
    		if (!scamp.onShip){
    			ready = false;
    		}
    	}
    	return ready;
    }

    public void update(float dt) {
//        accum += dt;
//        if (accum > PRIORITY_RECOMPUTE_TIME) {
//            accum -= PRIORITY_RECOMPUTE_TIME;
//            System.out.println("update() | it's time");
//            determinePriorities();
//        }
    	

        for(Scamp scamp : scamps) {
        	if (scamp.isIdle()) GiveScampJob(scamp);
            scamp.update(dt);
        }
    }

    public void renderScamps(SpriteBatch batch) {
        for(Scamp scamp : scamps) { scamp.render(batch); }
    }

    private void GiveScampJob(Scamp scamp){
    	
    	if (world.structureManager.countStructures("spaceship") > 0){
    		Structure ship = world.structureManager.findStructure("spaceship");
    		if (ship.buildPercent >= 1)
    			scamp.currentState = ScampState.GETONSHIP;
    		// TODO target spaceship?
    		return;
    	}
    	
    	if (scamp.hungerAmount > 5 && scampResources.getScampResourceCount(ScampResourceType.FOOD) > 0){
    		scamp.currentState = ScampState.EATING;
    		return;
    	}
    	
   	
    	if (scamp.hungerAmount > 3 && 
    		scampResources.getScampResourceCount(ScampResourceType.FOOD) < scamps.size &&
    		world.rManager.CountofType("field") > 0){
    		
    			scamp.currentState = ScampState.FOOD;
    			gatherResource(scamp, "field");
    			return;
    	}
    	

    	
    	if (!world.dayCycle.isDay()){
    		scamp.currentState = ScampState.SLEEP;
    		//Get a House that has space available
    		return;
    	}
    	
    	
    	// Build SpaceShip
    	if (world.structureManager.countStructures("factory") > 1) {
	    	if (tryBuilding(scamp, "spaceship")){
	    		return;
	    	}
    	}
    	
    	
    	// Build Temple
    	if (world.structureManager.countStructures("temple") == 0) {
    	    	if (tryBuilding(scamp, "temple")){
    	    		return;
    	    	}
        	}
    	
    	// Build Factory
    	if ((1 + world.structureManager.countStructures("factory")) * 2 == world.structureManager.countStructures("warehouse") &&
    			world.structureManager.countStructures("factory") < 2 ){
    	    	if (tryBuilding(scamp, "factory")){
    	    		return;
    	    	}
        	}
    	
    	// Build Warehouse
    	if ((1 + world.structureManager.countStructures("warehouse")) * 2 == world.structureManager.countStructures("house") &&
    		(1 + world.structureManager.countStructures("factory")) * 2 > world.structureManager.countStructures("warehouse")	){
	    	if (tryBuilding(scamp, "warehouse")){
	    		return;
	    	}
    	}
    	
    	// Build House
    	if ((1 + world.structureManager.countStructures("warehouse")) * 2 > world.structureManager.countStructures("house") ){
	    	if (tryBuilding(scamp, "house")){
	    		return;
	    	}
    	}

    	// get food?
    	if (scampResources.getScampResourceCount(ScampResourceType.FOOD) < world.structureManager.getMaxAmount(ScampResourceType.FOOD) &&
    			world.rManager.CountofType("field") > 0) {
			scamp.setState(ScampState.FOOD);
			gatherResource(scamp, "field");
			return;
    	}
    	
    	// get grapes?
    	if (scampResources.getScampResourceCount(ScampResourceType.GRAPES) < world.structureManager.getMaxAmount(ScampResourceType.GRAPES) &&
    			world.rManager.CountofType("vinyard") > 0) {
			scamp.setState(ScampState.GRAPES);
			gatherResource(scamp, "vinyard");
			return;
    	}
    	
    	// get Wood?
    	if (scampResources.getScampResourceCount(ScampResourceType.WOOD) < world.structureManager.getMaxAmount(ScampResourceType.WOOD) &&
    			world.rManager.CountofType("forrest") > 0) {
    		scamp.setState(ScampState.WOOD);
			gatherResource(scamp, "forrest");
			return;
    	}
    	
    	// get Stone?
    	if (scampResources.getScampResourceCount(ScampResourceType.STONE) < world.structureManager.getMaxAmount(ScampResourceType.STONE) &&
    			world.rManager.CountofType("quarry") > 0) {
    		scamp.setState(ScampState.STONE);
			gatherResource(scamp, "quarry");
			return;
    	}
    	
    	// get Margble?
    	if (scampResources.getScampResourceCount(ScampResourceType.MARBLE) < world.structureManager.getMaxAmount(ScampResourceType.MARBLE) &&
    			world.rManager.CountofType("marblequarry") > 0) {
    		scamp.setState(ScampState.MARBLE);
			gatherResource(scamp, "marblequarry");
			return;
    	}
    	
    	// get Iron?
    	if (scampResources.getScampResourceCount(ScampResourceType.IRON) < world.structureManager.getMaxAmount(ScampResourceType.IRON) &&
    			world.rManager.CountofType("mountain") > 0) {
    		scamp.setState(ScampState.IRON);
			gatherResource(scamp, "mountain");
			return;
    	}
    	
    	// get Gold?
    	if (scampResources.getScampResourceCount(ScampResourceType.GOLD) < world.structureManager.getMaxAmount(ScampResourceType.GOLD) &&
    			world.rManager.CountofType("goldmine") > 0) {
    		scamp.setState(ScampState.GOLD);
			gatherResource(scamp, "goldmine");
			return;
    	}
    	
    	//Nothing else Walk Around
    	scamp.currentState = ScampState.STROLLING;
    	int pos = (int)scamp.position - 5 + Assets.random.nextInt(10);
    	while (pos < 0 || pos >= World.gameWidth) pos = (int)scamp.position - 5 + Assets.random.nextInt(10);
    	scamp.setTarget(pos);
 	
    }
    
    private int workersDoingJob(ScampState state){
    	int count = 0;
    	for (int i =0;i < scamps.size; i++){
    		if (scamps.get(i).currentState == state){
    			count ++;
    		}
    	}
    	return count;
    }
    
    private boolean tryBuilding(Scamp scamp, String name){
    	Map<String, Integer> costs = new HashMap<String, Integer>();
    	switch(name){
    	case "house":
    		costs = HouseStructure.buildCost;
    		break;
    	case "warehouse":
    		costs = WarehouseStructure.buildCost;
    		break;
    	case "temple":
    		costs = TempleStructure.buildCost;
    		break;
    	case "factory":
    		costs = FactoryStructure.buildCost;
    		break;
    	case "spaceship":
    		costs = SpaceshipStructure.buildCost;
    		break;
    	}
    	
    	boolean missingresource = false;
    	if (name == "spaceship"){
    		for(String key : costs.keySet()){
    			if (scampResources.getScampResourceCount(key.toUpperCase()) < costs.get(key)) {
    				missingresource = true;
    				String raw = FactoryStructure.getRawName(key);
    				// Have raw? refine it
    				if (scampResources.getScampResourceCount(raw.toUpperCase()) > 0 && 
    						World.THEWORLD.structureManager.emptyFactory()){
    					scamp.setState(key);
    					System.out.println("Refining " + key);
    					return true;
    				}
    				// Find raw
    				if (world.rManager.CountofType(world.rManager.getResourceFromProduct(raw)) > 0){
    					gatherResource(scamp, world.rManager.getResourceFromProduct(raw));
    					scamp.setState(raw);
    					System.out.println("Looking for " + raw);
    					return true;
    				}
    			}
    		}
    	} else {
    		for(String key : costs.keySet()){
    			if (scampResources.getScampResourceCount(key.toUpperCase()) < costs.get(key)) {
    				missingresource = true;
    				if (world.rManager.CountofType(world.rManager.getResourceFromProduct(key)) > 0 && 
    						costs.get(key) < world.structureManager.getMaxAmount(scampResources.getType(key.toUpperCase()))){
    					if (workersDoingJob(Scamp.scampStates.get(key)) > 1) continue;
    					gatherResource(scamp, world.rManager.getResourceFromProduct(key));
    					scamp.setState(key);
    					System.out.println("Looking for " + key);
    					return true;
    				}
    			}
    		}
    	}
    	
    	// All Costs are satisfied
    	if (!missingresource){
    		Structure struct;
    		switch(name){
    		case "house": struct = new HouseStructure(world.structureManager.getRandomAvilSpot(), world);
    			scamp.currentState = ScampState.BUILDHOUSE;
    		break;
    		case "warehouse": struct = new WarehouseStructure(world.structureManager.getRandomAvilSpot(), world);
				scamp.currentState = ScampState.BUILDWAREHOUSE;
			break;
    		case "temple": struct = new TempleStructure(world.structureManager.getRandomAvilSpot(), world);
				scamp.currentState = ScampState.BUILDTEMPLE;
			break;
    		case "factory": struct = new FactoryStructure(world.structureManager.getRandomAvilSpot(), world);
				scamp.currentState = ScampState.BUILDFACTORY;
			break;
    		case "spaceship": struct = new SpaceshipStructure(world.structureManager.getRandomAvilSpot(), world);
				scamp.currentState = ScampState.BUILDSPACESHIP;
			break;
    		default : struct = new HouseStructure(world.structureManager.getRandomAvilSpot(), world);
    			
    		}
    		
    		scamp.buildingStructure = struct;
    		world.structureManager.createStructure((int)struct.x, struct);
    		
        	for(String key : costs.keySet()){
        		scampResources.removeScampResource(key.toUpperCase(), costs.get(key));
        	}
    		return true;
    	}
    	
    	
    	return false;
    }
    
    
    public int getCurrentPopulation(){
    	return scamps.size;
    }
    
    public boolean spaceForMoreScamps(){
    	return !(scamps.size == world.structureManager.getMaxPopulation());
    	
    }
    
    private void gatherResource(Scamp scamp, String resourceName){
		Resource resource = world.rManager.getResource(resourceName, (int)scamp.position);
		if (resource == null){
			int i =0;
			i++;
		}
		scamp.setTarget(resource.getX());
		scamp.setWorkingResource(resource);
    }

	public void addScamps(Scamp scamp) {
		scamps.add(scamp);
		scampResources.addScampResource(ScampResourceType.PEOPLE);
	}
	
	public void removeScamps(Scamp scamp) {
		scamps.removeValue(scamp,  true);
		scampResources.removeScampResource(ScampResourceType.PEOPLE, 1);
		world.displayResourceGather(scamp,  -1);
	}

//    public void determinePriorities() {
//        System.out.println("determinePriorities | called");
//
//        updatePriorities();
//        updateScampTask(getIdleScamp(), getTopScampPriority());
//    }

//    private void updatePriorities() {
//        for (ScampPriority priority: ScampPriority.values()) {
//            switch (priority) {
//                case FOOD:
//                    int foodCount = scampResources.getScampResourceCount(ScampResources.ScampResourceType.FOOD);
//                    float foodPriority = 50;
//
//                    double temp = (foodCount / (scamps.size * 2));
//                    if (temp < 1) {
//                        temp = (1 - temp) * 50;
//                    } else {
//                        temp = Math.pow(temp, 2);
//                    }
//
//                    foodPriority += (int)temp;
//                    scampPriorityScores.put(priority, foodPriority);
//                    System.out.println("determinePriorities | foodPriority='" + foodPriority + "'");
//
//                    break;
//
//                case SHELTER:
//                    float shelterPriority = 40;
//                    scampPriorityScores.put(priority, shelterPriority);
//                    System.out.println("determinePriorities | shelterPriority='" + shelterPriority + "'");
//                    break;
//
//                case WINE:
//                    float winePriority = 30;
//                    scampPriorityScores.put(priority, winePriority);
//                    System.out.println("determinePriorities | winePriority='" + winePriority + "'");
//                    break;
//
//                case TEMPLE:
//                    float templePriority = 5;
//                    scampPriorityScores.put(priority, templePriority);
//                    System.out.println("determinePriorities | templePriority='" + templePriority + "'");
//                default:
//            }
//        }
//    }

//    private void updateScampTask(Scamp idleScamp, ScampPriority topPriority) {
//        if (idleScamp == null) {
//            System.out.println("determinePriorities | no idle scamps available for current top priority '" + topPriority.toString() + "'");
//        } else {
//            switch (topPriority) {
//                case FOOD:
//                    // find a field to harvest from
//                    Resource resource = world.rManager.getResource("field");
//                    if (resource != null) {
//                        idleScamp.setTarget(resource.getX());
//                        idleScamp.setState(ScampState.HARVESTING);
//                        idleScamp.setWorkingResource(resource);
//                        System.out.println("determinePriorities | Scamp " + idleScamp.toString() + " now harvesting field at x=" + idleScamp.getBlockTargetPosition());
//                    } else {
//                        // todo: handle case where there are no fields
//                        System.out.println("determinePriorities | top priority is food, but can't find field resource");
//                    }
//                    break;
//                case SHELTER:
//                    break;
//                case WINE:
//                    break;
//                case TEMPLE:
//                    break;
//            }
//        }
//    }

//    private Scamp getIdleScamp() {
//        for(Scamp scamp : scamps) {
//            if (scamp.isIdle()) {
//                return scamp;
//            }
//        }
//        return null;
//    }

//    private ScampPriority getTopScampPriority() {
//        float maxPriority = -999999;
//        ScampPriority topPriority = null;
//
//        for(ScampPriority priority : scampPriorityScores.keySet()) {
//            float thisPriority = scampPriorityScores.get(priority);
//            if (thisPriority > maxPriority) {
//                maxPriority = thisPriority;
//                topPriority = priority;
//            }
//        }
//        return topPriority;
//    }

}
