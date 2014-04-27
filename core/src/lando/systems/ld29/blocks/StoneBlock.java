package lando.systems.ld29.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;

public class StoneBlock extends Block {
    private static TextureRegion img = Assets.blocks.get("stone");

    public StoneBlock(float x, float y) {
        super(x, y);
        this.blockType = "stone";
        setSprite(new Sprite(img));
    }
}
