package lando.systems.ld29.scamps;

/**
 * Author: Ian McNamara <ian.mcnamara@doit.wisc.edu>
 * Date: 4/26/14 @ 5:45 PM
 */
public class ScampStructure {

	public static enum ScampStructureType {
		HOUSE,
		FARM,
		VINEYARD,
		TEMPLE
	}

	private ScampStructureType type;

	public ScampStructure(ScampStructureType type) {
		this.type = type;
	}

	public void render() {
		//todo
	}

}
