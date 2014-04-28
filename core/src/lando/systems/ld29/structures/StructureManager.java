package lando.systems.ld29.structures;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.ld29.World;

public class StructureManager {

    private World world;
    public Structure[] structures;

    public StructureManager(World world){
        this.world = world;
        structures = new Structure[World.gameWidth];

        // Add one for testing
        // TODO: Remove this...
        int i = World.gameWidth/2;
        structures[i] = new HouseStructure(i, world);
        structures[++i] = new WarehouseStructure(i, world);
        structures[++i] = new TempleStructure(i, world);
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

    public void render(SpriteBatch batch){
        for(Structure structure : structures){
            if(structure != null) {
                structure.render(batch);
            }
        }
    }
}
