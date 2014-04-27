package lando.systems.ld29.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.*;

/**
 * Created by jhoopes on 4/26/14.
 */
public class Resource {

    protected Sprite sprite;
    float x;
    float y;
    static final float RESOURCE_WIDTH = 64;
    protected int resourceCount;

    public Resource(float x, float y){
        this.x = x;
        this.y = y;

        // Generate resource count
        this.resourceCount = (int) Math.ceil(Math.random()*10);
    }

    public void createSprite(TextureRegion img){
        sprite = new Sprite(img);
        sprite.setSize(RESOURCE_WIDTH, RESOURCE_WIDTH);
    }

    public void remove(){

    }

    public Sprite getSprite(){
        return this.sprite;
    }

    public void render(SpriteBatch batch){
        getSprite().setPosition(x, y);
        getSprite().draw(batch);
    }

    public int getResourceCount(){
        return this.resourceCount;
    }

    public void setResourceCount(int newRCount){
        this.resourceCount = newRCount;
    }

}
