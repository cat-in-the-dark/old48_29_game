package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.assets.Assets;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Rocket extends Entity {
    private static final int rocketWidth = 2;
    private static final int rocketHeight = 1;
    private final float speedModification = 1.3f;
    private final Vector2 maxAccelerationMiddle = new Vector2(0.5f * speedModification, -0.015f * speedModification);
    private final Vector2 maxAccelerationUp = new Vector2(0.5f * speedModification, 0.08f * speedModification);
    private final Vector2 maxAccelerationDown = new Vector2(0.5f * speedModification, -0.2f * speedModification);
    private final Vector2 acceleration = new Vector2(maxAccelerationMiddle.x, maxAccelerationMiddle.y);
    private final President creator;

    public Rocket(float x, float y, President entity){
        super(x, y, rocketWidth, rocketHeight);
        this.creator = entity;
        switch (this.creator.state) {
            case AIM_UP:
                acceleration.x = maxAccelerationUp.x;
                acceleration.y = maxAccelerationUp.y;
                break;
            case AIM_DOWN:
                acceleration.x = maxAccelerationDown.x;
                acceleration.y = maxAccelerationDown.y;
                break;
            case IDLE:
                acceleration.x = maxAccelerationMiddle.x;
                acceleration.y = maxAccelerationMiddle.y;
                break;
        }
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        x += acceleration.x;
        y += acceleration.y;
        bounds.x = x;
        bounds.y = y;
        batch.draw(Assets.rocketTexture, x, y, width, height);
    }
}
