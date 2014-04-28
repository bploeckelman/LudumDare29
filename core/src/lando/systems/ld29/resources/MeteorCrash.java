package lando.systems.ld29.resources;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;

/**
 * Created by jhoopes on 4/26/14.
 */
public class MeteorCrash extends Resource {
    protected TextureRegion img = Assets.resources.get("meteor");

    public MeteorCrash(float x) {
        super(x);

        this.createSprite(this.img);
        this.resourceCount = 1;
    }

    public String getName() { return "meteorcrash"; }
    public String resourceName() { return "meteor"; }
}
