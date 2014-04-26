package com.catinthedark.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.Constants;
import com.catinthedark.assets.Assets;

/**
 * Created by Ilya on 26.04.2014.
 */
public class President extends Entity {
    private static final int presidentWidth = 3;
    private static final int presidentHeight = 10;
    private final Vector2 minAcceleration = new Vector2(0f, 0f);
    private final Vector2 maxAcceleration = new Vector2(0.5f, 0f);
    private Vector2 acceleration = new Vector2(minAcceleration.x, minAcceleration.y);
    private State state;

    private double shutDelay = 1;
    private double lastShutTime = 0;

    public President(float x, float y) {
        super(x, y, presidentWidth, presidentHeight);
        this.direction = Direction.RIGHT;
        this.state = State.IDLE;
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        lastShutTime += delta;
        tryMove();
        batch.draw(Assets.presidentTexture, this.x, this.y);
    }

    public Rocket shut() {
        if (lastShutTime > shutDelay) {
            lastShutTime = 0;
            return new Rocket(this.x + this.width / 2f, this.y + this.height / 2f, this);
        }
        return null;
    }

    public void move(boolean is_moving, Camera camera) {
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

        if (!canMove(camera)) {
            acceleration.x = minAcceleration.x;
        }
    }

    private void tryMove() {
        this.x += acceleration.x;
    }

    public boolean canMove(Camera camera) {
        float nextX = this.x + acceleration.x;
        return !(camera.position.x - this.width - Constants.maxPresidentDestinationFromBorder< nextX || nextX < 0);
    }
}
