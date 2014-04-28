package lando.systems.ld29.resources;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.ld29.Global;
import lando.systems.ld29.World;
import lando.systems.ld29.blocks.Block;

/**
 * Created by jhoopes on 4/26/14.
 */
public class ResourceManager {

    World parentWorld; // so we can access the world
    int width;
    int height;

    Resource[] resources;
    Resource pushedOutResource;
    float pushedTimer;
    float pushedDelay = .5f;


    public ResourceManager(World world){
        parentWorld = world;
        resources = new Resource[parentWorld.gameWidth];
        width = world.gameWidth;
        height = world.gameHeight;
        // Fill the whole world with barren-ness
        for (int i = 0; i < parentWorld.gameWidth; i++) {
            resources[i] = new Barren(i);
        }
    }

    public int takeResource(int pos, int resourcesAsked ){
        if (resources[pos] == null) return 0;

        int rCount = resources[pos].getResourceCount();
        if(resourcesAsked > rCount){
            resources[pos] = null;
            parentWorld.particleSystem.addFirework(pos * Block.BLOCK_WIDTH + Block.BLOCK_WIDTH / 2, Global.GROUND_LEVEL);
            return rCount;
        }else{
            int rLeftOver = rCount - resourcesAsked;
            if(rLeftOver == 0){
                resources[pos] = null;
                parentWorld.particleSystem.addFirework(pos * Block.BLOCK_WIDTH + Block.BLOCK_WIDTH / 2, Global.GROUND_LEVEL);
            }else{
                resources[pos].setResourceCount(rLeftOver);
            }
            return resourcesAsked;
        }
    }

    public void update(float dt){
        for (Resource resource : resources){
            if(resource == null){
                continue;
            }
            resource.update(dt);
        }
    }

    public void render(SpriteBatch batch){
        for (Resource resource : resources){
            if(resource == null){
                continue;
            }
            resource.render(batch);
        }
    }

    public Resource getResource(int x){
        return resources[x];
    }

    public Resource getResource(String blockName, int x) {
        Resource resource = null;
        for(int i = 0; i < resources.length; ++i) {
        	int pos = i + x;
            if (pos >= resources.length || resources[pos] == null) {
                
            } else if (blockName.equals(resources[pos].getName())) {
                resource = resources[pos];
                return resource;
                
            }
            
        	 pos = x - i;
            if (pos < 0 || resources[pos] == null) {
                continue;
            } else if (blockName.equals(resources[pos].getName())) {
                resource = resources[pos];
                return resource;
                
            }
        }
        return resource;
    }
    
    public int CountofType(String type){
    	int count = 0;
        for(int i = 0; i < resources.length; ++i) {
            if (resources[i] == null) {
                continue;
            } else if (type.equals(resources[i].getName())) {
                count++;
            }
        }
        return count;
    }
    
    public String getResourceFromProduct(String item){
    	switch (item){
    	case "food" : return "field";
    	case "wood" : return "forrest";
    	case "stone" : return "quarry";
    	case "gold" :return "goldmine";
    	case "marble": return "marblequarry";
    	case "iron" : return "mountain";
    	case "meteor" : return "meteorcrash";
    	case "grapes" : return "vinyard";
    	default: return "";
    	}
    }

//    public Resource getNewResource(String blockName, int x, int y){
//        switch(blockName){
//            case "wheat":   return new Field(x, y);
//            case "stone":  return new Quarry(x, y);
//            case "iron":   return new Mountain(x, y);
//            case "acorn":  return new Forrest(x, y);
//            case "grapes": return new Vinyard(x, y);
//            default:       return null;
//        }
//    }

    public boolean createResource(Block block, int x){
        // set pixel x and y
        int xpx = x * Block.BLOCK_WIDTH;
        int ypx = (int) Global.GROUND_LEVEL;

        resources[x] = block.MakeResource();
        parentWorld.particleSystem.createFountain(xpx + (Block.BLOCK_WIDTH / 2), ypx, block.fountainColor);

        return true;
    }
}
