import { A } from '../assets.js';
import { Entity, LAYERS } from '../entity.js';

export class OilField extends Entity {
  static layer = LAYERS.OilField;
  static width = 7;
  static height = 2;

  constructor(world, x, y) {
    super(world, x, y, OilField.width, OilField.height);
    this.hasFactory = false;
  }

  render(delta) {
    super.render(delta);
    this.draw(A.oilFieldAnimation.getKeyFrame(this.stateTime));
  }

  isHasFactory() { return this.hasFactory; }
  setHasFactory(v) { this.hasFactory = v; }
}
