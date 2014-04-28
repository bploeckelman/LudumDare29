package lando.systems.ld29.structures;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;

public class StructureManager {

    private World world;
    public Structure[] structures;

    public StructureManager(World world){
        this.world = world;
        structures = new Structure[World.gameWidth];

        // Add one for testing
        // TODO: Remove this...
        int i = World.gameWidth/2;
        createStructure(i,   new HouseStructure(i, world));
        createStructure(++i, new WarehouseStructure(i, world));
        createStructure(++i, new TempleStructure(i, world));
        createStructure(++i, new FactoryStructure(i, world));
        createStructure(++i, new SpaceshipStructure(i, world));
    }
    
    public int countStructures(String name){
    	int count = 0;
        for(Structure structure : structures){
            if(structure != null && structure.name == name) {
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
}
