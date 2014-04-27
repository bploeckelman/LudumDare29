package lando.systems.ld29.scamps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import lando.systems.ld29.Global;
import lando.systems.ld29.World;
import lando.systems.ld29.blocks.Block;
import lando.systems.ld29.core.Assets;

/**
 * Author: Ian McNamara <ian.mcnamara@doit.wisc.edu>
 * Date: 4/26/14 @ 3:58 PM
 */
public class Scamp {

    public static final int SCAMP_SIZE = 32;
    public static final float SCAMP_SPEED = 1.0f;

    public enum ScampState {
        IDLE,
        HARVESTING,
        EATING,
        SLEEPING,
        MURDERING
    }

    int skinID;
    float position;
    float targetPosition;

    boolean walkRight;

    TextureRegion texture;
    ScampState currentState = ScampState.IDLE;

    public Scamp(float startingPosition) {
        this.position = startingPosition;
        this.targetPosition = Assets.random.nextInt(World.gameWidth);
        // TODO : each scamp should have a unique skin, duplicates will end up facing the wrong direction
        this.skinID = Assets.random.nextInt(Assets.num_scamps);
        this.walkRight = (targetPosition - position) >= 0;
        this.texture = Assets.scamps.get(skinID);
        if(!walkRight) texture.flip(true, false);
    }

    boolean atTarget = false;
    public void update(float dt) {
        // If we've reached the target, acquire a new target
        if((walkRight && position >= targetPosition) || (!walkRight && position <= targetPosition)) {
            atTarget = true;

            // If idling, move around randomly
            if (currentState == ScampState.IDLE) {
                targetPosition = Assets.random.nextInt(World.gameWidth);
                atTarget = false;
            } else {
                System.out.println("non-idle scamp " + this.toString() + " arrived at target: target(" + targetPosition + "), pos(" + position + ")");
            }

            // todo : won't always be turning around
            texture.flip(true, false);

            // todo : walkRight will be based on new targetPosition
            walkRight = !walkRight;

        }

        if (!atTarget) {
            // Move you sluggard!
            position += (walkRight ? SCAMP_SPEED : -SCAMP_SPEED) * dt;
        }
    }


    public void render(SpriteBatch batch) {
        batch.draw(texture, position * Block.BLOCK_WIDTH, Global.GROUND_LEVEL, SCAMP_SIZE, SCAMP_SIZE);
    }

    public boolean isIdle() { return currentState == ScampState.IDLE; }
    public boolean isEating() { return currentState == ScampState.EATING; }
    public boolean isSleeping() { return currentState == ScampState.SLEEPING; }
    public boolean isMurdering() { return currentState == ScampState.MURDERING; }
    public boolean isHarvesting() { return currentState == ScampState.HARVESTING; }

    public void setState(ScampState state) { currentState = state; }

    public void setTarget(float position) { this.targetPosition = position; }
    public void setTarget(Block block) { this.targetPosition = block.getX(); }

    public int getBlockPosition() { return (int) Math.floor(position); }
    public int getBlockTargetPosition() { return (int) Math.floor(targetPosition); }

    public float getPixelPosition() { return position * Block.BLOCK_WIDTH; }
    public float getPixelTargetPosition() { return targetPosition * Block.BLOCK_WIDTH; }

    public int getSkinID() { return skinID; }
    public TextureRegion getSkin() { return texture; }

    public void setSkin(int skinID) {
        TextureRegion temp = null;
        try {
            temp = Assets.scamps.get(skinID);
        } catch(IndexOutOfBoundsException e) {
            System.err.println("Error: tried to set scamp skin to invalid texture: " + skinID);
            return;
        }

        if(temp == null) {
            System.err.println("Error: tried to set scamp skin to missing texture: " + skinID);
            return;
        }

        this.skinID = skinID;
        this.texture = temp;
    }

}
