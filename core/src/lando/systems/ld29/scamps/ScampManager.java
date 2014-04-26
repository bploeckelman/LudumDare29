package lando.systems.ld29.scamps;

import lando.systems.ld29.World;

/**
 * Author: Ian McNamara <ian.mcnamara@doit.wisc.edu>
 * Date: 4/26/14 @ 3:58 PM
 */
public class ScampManager {

    World world;
    Scamp[] scamps = new Scamp[8];

    public ScampManager(World world) {
        this.world = world;
    }

}
