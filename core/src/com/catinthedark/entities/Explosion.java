package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.assets.Assets;

/**
 * Created by Ilya on 27.04.2014.
 */
public class Explosion extends Entity {
    private static final float HEIGHT = 2f;
    private static final float WIDTH = 2f;

    public Explosion(float x, float y) {
        super(x, y, HEIGHT, WIDTH);
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        batch.draw(Assets.explosionAnimation.getKeyFrame(stateTime), this.x, this.y, this.width, this.height);
    }
}
