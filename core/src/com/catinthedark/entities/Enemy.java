package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.assets.Assets;

/**
 * User: Leyfer Kirill kolbasisha@gmail.com
 * Date: 26.04.14
 * Time: 13:40
 */
public class Enemy extends Entity {
    private boolean killed = false;

    public Enemy(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public boolean isKilled() {
        return killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        if (!isKilled()) {
            batch.draw(Assets.enemyBlockTexture, this.x, this.y);
        }
    }

}
