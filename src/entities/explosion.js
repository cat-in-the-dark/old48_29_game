import { A } from '../assets.js';
import { Entity } from '../entity.js';

// Port of com.catinthedark.entities.Explosion
export class Explosion extends Entity {
  static HEIGHT = 2;
  static WIDTH = 2;

  constructor(world, x, y) {
    super(world, x, y, Explosion.WIDTH, Explosion.HEIGHT);
  }

  render(delta) {
    super.render(delta);
    this.draw(A.explosionAnimation.getKeyFrame(this.stateTime));
  }
}
