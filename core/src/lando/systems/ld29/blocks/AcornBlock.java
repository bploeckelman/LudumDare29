package lando.systems.ld29.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;

public class AcornBlock extends Block {
    private static TextureRegion img = Assets.blocks.get("acorn");

    public AcornBlock(float x, float y) {
        super(x, y);

        setSprite(new Sprite(img));
    }
}
