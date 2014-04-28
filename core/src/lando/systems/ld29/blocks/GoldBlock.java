package lando.systems.ld29.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.resources.Forrest;
import lando.systems.ld29.resources.GoldMine;
import lando.systems.ld29.resources.Resource;

public class GoldBlock extends Block {
    private static TextureRegion img = Assets.blocks.get("iron");

    public GoldBlock(float x, float y) {
        super(x, y);
        this.blockType = "gold";
        toolTipString = "Used for making circuits";
        fountainColor = Color.GRAY;
        setSprite(new Sprite(img));
        cost = 10;
    }
    
	public Resource MakeResource(){

		return new GoldMine(x);
	}
    
}