package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.catinthedark.assets.Assets;

/**
 * User: Leyfer Kirill kolbasisha@gmail.com
 * Date: 26.04.14
 * Time: 12:55
 */
public class HouseBlock extends Entity {
    private boolean withEnemy = false;
    private boolean destroyed = false;
    private float x;
    private float y;
    public static final int blockWidth = 2;
    public static final int blockHeight = 2;

    public boolean isWithEnemy() {
        return withEnemy;
    }

    public void setWithEnemy(boolean withEnemy) {
        this.withEnemy = withEnemy;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public HouseBlock(boolean withEnemy, float x, float y) {
        super(x, y, blockWidth, blockHeight);
        setWithEnemy(withEnemy);
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        if (isWithEnemy()) {
            batch.draw(Assets.enemyBlockTexture, this.x, this.y);
        } else {
            batch.draw(Assets.houseBlockTexture, this.x, this.y);
        }
    }
}
