import { A } from '../assets.js';
import { Entity } from '../entity.js';

export class Vehicle extends Entity {
  static width = 4;
  static height = 2;

  constructor(world, x, y, speedX, speedY) {
    super(world, x, y, Vehicle.width, Vehicle.height);
    this.destroyed = false;
    this.destroyAnimationPlayed = false;
    this.speedX = speedX;
    this.speedY = speedY;
    this.accelerationY = 0;
  }

  render(delta) {
    super.render(delta);
    this.x += this.speedX;
    this.y += this.speedY;
    this.speedY += this.accelerationY;
    this.bounds.x = this.x;
    this.bounds.y = this.y;

    if (!this.destroyed) {
      this.stateTime += delta;
      this.draw(A[this.constructor.riding].getKeyFrame(this.stateTime));
    } else {
      if (!this.destroyAnimationPlayed) {
        this.destroyAnimationPlayed = true;
        this.speedY = this.speedX = 0.1;
        this.accelerationY = -0.01;
      }
      this.draw(A[this.constructor.exploded]);
    }
  }

  isDestroyed() { return this.destroyed; }
  setDestroyed(v) { this.destroyed = v; }
}
