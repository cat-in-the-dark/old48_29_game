package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.assets.Assets;

/**
 * User: Leyfer Kirill kolbasisha@gmail.com
 * Date: 27.04.14
 * Time: 11:01
 */
public class TntVehicle extends Entity {
    public static final int width = 4;
    public static final int height = 2;
    private float speedX;
    private float speedY;

    public TntVehicle(float x, float y, int width, int height, float speedX, float speedY) {
        super(x, y, width, height);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public TntVehicle(float x, float y, float speedX, float speedY) {
        super(x, y, width, height);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    private void move() {
        this.x += speedX;
        this.y += speedY;
        bounds.x = this.x;
        bounds.y = this.y;
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        move();
        batch.draw(Assets.tntVehicleTexture, x, y, width, height);
    }
}
