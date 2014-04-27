package lando.systems.ld29.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;

public class IronBlock extends Block {
    private static TextureRegion img = Assets.blocks.get("iron");

    public IronBlock(float x, float y) {
        super(x, y);
        this.blockType = "iron";
        fountainColor = Color.GRAY;
        setSprite(new Sprite(img));
    }
}
