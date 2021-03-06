package lando.systems.ld29.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.resources.Forrest;
import lando.systems.ld29.resources.GoldMine;
import lando.systems.ld29.resources.Resource;

public class GoldBlock extends Block {
    private static TextureRegion img = Assets.blocks.get("gold");

    public GoldBlock(float x, float y) {
        super(x, y);
        this.blockType = "gold";
        toolTipString = "Used for making circuits";
        fountainColor = Color.GRAY;
        setSprite(new Sprite(img));
    }
    
	public Resource MakeResource(){

		return new GoldMine(x);
	}
    
}