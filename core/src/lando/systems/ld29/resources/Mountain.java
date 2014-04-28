package lando.systems.ld29.resources;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;

/**
 * Created by jhoopes on 4/26/14.
 */
public class Mountain extends Resource{

    protected TextureRegion img = Assets.resources.get("mountain");

    public Mountain(float x) {
        super(x);

        this.createSprite(this.img);
    }

    public void createSprite(TextureRegion img){
        sprite = new Sprite(img);
        sprite.setSize(Resource.RESOURCE_WIDTH, 128);
    }

    public String getName() { return "mountain"; }
    public String resourceName() { return "iron"; }
}
