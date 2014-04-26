package lando.systems.ld29.scamps;

/**
 * Author: Ian McNamara <ian.mcnamara@doit.wisc.edu>
 * Date: 4/26/14 @ 3:58 PM
 */
public class Scamp {

    public enum ScampStates {
        HARVESTING,
        EATING,
        SLEEPING,
        MURDERING
    }

    float position;
    float targetPosition;
    boolean isHarvesting = false;


}
