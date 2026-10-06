import * as C from '../constants.js';
import { A } from '../assets.js';
import { Entity, LAYERS } from '../entity.js';

export class OilFactory extends Entity {
  static layer = LAYERS.OilFactory;
  static WIDTH = 4;
  static HEIGHT = 4;

  constructor(world, x) {
    super(world, x, C.GROUND_LEVEL, OilFactory.WIDTH, OilFactory.HEIGHT);
    this.localStateTime = 0;
    this.destroyed = false;
  }

  render(delta) {
    super.render(delta);
    if (!this.isDestroyed()) {
      this.draw(A.oilFactoryAppearance.getKeyFrame(this.stateTime));
    } else {
      this.localStateTime += delta;
      this.draw(A.oilFactoryDisappearance.getKeyFrame(this.localStateTime));
    }
  }

  isDestroyed() { return this.destroyed; }
  setDestroyed(v) { this.destroyed = v; }
}
