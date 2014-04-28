package lando.systems.ld29.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.resources.Field;
import lando.systems.ld29.resources.Forrest;
import lando.systems.ld29.resources.Resource;

public class WheatBlock extends Block {
    private static TextureRegion img = Assets.blocks.get("dirt");

    public WheatBlock(float x, float y) {
        super(x, y);
        this.blockType = "wheat";
        toolTipString = "Grows a field";
        fountainColor = new Color(.5f, .3f, .1f, 1);
        setSprite(new Sprite(img));
        cost = 3;
    }
    
	public Resource MakeResource(){

		return new Field(x);
	}
}
