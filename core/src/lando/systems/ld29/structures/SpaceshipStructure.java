package lando.systems.ld29.structures;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.World;
import lando.systems.ld29.core.Assets;

public class SpaceshipStructure extends Structure {
    private static TextureRegion img = Assets.structures.get("spaceship");

    public SpaceshipStructure(float x, World world) {
        super(x, world);

        setSprite(new Sprite(img));
    }
}
