package lando.systems.ld29.resources;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;

/**
 * Created by jhoopes on 4/26/14.
 */
public class MarbleQuarry extends Resource {
    protected TextureRegion img = Assets.resources.get("marblequarry");

    public MarbleQuarry(float x) {
        super(x);

        this.createSprite(this.img);
    }

    public String getName() { return "marblequarry"; }
    public String resourceName() { return "marble"; }
}
