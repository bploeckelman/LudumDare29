package lando.systems.ld29.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;

public class GrapesBlock extends Block {
    private static TextureRegion img = Assets.blocks.get("grapes");

    public GrapesBlock(float x, float y) {
        super(x, y);
        this.blockType = "grapes";
        toolTipString = "for grapejuice?";
        fountainColor = new Color (1,0,1,1);
        setSprite(new Sprite(img));
    }
}
