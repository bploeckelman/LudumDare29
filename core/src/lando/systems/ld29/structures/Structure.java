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

    private int capacity;
    ArrayList<Scamp> scamps;

    float x;
    
    String name = "CHANGE ME";

    public Structure(float x, World world){
        this.x = x;
        this.world = world;
        this.capacity = 0;
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

    void setCapacity(int capacity){
        this.capacity = capacity;
    }
    public int getCapacity(){
        return capacity;
    }
    public int getOccupancy(){
        if(capacity > 0){
            return capacity - scamps.size();
        }
        return 0;
    }

    public void enter(Scamp scamp){
        if(capacity > 0) {
            scamps.add(scamp);
        }
    }
    public void evict(){
        if(capacity > 0) {
            Iterator<Scamp> iterator = scamps.iterator();
            while(iterator.hasNext()){
                iterator.next().setState(Scamp.ScampState.IDLE);
                iterator.remove();
            }
        }
    }

    public void update(float dt){
        if(getWorld().dayCycle.hasNightEnded()){
            evict();
        }
    }

    public void render(SpriteBatch batch){
        getSprite().draw(batch);
    }
}
