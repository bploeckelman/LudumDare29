package lando.systems.ld29.resources;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;

/**
 * Created by jhoopes on 4/26/14.
 */
public class Field extends Resource {

    protected TextureRegion img = Assets.resources.get("field");

    public Field(float x, float y) {
        super(x, y);

        this.createSprite(this.img);
    }
}
