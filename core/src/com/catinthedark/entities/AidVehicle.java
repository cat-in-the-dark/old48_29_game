package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.assets.Assets;

/**
 * User: Leyfer Kirill kolbasisha@gmail.com
 * Date: 27.04.14
 * Time: 12:59
 */
public class AidVehicle extends Entity{
    public static final int width = 4;
    public static final int height = 2;
    private float speedX;
    private float speedY;
    private boolean destroyed;
    private boolean destroyAnimationPlayed = false;
    private float accelerationY = 0.0f;

    public AidVehicle(float x, float y, int width, int height, float speedX, float speedY) {
        super(x, y, width, height);
        setDestroyed(false);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public AidVehicle(float x, float y, float speedX, float speedY) {
        super(x, y, width, height);
        setDestroyed(false);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    private void move() {
        this.x += speedX;
        this.y += speedY;
        speedY += accelerationY;
        bounds.x = this.x;
        bounds.y = this.y;
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        move();
        if (!isDestroyed()) {
            stateTime += delta;
            batch.draw(Assets.aidVehicleRiding.getKeyFrame(stateTime), x, y, width, height);
        } else {
            playDestroyAnimation(batch);
        }
    }

    private void playDestroyAnimation(SpriteBatch batch) {
        if (!destroyAnimationPlayed) {
            destroyAnimationPlayed = true;
            speedY = 0.1f;
            speedX = 0.1f;
            accelerationY = -0.01f;
        }
        batch.draw(Assets.aidVehicleExploded, x, y, width, height);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
