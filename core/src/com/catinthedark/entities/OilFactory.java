package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.Constants;
import com.catinthedark.assets.Assets;

/**
 * Created by Ilya on 26.04.2014.
 */
public class OilFactory extends Entity {
    public static final int WIDTH = 4;
    public static final int HEIGHT = 4;
    private float localStateTime;
    
    private boolean destroyed;

    public OilFactory(float x) {
        super(x, Constants.GROUND_LEVEL, WIDTH, HEIGHT);
        localStateTime = 0.0f;
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        if (!isDestroyed()) {
            batch.draw(Assets.oilFactoryAppearance.getKeyFrame(stateTime), this.x, this.y, this.width, this.height);
        } else {
            localStateTime += delta;
            batch.draw(Assets.oilFactoryDisappearance.getKeyFrame(localStateTime), this.x, this.y, this.width, this.height);
        }
    }

	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}
    
}
