import { A } from '../assets.js';
import { Entity, LAYERS } from '../entity.js';

export class Hint extends Entity {
  static layer = LAYERS.Hint;
  static WIDTH = 10;
  static HEIGHT = 4;

  constructor(world, x, y) {
    super(world, x, y, Hint.WIDTH, Hint.HEIGHT);
  }

  render(delta) {
    super.render(delta);
    this.draw(A.oilHint);
  }
}
