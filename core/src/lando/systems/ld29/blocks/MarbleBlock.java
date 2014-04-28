package lando.systems.ld29.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.resources.Forrest;
import lando.systems.ld29.resources.MarbleQuarry;
import lando.systems.ld29.resources.Resource;

public class MarbleBlock extends Block {
    private static TextureRegion img = Assets.blocks.get("marble");

    public MarbleBlock(float x, float y) {
        super(x, y);
        this.blockType = "marble";
        toolTipString = "a classy building material";
        setSprite(new Sprite(img));
    }
    
	public Resource MakeResource(){

		return new MarbleQuarry(x);
	}
}
