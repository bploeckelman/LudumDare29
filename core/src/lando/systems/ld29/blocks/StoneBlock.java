package lando.systems.ld29.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.ld29.core.Assets;
import lando.systems.ld29.resources.Forrest;
import lando.systems.ld29.resources.Quarry;
import lando.systems.ld29.resources.Resource;

public class StoneBlock extends Block {
    private static TextureRegion img = Assets.blocks.get("stone");

    public StoneBlock(float x, float y) {
        super(x, y);
        this.blockType = "stone";
        toolTipString = "this is what happens to earth when it gets too hard";
        setSprite(new Sprite(img));
    }
    
	public Resource MakeResource(){

		return new Quarry(x);
	}
}
