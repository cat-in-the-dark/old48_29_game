import { Sprite } from 'pixi.js';
import * as C from '../constants.js';
import { A } from '../assets.js';
import { Entity, LAYERS } from '../entity.js';
import { Bullet } from './bullet.js';

export class HouseBlock extends Entity {
  static layer = LAYERS.HouseBlock;
  static blockWidth = 2;
  static blockHeight = 2;

  constructor(level, withEnemy, top, left, x, y) {
    super(level.world, x, y, HouseBlock.blockWidth, HouseBlock.blockHeight);
    this.level = level;
    this.withEnemy = withEnemy;
    this.destroyed = false;
    this.top = top;
    this.left = left;
    this.shooting = false;
    this.lastShootTime = -Infinity;
    this.shootInterval = randInterval(C.SHOOT_INTERVAL_MIN, C.SHOOT_INTERVAL_MAX);

    this.enemySprite = new Sprite({ visible: false, zIndex: this.sprite.zIndex });
    this.world.addChild(this.enemySprite);
  }

  isWithEnemy() { return this.withEnemy; }
  setWithEnemy(v) { this.withEnemy = v; }
  isDestroyed() { return this.destroyed; }
  setDestroyed(v) {
    this.destroyed = v;
    this.setWithEnemy(false);
  }

  shoot() {
    if (!this.withEnemy) return;
    const now = this.level.time;
    const fromLastShot = now - this.lastShootTime;
    if (fromLastShot > this.shootInterval) {
      if (Math.random() < 0.5) {
        this.shooting = true;
        this.level.levelEntities.Bullet.push(
          new Bullet(this.world, this.x, this.y, -C.BULLET_SPEED_X, 0));
        this.lastShootTime = now;
        this.shootInterval = randInterval(C.SHOOT_INTERVAL_MIN, C.SHOOT_INTERVAL_MAX);
      }
    } else if (fromLastShot > C.SHOT_ANIMATION_DURATION) {
      this.shooting = false;
    }
  }

  render(delta) {
    super.render(delta);

    let region;
    if (this.destroyed) {
      region = this.left ? A.leftBrocken : A.rightBrocken;
    } else if (this.top) {
      region = this.left ? A.leftTopUnbrocken : A.rightToppUnbrocken;
    } else {
      region = this.left ? A.leftUnbrocken : A.rightUnbrocken;
    }
    this.draw(region);

    this.enemySprite.visible = this.withEnemy;
    if (this.withEnemy) {
      const anim = this.shooting ? A.mdShoot : A.mdIdle;
      const f = anim.getKeyFrame(this.stateTime);
      this.enemySprite.texture = f.tex;
      this.enemySprite.width = HouseBlock.blockWidth;
      this.enemySprite.height = HouseBlock.blockHeight;
      this.enemySprite.position.set(this.x, -(this.y + HouseBlock.blockHeight));
    }
  }

  destroy() {
    super.destroy();
    this.enemySprite.destroy();
  }
}

function randInterval(min, max) {
  return (min + Math.floor(Math.random() * (max - min + 1)));
}
