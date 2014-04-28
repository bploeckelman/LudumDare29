package lando.systems.ld29.scamps;

import lando.systems.ld29.resources.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Ian McNamara <ian.mcnamara@doit.wisc.edu>
 * Date: 4/26/14 @ 6:16 PM
 */
public class ScampResources {

    public static enum ScampResourceType {
        FOOD,
        WOOD,
        STONE,
        IRON,
        MARBLE,
        GOLD,
        GRAPES,
        FUEL,
        CIRCUITS,
        METEOR,
        STEEL,
        PEOPLE
    }
    Map<ScampResourceType, Integer> scampResourcesByType;
    Map<String, ScampResourceType> resourceNameToType;

    public ScampResources() {
        this.initializeScampResources();
    }

    public void initializeScampResources() {
        scampResourcesByType = new HashMap<ScampResourceType, Integer>(ScampResourceType.values().length);
        for (ScampResourceType thisResourceType : ScampResourceType.values() ) {
            scampResourcesByType.put(thisResourceType, 0);
        }

        resourceNameToType = new HashMap<String, ScampResourceType>();
        resourceNameToType.put("FOOD",     ScampResourceType.FOOD);
        resourceNameToType.put("WOOD",     ScampResourceType.WOOD);
        resourceNameToType.put("STONE",    ScampResourceType.STONE);
        resourceNameToType.put("IRON",     ScampResourceType.IRON);
        resourceNameToType.put("MARBLE",   ScampResourceType.MARBLE);
        resourceNameToType.put("GOLD",     ScampResourceType.GOLD);
        resourceNameToType.put("GRAPES",   ScampResourceType.GRAPES);
        resourceNameToType.put("FUEL",     ScampResourceType.FUEL);
        resourceNameToType.put("CIRCUITS", ScampResourceType.CIRCUITS);
        resourceNameToType.put("METEOR",   ScampResourceType.METEOR);
        resourceNameToType.put("STEEL",    ScampResourceType.STEEL);
        resourceNameToType.put("PEOPLE",   ScampResourceType.PEOPLE);
    }

    public ScampResourceType getType(String resourceName) {
        return resourceNameToType.get(resourceName.toUpperCase());
    }

    public void addScampResource(ScampResourceType scampResourceType) {
        addScampResources(scampResourceType, 1);
    }

    public void addScampResources(ScampResourceType scampResourceType, int numResources) {
        this.scampResourcesByType.put(
                scampResourceType,
                this.scampResourcesByType.get(scampResourceType) + numResources);
    }

    /**
     *
     * @param scampResourceType
     * @return Returns true if the resource was successfully removed.  Returns FALSE if there wasn't a resource of the
     * proper type to remove.
     */
    public boolean removeScampResource(ScampResourceType scampResourceType) {
        int resourceCount = this.scampResourcesByType.get(scampResourceType);
        if (resourceCount < 1) {
            return false;
        } else {
            this.scampResourcesByType.put(scampResourceType, resourceCount - 1);
            return true;
        }
    }

    public int getScampResourceCount(ScampResourceType scampResourceType) {
        return this.scampResourcesByType.get(scampResourceType);
    }

}
