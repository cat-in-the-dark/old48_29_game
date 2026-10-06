import { A } from '../assets.js';
import { Entity } from '../entity.js';

// Port of com.catinthedark.entities.Hint
export class Hint extends Entity {
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
