import { A } from '../assets.js';
import { Entity, LAYERS } from '../entity.js';

export class Bullet extends Entity {
  static layer = LAYERS.Bullet;
  static bulletWidth = 0.5;
  static bulletHeight = 0.5;

  constructor(world, x, y, speedX, speedY) {
    super(world, x, y, Bullet.bulletWidth, Bullet.bulletHeight);
    this.speedX = speedX;
    this.speedY = speedY;
  }

  move() {
    this.x += this.speedX;
    this.y += this.speedY;
    this.bounds.x = this.x;
    this.bounds.y = this.y;
  }

  render(delta) {
    this.move();
    this.drawAt(A.bullet, this.x, this.y + this.height);
  }
}
