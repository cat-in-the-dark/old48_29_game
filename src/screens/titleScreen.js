import { Container, Sprite } from 'pixi.js';
import * as C from '../constants.js';

export class TitleScreen {
  constructor(chain, texture, timeToLive) {
    this.chain = chain;
    this.texture = texture;
    this.timeToLive = timeToLive;
    this.infinite = timeToLive === 0;

    this.container = new Container();
    this.sprite = new Sprite(texture);
    this.sprite.width = C.SCREEN_W;
    this.sprite.height = C.SCREEN_H;
    this.container.addChild(this.sprite);

    this.timeLaps = 0;
    this.bindingsMap = {};
    this.nextHook = null;
    this.prevHook = null;
  }

  bind(key, index) { this.bindingsMap[key] = index; return this; }
  bindNext(key) { this.nextHook = key; return this; }
  bindPrev(key) { this.prevHook = key; return this; }

  keyDown(code) {
    const nextFrame = this.bindingsMap[code];
    if (nextFrame !== undefined) this.chain.gotoFrame(nextFrame);

    if (this.nextHook !== null && this.nextHook === code) {
      this.chain.next();
      return true;
    }
    if (this.prevHook !== null && this.prevHook === code) {
      this.chain.prev();
      return true;
    }
    return true;
  }

  show() {
    this.timeLaps = 0;
  }

  render(delta) {
    if (this.infinite) return;
    this.timeLaps += delta;
    if (this.timeLaps * 1000 > this.timeToLive) this.chain.next();
  }
}
