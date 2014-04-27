package lando.systems.ld29.structures;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;

public class WarehouseStructure extends Structure {
    private static TextureRegion img = Assets.structures.get("warehouse");

    public WarehouseStructure(float x, World world) {
        super(x, world);

        setSprite(new Sprite(img));
    }
}
