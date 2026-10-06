import { A } from '../assets.js';
import { Entity, LAYERS } from '../entity.js';
import { STATE } from './president.js';

export class Rocket extends Entity {
  static layer = LAYERS.Rocket;
  static rocketWidth = 2;
  static rocketHeight = 1;

  constructor(world, x, y, creator) {
    super(world, x, y, Rocket.rocketWidth, Rocket.rocketHeight);
    const m = 1.3;
    const maxMiddle = { x: 0.5 * m, y: -0.015 * m };
    const maxUp = { x: 0.5 * m, y: 0.08 * m };
    const maxDown = { x: 0.5 * m, y: -0.2 * m };
    this.acceleration = { ...maxMiddle };
    switch (creator.state) {
      case STATE.AIM_UP: this.acceleration = { ...maxUp }; break;
      case STATE.AIM_DOWN: this.acceleration = { ...maxDown }; break;
      case STATE.IDLE: this.acceleration = { ...maxMiddle }; break;
    }
  }

  render(delta) {
    super.render(delta);
    this.x += this.acceleration.x;
    this.y += this.acceleration.y;
    this.bounds.x = this.x;
    this.bounds.y = this.y;
    this.draw(A.rocket);
  }
}
