package lando.systems.ld29.scamps;

import lando.systems.ld29.World;

import java.util.Date;
import java.util.Random;

/**
 * Author: Ian McNamara <ian.mcnamara@doit.wisc.edu>
 * Date: 4/26/14 @ 3:58 PM
 */
public class ScampManager {

    private final static int INITIAL_SCAMP_COUNT = 8;

    World world;
    Scamp[] scamps;

    public ScampManager(World world) {
        this.world = world;
        this.placeScamps();
    }

    /**
     * Places the starting number of scamps in the world.
     * Clears any already existing Scamps, so this should only really be called on startup.
     */
    private void placeScamps() {
        Random r = new Random( new Date().getTime() );
        int i;
        float thisPosition;

        this.scamps = null;
        this.scamps = new Scamp[INITIAL_SCAMP_COUNT];
        for (i = 0; i < this.scamps.length; i++) {
            thisPosition = r.nextFloat() * (this.world.gameWidth - 1);
            new Scamp( thisPosition );
        }

    }


}
