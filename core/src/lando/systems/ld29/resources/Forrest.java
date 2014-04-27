package lando.systems.ld29.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.*;
import lando.systems.ld29.core.Assets;

/**
 * Created by jhoopes on 4/26/14.
 */
public class Forrest extends Resource {

    protected TextureRegion img = Assets.resources.get("forrest");

    public Forrest(float x, float y) {
        super(x, y);

       this.createSprite(this.img);
    }

    public void render(){

    }

    public void update(){

    }

}
