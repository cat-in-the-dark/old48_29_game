import { A } from '../assets.js';
import { Entity, LAYERS } from '../entity.js';

export class Explosion extends Entity {
  static layer = LAYERS.Explosion;
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
