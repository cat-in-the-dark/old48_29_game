package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.assets.Assets;

/**
 * Created by Ilya on 26.04.2014.
 */
public class President extends Entity {
    private static final int presidentWidth = 3;
    private static final int presidentHeight = 10;
    private final Vector2 minAcceleration = new Vector2(0f, 0f);
    private final Vector2 maxAcceleration = new Vector2(0.5f, 0.5f);
    private Vector2 acceleration = new Vector2(minAcceleration.x, minAcceleration.y);
    private Direction direction;
    private State state;

    public enum Direction {
        RIGHT, LEFT
    }
    public enum State {
        IDLE, RUN, JUMP
    }

    public President(int x, int y) {
        super(x, y, presidentWidth, presidentHeight);
        this.direction = Direction.RIGHT;
        this.state = State.IDLE;
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        this.x += acceleration.x;
        batch.draw(Assets.presidentTexture, this.x, this.y);
    }

    public void setDirection(Direction dir) {
        this.direction = dir;
    }
    public void move(boolean is_moving) {
        if (!is_moving) {
            acceleration.x = minAcceleration.x;
        } else {
            switch (direction) {
                case RIGHT:
                    acceleration.x = maxAcceleration.x;
                    break;
                case LEFT:
                    acceleration.x = -maxAcceleration.x;
                    break;
            }
        }
    }
}
