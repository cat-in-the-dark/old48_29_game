package com.catinthedark.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.catinthedark.Constants;
import com.catinthedark.assets.Assets;
import com.catinthedark.level.Level;

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
    public static final float bulletOffsetX = 0;
    public static final float bulletOffsetY = 0;
    private int shootIntervalMin;
    private int shootIntervalMax;
    private boolean top;
    private boolean left;
    private boolean shooting;
    private long lastShootTime;
    private long shootInterval;
    private Level level;

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
        setWithEnemy(false);
    }

    public HouseBlock(Level level, boolean withEnemy, boolean top, boolean left, float x, float y) {
        super(x, y, blockWidth, blockHeight);
        setWithEnemy(withEnemy);
        lastShootTime = 0;
        this.x = x;
        this.y = y;
        this.top = top;
        this.left = left;
        this.level = level;
        shootIntervalMin = Constants.SHOOT_INTERVAL_MIN;
        shootIntervalMax = Constants.SHOOT_INTERVAL_MAX;
        shootInterval = MathUtils.random(shootIntervalMin, shootIntervalMax) * 1000000000L;
        shooting = false;
    }

    public void shoot() {
        if (!withEnemy) {
            return;
        }

        long fromLastShot = TimeUtils.nanoTime() - lastShootTime;
        if (fromLastShot > this.shootInterval) {
            int iWantToShoot = MathUtils.random(0, 1);
            if (iWantToShoot == 1) {
                shooting = true;
                level.levelEntities.get(Bullet.class).add(new Bullet(this.x + bulletOffsetX, this.y + bulletOffsetY,
                        -1 * Constants.BULLET_SPEED_X, 0));
                lastShootTime = TimeUtils.nanoTime();
                shootInterval = MathUtils.random(shootIntervalMin, shootIntervalMax) * 1000000000L;
            }
        } else if(fromLastShot > Constants.SHOT_ANIMATION_DURATION) {
            shooting = false;
        }
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);
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
            Animation enemyAnimation;
            if (shooting) {
                enemyAnimation = Assets.mdShoot;
            } else {
                enemyAnimation = Assets.mdIdle;
            }
            batch.draw(enemyAnimation.getKeyFrame(stateTime), this.x, this.y, HouseBlock.blockWidth, HouseBlock.blockHeight);

        }
    }
}
