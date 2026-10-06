import * as C from './constants.js';
import { score } from './gameScore.js';
import { intersectRectangles } from './entity.js';
import { President } from './entities/president.js';
import { Hint } from './entities/hint.js';
import { House } from './entities/house.js';
import { Rocket } from './entities/rocket.js';
import { Bullet } from './entities/bullet.js';
import { OilFactory } from './entities/oilFactory.js';
import { OilField } from './entities/oilField.js';
import { TntVehicle } from './entities/tntVehicle.js';
import { AidVehicle } from './entities/aidVehicle.js';
import { Explosion } from './entities/explosion.js';
import { LevelGenerator } from './levelGenerator.js';

// Port of com.catinthedark.level.Level
export class Level {
  constructor(gameScreen) {
    this.gameScreen = gameScreen;
    this.world = gameScreen.world;
    this.president = new President(this.world, 0, C.GROUND_LEVEL);
    this.generator = new LevelGenerator();

    this.levelEntities = {
      Hint: [], House: [], Rocket: [], Bullet: [],
      OilFactory: [], OilField: [], TntVehicle: [], AidVehicle: [], Explosion: [],
    };
    this.renderOrder = [
      'Hint', 'House', 'Rocket', 'Bullet', 'OilFactory', 'OilField',
      'TntVehicle', 'AidVehicle', 'Explosion',
    ];
  }

  isRighterThanViewPort(entity) {
    const cam = this.gameScreen.camera;
    return (entity.x - cam.x) > C.VIEW_PORT_WIDTH / 2;
  }

  isInViewPort(entity) {
    const cam = this.gameScreen.camera;
    return (cam.x - (entity.x + entity.width)) < C.VIEW_PORT_WIDTH / 1.5;
  }

  isBulletInViewPort(entity) {
    const cam = this.gameScreen.camera;
    return (cam.x - entity.x) < C.VIEW_PORT_WIDTH / 2 &&
           (entity.x - cam.x) < C.VIEW_PORT_WIDTH * 1.1 &&
           entity.y < C.VIEW_PORT_HEIGHT && entity.y > C.GROUND_LEVEL;
  }

  render(delta) {
    this.generator.generateLevel(this);

    for (const cls of this.renderOrder) {
      if (cls === 'Rocket') {
        for (const rocket of this.levelEntities.Rocket) {
          if (!this.isBulletInViewPort(rocket)) {
            rocket.markDeleted();
            this.bum(rocket.x, rocket.y - 0.2);
          } else {
            rocket.render(delta);
          }
        }
      } else if (cls === 'OilField') {
        this.renderEntities(this.levelEntities.OilField, delta);
        this.president.render(delta);
      } else {
        this.renderEntities(this.levelEntities[cls], delta);
      }
    }

    for (const cls in this.levelEntities) {
      const list = this.levelEntities[cls];
      for (let i = list.length - 1; i >= 0; i--) {
        if (list[i].isMarkedToDelete()) {
          list[i].destroy();
          list.splice(i, 1);
        }
      }
    }
  }

  renderEntities(entities, delta) {
    for (const entity of entities) {
      if (!this.isInViewPort(entity)) {
        entity.markDeleted();
      } else {
        entity.render(delta);
      }
    }
  }

  shut() {
    const rocket = this.president.shut();
    if (rocket) this.levelEntities.Rocket.push(rocket);
  }

  placeOilFactory() {
    const oilRectWidth = 2;
    const factoryRect = {
      x: this.president.x + President.WIDTH / 2 - oilRectWidth / 2,
      y: this.president.y - 4,
      width: oilRectWidth,
      height: 1,
    };

    for (const field of this.levelEntities.OilField) {
      if (field.isHasFactory()) continue;
      if (intersectRectangles(field.bounds, factoryRect)) {
        this.levelEntities.OilFactory.push(
          new OilFactory(this.world, field.x + field.width / 2 - OilFactory.WIDTH / 2));
        field.setHasFactory(true);
        score.incDemocracyLevel();
        break;
      }
    }
  }

  bum(x, y) {
    this.levelEntities.Explosion.push(new Explosion(this.world, x, y));
  }
}
