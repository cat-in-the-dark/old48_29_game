import * as C from '../constants.js';
import { score } from '../gameScore.js';
import { Entity, LAYERS } from '../entity.js';
import { HouseBlock } from './houseBlock.js';

export class House extends Entity {
  static layer = LAYERS.House;
  constructor(level, x, y, widthInBlocks, heightInBlocks) {
    super(level.world, x, y, widthInBlocks * HouseBlock.blockWidth, heightInBlocks * HouseBlock.blockHeight);
    this.sprite.visible = false; // house itself is drawn via its blocks
    this.widthInBlocks = widthInBlocks;
    this.heightInBlocks = heightInBlocks;
    this.level = level;
    this.houseBlocks = [];
    this.buildHouse();
  }

  buildHouse() {
    const blocksCount = this.widthInBlocks * this.heightInBlocks;
    const enemiesInHouseMax = Math.floor(score.getDemocracyLevel() * blocksCount / C.DEMOCRACY_LEVEL_MAX);
    const enemiesInHouse = Math.floor(Math.random() * (enemiesInHouseMax + 1));

    const vacant = [];
    for (let i = 0; i < blocksCount; i++) vacant.push(i);
    const roomWithEnemy = new Array(blocksCount).fill(false);

    for (let i = 0; i < enemiesInHouse; i++) {
      const roomNumber = Math.floor(Math.random() * vacant.length);
      roomWithEnemy[vacant[roomNumber]] = true;
      vacant.splice(roomNumber, 1);
    }

    for (let i = 0; i < this.widthInBlocks; i++) {
      for (let j = 0; j < this.heightInBlocks; j++) {
        const blockX = i * HouseBlock.blockWidth + this.x;
        const blockY = j * HouseBlock.blockHeight + this.y;
        const left = i === 0;
        const top = j === this.heightInBlocks - 1;
        this.houseBlocks.push(new HouseBlock(this.level, roomWithEnemy[i + j * this.widthInBlocks], top, left, blockX, blockY));
      }
    }
  }

  shoot() {
    for (const block of this.houseBlocks) block.shoot();
  }

  render(delta) {
    super.render(delta);
    if (!this.level.isRighterThanViewPort(this)) this.shoot();
    for (const block of this.houseBlocks) block.render(delta);
  }

  destroy() {
    for (const block of this.houseBlocks) block.destroy();
    super.destroy();
  }
}
