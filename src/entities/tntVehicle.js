import { A } from '../assets.js';
import { Entity } from '../entity.js';

// Port of com.catinthedark.entities.TntVehicle
export class TntVehicle extends Entity {
  static width = 4;
  static height = 2;

  constructor(world, x, y, speedX, speedY) {
    super(world, x, y, TntVehicle.width, TntVehicle.height);
    this.destroyed = false;
    this.destroyAnimationPlayed = false;
    this.speedX = speedX;
    this.speedY = speedY;
    this.accelerationY = 0;
  }

  move() {
    this.x += this.speedX;
    this.y += this.speedY;
    this.speedY += this.accelerationY;
    this.bounds.x = this.x;
    this.bounds.y = this.y;
  }

  render(delta) {
    super.render(delta);
    this.move();
    if (!this.isDestroyed()) {
      this.stateTime += delta;
      this.draw(A.tntVehicleRiding.getKeyFrame(this.stateTime));
    } else {
      if (!this.destroyAnimationPlayed) {
        this.destroyAnimationPlayed = true;
        this.speedY = 0.1;
        this.speedX = 0.1;
        this.accelerationY = -0.01;
      }
      this.draw(A.tntVehicleExploded);
    }
  }

  isDestroyed() { return this.destroyed; }
  setDestroyed(v) { this.destroyed = v; }
}
