package lando.systems.ld29;

import lando.systems.ld29.blocks.Block;
import lando.systems.ld29.resources.Resource;

public class Global {
    public static final float UNDERGROUND_HEIGHT = 164;
    public static final float UNDERGROUND_LEVEL = Hud.Height;
    public static final float GROUND_LEVEL = UNDERGROUND_HEIGHT + (World.gameHeight * Block.BLOCK_WIDTH) + Resource.RESOURCE_GROUND_LEVEL_OFFSET;
}
