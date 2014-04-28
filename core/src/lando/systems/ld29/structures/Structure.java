package lando.systems.ld29.structures;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.ld29.Global;
import lando.systems.ld29.World;
import lando.systems.ld29.blocks.Block;
import lando.systems.ld29.scamps.Scamp;

import java.util.ArrayList;
import java.util.Iterator;

public class Structure {

    private World world;
    private Sprite sprite;

    private int maxCapacity;
    ArrayList<Scamp> scamps;

    float x;
    
    String name = "CHANGE ME";

    public Structure(float x, World world){
        this.x = x;
        this.world = world;
        this.maxCapacity = 0;
    }

    public void setSprite(Sprite sprite){
        sprite.setPosition(x * Block.BLOCK_WIDTH, Global.GROUND_LEVEL);
        this.sprite = sprite;
    }
    public Sprite getSprite(){
        return this.sprite;
    }

    World getWorld(){
        return world;
    }

    void setMaxCapacity(int maxCapacity){
        this.maxCapacity = maxCapacity;
    }
    public int getMaxCapacity(){
        return maxCapacity;
    }
    public int getCapacity(){
        if(maxCapacity > 0){
            return maxCapacity - scamps.size();
        }
        return 0;
    }

    public void enter(Scamp scamp){
        if(maxCapacity > 0) {
            scamps.add(scamp);
        }
    }
    public void leave(Scamp scamp){
        scamps.remove(scamp);
        scamp.setState(Scamp.ScampState.IDLE);
    }
    public void evict(){
        if(maxCapacity > 0) {
            Iterator<Scamp> iterator = scamps.iterator();
            while(iterator.hasNext()){
                iterator.next().setState(Scamp.ScampState.IDLE);
                iterator.remove();
            }
        }
    }

    public void update(float dt){
        // Maybe do something for every Structure?
    }

    public void render(SpriteBatch batch){
        getSprite().draw(batch);
    }
}
