import { Container, Sprite } from 'pixi.js';
import * as C from '../constants.js';
import { A } from '../assets.js';
import { score } from '../gameScore.js';
import { Level } from '../level.js';
import { InterceptionManager } from '../interceptionManager.js';
import { GameHud } from '../hud.js';
import { audio } from '../audio.js';

// Port of com.catinthedark.screens.GameScreen
export class GameScreen {
  constructor(chain, keys) {
    this.chain = chain;
    this.keys = keys;

    this.container = new Container();
    this.bg = new Container();
    this.world = new Container();
    this.bg.scale = C.UNIT_SIZE;
    this.world.scale = C.UNIT_SIZE;
    this.hud = new GameHud();
    this.container.addChild(this.bg, this.world, this.hud.container);

    for (let i = 0; i < A.bgTiles.length; i++) {
      const s = new Sprite(A.bgTiles[i]);
      s.width = 4;   // tile is 128px = 4 logical units
      s.height = 20; // tile is 640px = 20 logical units
      s.position.set(i * 4, -20);
      this.bg.addChild(s);
    }

    this.camera = { x: C.VIEW_PORT_WIDTH / 2, y: C.VIEW_PORT_HEIGHT / 2 };
    this.backCamera = { x: C.VIEW_PORT_WIDTH / 2, y: C.VIEW_PORT_HEIGHT / 2 };
  }

  show() {
    // BGM starts when the game itself begins (not on the logo screen).
    // playMusic() is guarded, so re-entering the game screen is safe.
    audio.playMusic(0.7);

    this.level = new Level(this);
    this.interManager = new InterceptionManager(this.level);

    this.camera.x = C.VIEW_PORT_WIDTH / 2;
    this.camera.y = C.VIEW_PORT_HEIGHT / 2;
    this.backCamera.x = C.VIEW_PORT_WIDTH / 2;
    this.backCamera.y = C.VIEW_PORT_HEIGHT / 2;

    score.setDemocracyLevel(0);
    score.setHealth(100);
    score.resetScore();
    this.hud.setDemocracyLevel(0);
    this.hud.setHealth(100);
  }

  render(delta) {
    this.processKeys();
    this.interManager.manage();

    // background layer (back camera)
    this.bg.position.set(
      (C.VIEW_PORT_WIDTH / 2 - this.backCamera.x) * C.UNIT_SIZE,
      (this.backCamera.y + C.VIEW_PORT_HEIGHT / 2) * C.UNIT_SIZE);

    // entities layer (main camera)
    this.world.position.set(
      (C.VIEW_PORT_WIDTH / 2 - this.camera.x) * C.UNIT_SIZE,
      (this.camera.y + C.VIEW_PORT_HEIGHT / 2) * C.UNIT_SIZE);

    this.level.render(delta);

    if (score.getHealth() === 0) this.chain.next();
    if (score.getDemocracyLevel() === C.DEMOCRACY_LEVEL_MAX) this.chain.gotoFrame(8);

    this.hud.setHealth(score.getHealth());
    this.hud.setDemocracyLevel(100 / C.DEMOCRACY_LEVEL_MAX * score.getDemocracyLevel());
    this.hud.render();
  }

  keyDown(code) {
    if (code === 'Escape') this.chain.next();
    return true;
  }

  processKeys() {
    const k = this.keys;

    if (k.has('Space')) {
      this.level.placeOilFactory();
      this.level.president.move('IDLE', this.camera);
      return;
    }

    let lastState = 'IDLE';
    if (k.has('KeyS') || k.has('ArrowDown')) lastState = 'AIM_DOWN';
    if (k.has('KeyW') || k.has('ArrowUp')) lastState = 'AIM_UP';

    if (k.has('Enter')) {
      this.level.shut();
      this.level.president.move(lastState, this.camera);
      return;
    }

    this.level.president.move(lastState, this.camera);

    if (k.has('KeyD') || k.has('ArrowRight')) {
      this.level.president.direction = 'RIGHT';
      this.level.president.move('RUN', this.camera);
      if (this.needMoveCamera()) {
        this.moveMainCamera();
        this.moveBackCamera();
        this.level.president.move('RUN', this.camera);
      }
    } else if (k.has('KeyA') || k.has('ArrowLeft')) {
      this.level.president.direction = 'LEFT';
      this.level.president.move('RUN', this.camera);
    }
  }

  needMoveCamera() {
    return this.camera.x - 6 - C.MAX_PRESIDENT_DEST_FROM_BORDER <= this.level.president.x;
  }

  moveMainCamera() {
    this.camera.x += C.MAIN_CAMERA_SPEED;
  }

  moveBackCamera() {
    let p = this.backCamera.x + C.BACK_CAMERA_SPEED;
    if (p >= C.VIEW_PORT_WIDTH / 2 + 2 * C.VIEW_PORT_WIDTH) p = C.VIEW_PORT_WIDTH / 2;
    this.backCamera.x = p;
  }
}
