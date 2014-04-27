package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.assets.Assets;

/**
 * User: Leyfer Kirill kolbasisha@gmail.com
 * Date: 27.04.14
 * Time: 9:29
 */
public class OilField extends Entity{
    public static final int width = 7;
    public static final int height = 2;

    public OilField(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    public OilField(float x, float y) {
        super(x, y, width, height);
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        batch.draw(Assets.oilFieldAnimation.getKeyFrame(stateTime), this.x, this.y, width, height);
    }
}
