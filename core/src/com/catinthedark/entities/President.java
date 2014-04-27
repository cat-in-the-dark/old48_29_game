package com.catinthedark.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.Constants;
import com.catinthedark.assets.Assets;

/**
 * Created by Ilya on 26.04.2014.
 */
public class President extends Entity {
    public static final int WIDTH = 6;
    public static final int HEIGHT = 9;

    private final Vector2 minAcceleration = new Vector2(0f, 0f);
    private final Vector2 maxAcceleration = new Vector2(0.5f, 0f);
    private Vector2 acceleration = new Vector2(minAcceleration.x, minAcceleration.y);
    private State state;

    private double shutDelay = 1;
    private double lastShutTime = 0;
    private double lastLayOilFactoryTime = 0;
    private double layDelay = 1;
    private float stateTime = 0;
    private int healt = 100;

    public President(float x, float y) {
        super(x, y, WIDTH, HEIGHT);
        this.direction = Direction.RIGHT;
        this.state = State.IDLE;
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        stateTime += delta;
        lastShutTime += delta;
        lastLayOilFactoryTime += delta;
        tryMove();
        batch.draw(playAnimation(stateTime), this.x, this.y - 0.5f, width, height);
    }

    private TextureRegion playAnimation(float stateTime) {
        if (state == State.IDLE) {
            return Assets.presidentIdle.getKeyFrame(stateTime);
        } else {
            switch (direction) {
                case RIGHT:
                    return Assets.presidentRunRight.getKeyFrame(stateTime);
                case LEFT:
                    return Assets.presidentRunLeft.getKeyFrame(stateTime);
            }
        }

        return Assets.presidentIdle.getKeyFrame(stateTime);
    }

    public Rocket shut() {
        if (lastShutTime > shutDelay) {
            lastShutTime = 0;
            return new Rocket(this.x + this.width / 2f, this.y + this.height / 2f, this);
        }
        return null;
    }

    public OilFactory layOilFactory() {
        if (lastLayOilFactoryTime > layDelay) {
            lastLayOilFactoryTime = 0;
            return new OilFactory(this.x);
        }
        return null;
    }

    public void move(boolean is_moving, Camera camera) {
        if (!is_moving) {
            state = State.IDLE;
            acceleration.x = minAcceleration.x;
        } else {
            state = State.RUN;
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
        this.bounds.x = this.x;
    }

    public boolean canMove(Camera camera) {
        float nextX = this.x + acceleration.x;
        return !(camera.position.x - this.width - Constants.maxPresidentDestinationFromBorder < nextX || nextX < camera.position.x - camera.viewportWidth / 2f);
    }
    
    public int getHealth(){
    	return healt;
    }
    
    public void doDamage(int amount){
    	healt -= amount;
    }
}
