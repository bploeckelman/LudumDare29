package lando.systems.ld29.scamps;

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

    public ScampResources() {
        this.initializeScampResources();
    }

    public void initializeScampResources() {
        this.scampResourcesByType = new HashMap<ScampResourceType, Integer>(ScampResourceType.values().length);
        for (ScampResourceType thisResourceType : ScampResourceType.values() ) {
            this.scampResourcesByType.put(thisResourceType, 0);
        }
    }

    public void addScampResource(ScampResourceType scampResourceType) {
        this.scampResourcesByType.put(
                scampResourceType,
                this.scampResourcesByType.get(scampResourceType) + 1);
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
