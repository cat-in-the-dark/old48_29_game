package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private boolean top;
    private boolean left;

    private float stateTime;

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

    public HouseBlock(boolean withEnemy, boolean top, boolean left, float x, float y) {
        super(x, y, blockWidth, blockHeight);
        setWithEnemy(withEnemy);
        this.x = x;
        this.y = y;
        this.top = top;
        this.left = left;
        stateTime = 0f;
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
        stateTime += delta;
        TextureRegion region;
        if (destroyed) {
            if (left) {
                region = Assets.leftBrocken;
            } else {
                region = Assets.rightBrocken;
            }
        } else {
            if (top) {
                if (left) {
                    region = Assets.leftTopUnbrocken;
                } else {
                    region = Assets.rightToppUnbrocken;
                }
            } else {
                if (left) {
                    region = Assets.leftUnbrocken;
                } else {
                    region = Assets.rightUnbrocken;
                }
            }
        }
        batch.draw(region, this.x, this.y, HouseBlock.blockWidth, HouseBlock.blockHeight);
        if (isWithEnemy()) {
            batch.draw(Assets.mdIdle.getKeyFrame(stateTime), this.x, this.y, HouseBlock.blockWidth, HouseBlock.blockHeight);
        }
    }
}
