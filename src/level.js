import * as C from './constants.js';
import { score } from './gameScore.js';
import { intersectRectangles } from './entity.js';
import { President } from './entities/president.js';
import { OilFactory } from './entities/oilFactory.js';
import { Explosion } from './entities/explosion.js';
import { LevelGenerator } from './levelGenerator.js';

export class Level {
  constructor(gameScreen) {
    this.gameScreen = gameScreen;
    this.world = gameScreen.world;
    this.time = 0;
    this.president = new President(this.world, 0, C.GROUND_LEVEL);
    this.generator = new LevelGenerator();

    this.levelEntities = {
      Hint: [], House: [], Rocket: [], Bullet: [],
      OilFactory: [], OilField: [], TntVehicle: [], AidVehicle: [], Explosion: [],
    };
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
    this.time += delta;
    this.generator.generateLevel(this);

    for (const [cls, entities] of Object.entries(this.levelEntities)) {
      if (cls === 'Rocket') {
        for (const rocket of entities) {
          if (!this.isBulletInViewPort(rocket)) {
            rocket.markDeleted();
            this.bum(rocket.x, rocket.y - 0.2);
          } else {
            rocket.render(delta);
          }
        }
      } else if (cls === 'OilField') {
        this.renderEntities(entities, delta);
        this.president.render(delta);
      } else {
        this.renderEntities(entities, delta);
      }
    }

    for (const list of Object.values(this.levelEntities)) {
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

  destroy() {
    for (const entities of Object.values(this.levelEntities)) {
      for (const entity of entities) entity.destroy();
      entities.length = 0;
    }
    this.president.destroy();
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
