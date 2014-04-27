package lando.systems.ld29;

import lando.systems.ld29.blocks.Block;

public class Global {
    public static final float UNDERGROUND_HEIGHT = 164;
    public static final float UNDERGROUND_LEVEL = 44;
    public static final float GROUND_LEVEL = UNDERGROUND_HEIGHT + World.gameHeight * Block.BLOCK_WIDTH;
}
