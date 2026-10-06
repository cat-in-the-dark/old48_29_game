import { Sprite } from 'pixi.js';
import * as C from '../constants.js';
import { A } from '../assets.js';
import { score } from '../gameScore.js';
import { audio } from '../audio.js';
import { Rocket } from './rocket.js';
import { LAYERS } from '../entity.js';

export const STATE = { IDLE: 'IDLE', RUN: 'RUN', AIM_UP: 'AIM_UP', AIM_DOWN: 'AIM_DOWN' };
export const DIR = { RIGHT: 'RIGHT', LEFT: 'LEFT' };

export class President {
  static WIDTH = 6;
  static HEIGHT = 9;

  constructor(world, x, y) {
    this.world = world;
    this.direction = DIR.RIGHT;
    this.state = STATE.IDLE;
    this.x = x;
    this.y = y;

    this.acceleration = { x: 0, y: 0 };

    const W = President.WIDTH, H = President.HEIGHT;
    this.bounds = [
      { x, y, width: W * 3 / 4, height: H * 0.32 },
      { x, y: y + H * 0.32, width: W * 0.58, height: H * 0.19 },
      { x, y: y + H * (0.32 + 0.19), width: W * 0.9, height: H * 0.22 },
      { x, y: y + H * (0.32 + 0.19 + 0.22), width: W * 0.54, height: H * 0.2 },
    ];

    this.shutDelay = 0.5;
    this.lastShutTime = 0;
    this.stateTime = 0;

    this.sprite = new Sprite({ visible: false, zIndex: LAYERS.President });
    world.addChild(this.sprite);
  }

  render(delta) {
    this.stateTime += delta;
    this.lastShutTime += delta;
    this.tryMove();

    let anim;
    switch (this.state) {
      case STATE.IDLE: anim = A.presidentAimMiddle; break;
      case STATE.AIM_UP: anim = A.presidentAimUp; break;
      case STATE.AIM_DOWN: anim = A.presidentAimDown; break;
      case STATE.RUN:
        anim = this.direction === DIR.RIGHT ? A.presidentRunRight : A.presidentRunLeft;
        break;
      default: anim = A.presidentAimMiddle;
    }
    const f = anim.getKeyFrame(this.stateTime);
    this.sprite.texture = f.tex;
    this.sprite.visible = true;
    this.sprite.width = President.WIDTH;
    this.sprite.height = President.HEIGHT;
    this.sprite.position.set(this.x, -(this.y - 0.5 + President.HEIGHT));
  }

  shut() {
    if (this.lastShutTime > this.shutDelay) {
      this.lastShutTime = 0;
      const rx = this.x + 3 * President.WIDTH / 5;
      let ry;
      switch (this.state) {
        case STATE.AIM_DOWN: ry = this.y + President.HEIGHT / 2 + 0.1; break;
        case STATE.AIM_UP: ry = this.y + President.HEIGHT / 2 + 1.2; break;
        default: ry = this.y + President.HEIGHT / 2 + 0.8;
      }
      return new Rocket(this.world, rx, ry, this);
    }
    return null;
  }

  move(state, camera) {
    if (state !== STATE.RUN) {
      this.state = state;
      this.acceleration.x = 0;
    } else {
      this.state = STATE.RUN;
      this.acceleration.x = this.direction === DIR.RIGHT
        ? C.PRESIDENT_SPEED : -C.PRESIDENT_SPEED;
    }
    if (!this.canMove(camera)) this.acceleration.x = 0;
  }

  tryMove() {
    this.x += this.acceleration.x;
    for (const b of this.bounds) b.x = this.x;
  }

  canMove(camera) {
    const nextX = this.x + this.acceleration.x;
    return !(camera.x - President.WIDTH - C.MAX_PRESIDENT_DEST_FROM_BORDER < nextX ||
             nextX < camera.x - C.VIEW_PORT_WIDTH / 2);
  }

  doDamage(amount) {
    audio.play(Math.random() < 0.5 ? 'oh1' : 'oh2', 0.7);
    score.setHealth(score.getHealth() - amount);
  }

  getBounds() { return this.bounds; }

  destroy() { this.sprite.destroy(); }
}
