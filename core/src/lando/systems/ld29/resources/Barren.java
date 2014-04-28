package lando.systems.ld29.resources;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;

/**
 * Created by jhoopes on 4/26/14.
 */
public class Barren extends Resource {

    protected TextureRegion img = Assets.resources.get("barren");

    public Barren(float x) {
        super(x);
        this.createSprite(this.img);
    }

    public String getName() { return "barren"; }
    public String resourceName() { return "OMGNOTHING"; }

}
