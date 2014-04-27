package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.assets.Assets;

public class Bullet extends Entity {
    public static final int bulletWidth = 1;
    public static final int bulletHeight = 1;

	public Bullet(float x, float y, int width, int height) {
		super(x, y, width, height);
	}


    public Bullet(float x, float y) {
        super(x, y, bulletWidth, bulletHeight);
    }

	@Override
	public void render(float delta, SpriteBatch batch) {
		batch.draw(Assets.bulletTexture, x, y, width, height);
	}

}
