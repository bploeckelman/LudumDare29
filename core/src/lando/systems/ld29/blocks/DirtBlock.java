package lando.systems.ld29.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.resources.Barren;
import lando.systems.ld29.resources.Resource;

public class DirtBlock extends Block {
    private static TextureRegion img = Assets.blocks.get("dirt");

    public DirtBlock(float x, float y) {
        super(x, y);
        this.blockType = "dirt";
        toolTipString = "useless";
        fountainColor = new Color(.5f, .3f, .1f, 1);
        setSprite(new Sprite(img));
    }

    public Resource makeResource() {
        return new Barren(x);
    }
}
