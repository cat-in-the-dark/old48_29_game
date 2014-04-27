package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.catinthedark.assets.Assets;

/**
 * Created by Ilya on 26.04.2014.
 */
public class Rocket extends Entity {
    private static final int rocketWidth = 2;
    private static final int rocketHeight = 2;
    private final Vector2 maxAcceleration = new Vector2(0.5f, 0.5f);
    private final Vector2 acceleration = new Vector2(0f, 0f);
    private final President creator;

    public Rocket(float x, float y, President entity){
        super(x, y, rocketWidth, rocketHeight);
        this.creator = entity;
        switch (this.creator.direction) {
            case RIGHT:
                acceleration.x = maxAcceleration.x;
                break;
            case LEFT:
                acceleration.x = maxAcceleration.x;
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
        batch.draw(Assets.rocketTexture, x, y);
    }
}
