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
        GRAPE,
        IRON,
        STONE,
        WOOD
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
        resourceNameToType.put("food", ScampResourceType.FOOD);
        resourceNameToType.put("stone", ScampResourceType.STONE);
        resourceNameToType.put("wood", ScampResourceType.WOOD);
        resourceNameToType.put("grapes", ScampResourceType.GRAPE);
        resourceNameToType.put("iron", ScampResourceType.IRON);
    }

    public ScampResourceType getType(String resourceName) {
        return resourceNameToType.get(resourceName);
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
