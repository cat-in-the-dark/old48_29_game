package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.assets.Assets;

public class Bullet extends Entity {

	public Bullet(float x, float y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(float delta, SpriteBatch batch) {
		batch.draw(Assets.bulletTexture, x, y, width, height);
	}

}
