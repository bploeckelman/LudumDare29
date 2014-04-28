package lando.systems.ld29.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.resources.Forrest;
import lando.systems.ld29.resources.Resource;

public class AcornBlock extends Block {
    private static TextureRegion img = Assets.blocks.get("acorn");

    public AcornBlock(float x, float y) {
        super(x, y);
        this.blockType = "acorn";
        toolTipString = "I want to become a tree!";
        fountainColor = Color.GREEN;
        setSprite(new Sprite(img));
    }
    
	public Resource MakeResource(){

		return new Forrest(x);
	}
}
