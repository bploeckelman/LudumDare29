package lando.systems.ld29.scamps;

/**
 * Author: Ian McNamara <ian.mcnamara@doit.wisc.edu>
 * Date: 4/26/14 @ 3:58 PM
 */
public class Scamp {

	public enum ScampState {
		IDLE,
		HARVESTING,
		EATING,
		SLEEPING,
		MURDERING
	}

	float position;
	float targetPosition;
	ScampState currentState = ScampState.IDLE;
	int skinID = 1;

	public Scamp(float startingPosition) {
		this.position = startingPosition;
	}


}
