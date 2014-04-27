package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.Constants;
import com.catinthedark.assets.Assets;

/**
 * Created by Ilya on 26.04.2014.
 */
public class OilFactory extends Entity {
    private static final int WIDTH = 4;
    private static final int HEIGHT = 4;
    private float stateTime;

    public OilFactory(float x) {
        super(x, Constants.GROUND_LEVEL, WIDTH, HEIGHT);
        stateTime = 0;
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        stateTime += delta;
        super.render(delta, batch);
        batch.draw(Assets.oilFactoryAppearance.getKeyFrame(stateTime), this.x, this.y, this.width, this.height);
    }
}
