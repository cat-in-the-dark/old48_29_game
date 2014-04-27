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
    
    private boolean destroyed;

    public OilFactory(float x) {
        super(x, Constants.GROUND_LEVEL, WIDTH, HEIGHT);
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        batch.draw(Assets.oilFactoryAppearance.getKeyFrame(stateTime), this.x, this.y, this.width, this.height);
    }

	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}
    
}
