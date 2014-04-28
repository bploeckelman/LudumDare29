package lando.systems.ld29.structures;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lando.systems.ld29.IToolTip;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.scamps.ScampResources;
import lando.systems.ld29.scamps.ScampResources.ScampResourceType;

public class StructureManager {

    private World world;
    public Structure[] structures;

    public StructureManager(World world){
        this.world = world;
        structures = new Structure[World.gameWidth];

        // Add one for testing
        // TODO: Remove this...
        int i = World.gameWidth/2;

        HouseStructure initialHouse = new HouseStructure(i, world);
        initialHouse.buildPercent = 1f;
        createStructure(i, initialHouse);
//        createStructure(++i, new WarehouseStructure(i, world));
//        createStructure(++i, new TempleStructure(i, world));
//        createStructure(++i, new FactoryStructure(i, world));
        
//        SpaceshipStructure space =new SpaceshipStructure(++i, world);
//        space.buildPercent = 1f;
//        createStructure(i, space);

    }
    
    public Structure findStructure(String name){
        for(Structure structure : structures){
            if(structure != null && structure.getName() == name) {
                return structure;
            }
        }
        return null;
    }
    
    public int countStructures(String name){
    	int count = 0;
        for(Structure structure : structures){
            if(structure != null && structure.getName() == name) {
                count++;
            }
        }
    	return count;
    }

    public void createStructure(int column, Structure structure){
        structures[column] = structure;
    }

    public void update(float dt){
        for(Structure structure : structures){
            if(structure != null) {
                structure.update(dt);
            }
        }
    }

    public int getMaxAmount(ScampResourceType type){
    	int max = 0;
    	switch (type){
    	case FOOD:
    	case WOOD:
    	case STONE:
    	case GRAPES:
    		max = 10;
    		break;
    	}
    	
    	max += countStructures("warehouse") * 10;
    	return max;
    }
    
    public int getMaxPopulation(){
    	return countStructures("house") * HouseStructure.HouseLimit;
    }
    
    public int getRandomAvilSpot(){
    	int point = Assets.random.nextInt(World.gameWidth);
    	while (structures[point] != null){
    		point = Assets.random.nextInt(World.gameWidth);
    	}
    	return point;
    }
    
    public void render(SpriteBatch batch){
        for(Structure structure : structures){
            if(structure != null) {
                structure.render(batch);
            }
        }
    }

	public IToolTip getStructureFromPos(float x, float y) {
		IToolTip structureToolTip = null;
		for(Structure structure : structures) {
			if(structure != null && structure.getSprite().getBoundingRectangle().contains(x, y)) {
            	structureToolTip = structure;
            	break;
            }
        }
		return structureToolTip;
	}
}
