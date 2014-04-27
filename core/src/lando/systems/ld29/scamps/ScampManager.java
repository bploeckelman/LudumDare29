package lando.systems.ld29.scamps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.ld29.World;
import lando.systems.ld29.blocks.Block;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Author: Ian McNamara <ian.mcnamara@doit.wisc.edu>
 * Date: 4/26/14 @ 3:58 PM
 */
public class ScampManager {

    private static enum ScampPriority {
        FOOD,
        SHELTER,
        WINE
    }

    private final static int INITIAL_SCAMP_COUNT = 8;
    private final static float DEFAULT_SCAMP_PRIORITY_SCORE = 10;
    private final static float PRIORITY_RECOMPUTE_TIME = 10;

    World world;
    ScampResources scampResources;
    int scampCount = INITIAL_SCAMP_COUNT;
    Scamp[] scamps;
    float accum = 0;

    public ScampManager(World world) {
        this.world = world;
        this.scampResources = new ScampResources();
        this.placeScamps();
    }

    private Map<ScampPriority, Float> scampPriorityScores = new HashMap<ScampPriority, Float>(
            ScampPriority.values().length
    );

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
            thisPosition = r.nextInt(this.world.gameWidth);
            this.scamps[i] = new Scamp( thisPosition );
        }

    }

    public void update(float dt) {
        this.accum += dt;
        if (this.accum > PRIORITY_RECOMPUTE_TIME) {
            System.out.println("update() | it's time");
            this.accum = this.accum % PRIORITY_RECOMPUTE_TIME;
            this.determinePriorities();
        }
        for(Scamp scamp : scamps) { scamp.update(dt); }
    }

    public void renderScamps(SpriteBatch batch) {
        for(Scamp scamp : scamps) { scamp.render(batch); }
    }

    public void determinePriorities() {

        System.out.println("determinePriorities | called");
        float thisPriorityScore;


        for (ScampPriority scampPriority : ScampPriority.values()) {

            thisPriorityScore = DEFAULT_SCAMP_PRIORITY_SCORE;

            switch (scampPriority) {

                case FOOD:
                    float foodPriority = 50;
                    int foodCount = this.scampResources.getScampResourceCount(ScampResources.ScampResourceType.FOOD);
                    double temp = (foodCount / (this.scampCount * 2));
                    if (temp < 1) {
                        temp = (1 - temp) * 50;
                    } else {
                        temp = Math.pow(temp, 2);
                    }
                    foodPriority += (int)temp;
                    System.out.println("determinePriorities | foodPriority='" + foodPriority + "'");
                    this.scampPriorityScores.put(scampPriority, foodPriority);

                    break;

                case SHELTER:
                    float shelterPriority = 40;
                    break;

                case WINE:
                    break;

                default:

            }
        }



    }









}
