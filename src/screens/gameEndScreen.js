import { Text } from 'pixi.js';
import * as C from '../constants.js';
import { score } from '../gameScore.js';
import { TitleScreen } from './titleScreen.js';

// Port of com.catinthedark.screens.GameEndScreen
export class GameEndScreen extends TitleScreen {
  constructor(chain, texture, timeToLive) {
    super(chain, texture, timeToLive);
    this.scoreText = new Text({
      text: 'Score:' + score.getScore(),
      style: {
        fontFamily: 'Impact, "Arial Narrow Bold", sans-serif',
        fontSize: 50,
        fill: 0xffffff,
      },
    });
    this.scoreText.position.set(C.SCREEN_W / 2 - 70, C.SCREEN_H / 2 - 100 + 30);
    this.container.addChild(this.scoreText);
  }

  render(delta) {
    super.render(delta);
    this.scoreText.text = 'Score:' + score.getScore();
  }
}
