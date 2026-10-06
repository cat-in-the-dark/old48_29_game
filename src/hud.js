import { Container, Graphics, Sprite, Text } from 'pixi.js';
import { A } from './assets.js';
import { score } from './gameScore.js';

const RED = 0xff0000;
const BLACK = 0x000000;
const GREEN = 0x0ab53d; // Color(0.04, 0.71, 0.24)
const FONT = { fontFamily: 'Impact, "Arial Narrow Bold", sans-serif', fontSize: 25, fill: RED };

export class GameHud {
  constructor() {
    this.democracyLevel = 0;
    this.health = 100;

    this.container = new Container();

    this.fillG = new Graphics();          // black democracy fill
    this.barBg = new Sprite(A.democracy); // bar frame image
    this.barBg.width = 850;
    this.barBg.height = 30;
    this.barBg.position.set(130, 25);
    this.healthG = new Graphics();        // health background + fill

    this.demText = new Text({ text: 'Democracy:', style: FONT });
    this.healthText = new Text({ text: 'Health:', style: FONT });
    this.scoreText = new Text({ text: 'Score:  0', style: FONT });

    this.demText.position.set(10, 22);
    this.healthText.position.set(10, 62);
    this.scoreText.position.set(710, 62);

    this.container.addChild(this.fillG, this.barBg, this.healthG,
      this.demText, this.healthText, this.scoreText);
  }

  setDemocracyLevel(v) { this.democracyLevel = v; }
  setHealth(v) { this.health = v <= 0 ? 0 : v; }

  render() {
    // democracy bar: black fill inside the frame image
    this.fillG.clear();
    const w = this.democracyLevel / 100 * 850 - 6;
    if (w > 0) this.fillG.rect(133, 27, w, 26).fill(BLACK);

    // health bar
    this.healthG.clear();
    this.healthG.rect(130, 65, 200, 30).fill(GREEN);
    const hw = this.health / 100 * 200 - 4;
    if (hw > 0) this.healthG.rect(132, 67, hw, 26).fill(RED);

    this.scoreText.text = 'Score:  ' + score.getScore();
  }
}
