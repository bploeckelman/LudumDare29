package lando.systems.ld29;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.ld29.blocks.Block;
import lando.systems.ld29.resources.*;

import java.util.HashMap;

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
        resources = new Resource[parentWorld.gameWidth * parentWorld.gameHeight];
        width = world.gameWidth;
        height = world.gameHeight;
    }

    public int takeResource(int pos, int resourcesAsked ){
        Resource selectedResource = resources[pos];

        int rCount = selectedResource.getResourceCount();
        if(resourcesAsked > rCount){
            resources[pos] = null;
            return rCount;
        }else{
            int rLeftOver = rCount - resourcesAsked;
            if(rLeftOver == 0){
                resources[pos] = null;
            }else{
                selectedResource.setResourceCount(rLeftOver);
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

    public Resource getResource(String blockName) {
        Resource resource = null;
        String resourceName = null;
        for(int i = 0; i < resources.length; ++i) {
            if (resources[i] == null) {
                continue;
            } else if (blockName.equals(resources[i].getName())) {
                resource = resources[i];
                break;
            }
        }
        return resource;
    }

    public Resource getNewResource(String blockName, int x, int y){

        switch(blockName){
            case "dirt":
                return new Field(x, y);

            case "stone":
                return new Quarry(x, y);

            case "iron":
                return new Mountain(x, y);

            case "acorn":
                return new Forrest(x, y);

            case "grapes":
                return new Vinyard(x, y);

            default:
                return null;
        }
    }

    public boolean createResource(Block block, int x){
        // set pixel x and y
        int xpx = x * Block.BLOCK_WIDTH;
        int ypx = (int) Global.GROUND_LEVEL;

        String blockName = block.blockType;
        resources[x] = this.getNewResource(blockName, xpx, ypx);
        parentWorld.particleSystem.createFountain(xpx + (Block.BLOCK_WIDTH / 2), ypx, block.fountainColor);

        return true;
    }
}
