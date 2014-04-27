package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.assets.Assets;

public class Bullet extends Entity {
    public static final int bulletWidth = 1;
    public static final int bulletHeight = 1;
    private float speedX;
    private float speedY;

	public Bullet(float x, float y, int width, int height, float speedX, float speedY) {
		super(x, y, width, height);
        this.speedX = speedX;
        this.speedY = speedY;
	}

    public Bullet(float x, float y, float speedX, float speedY) {
        super(x, y, bulletWidth, bulletHeight);
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
        move();
		batch.draw(Assets.bulletTexture, x, y, width, height);
	}

}
