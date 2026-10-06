import * as C from './constants.js';
import { score } from './gameScore.js';
import { Hint } from './entities/hint.js';
import { House } from './entities/house.js';
import { OilField } from './entities/oilField.js';
import { TntVehicle } from './entities/tntVehicle.js';
import { AidVehicle } from './entities/aidVehicle.js';

// Port of com.catinthedark.level.LevelGenerator
function randInt(min, max) {
  return min + Math.floor(Math.random() * (max - min + 1));
}

// MathUtils.random(float, float) in the original
function randFloat(min, max) {
  return min + Math.random() * (max - min);
}

export class LevelGenerator {
  constructor() {
    const now = performance.now() / 1000;
    this.lastTntVehicle = now;
    this.lastAidVehicle = now;
    this.tntVehicleInterval = C.TNT_VEHICLE_INTERVAL_MAX;
    this.aidVehicleInterval = C.AID_VEHICLE_INTERVAL_MAX;
  }

  generateLevel(level) {
    const d = score.getDemocracyLevel();
    const minHouseDistance = (2 - d / C.DEMOCRACY_LEVEL_MAX) * C.BUILDING_DISTANCE_MIN;
    const maxHouseDistance = (3 - d / C.DEMOCRACY_LEVEL_MAX) * C.BUILDING_DISTANCE_MIN;

    this.generateHouse(level, minHouseDistance, maxHouseDistance);
    this.generateOilField(level);
    this.generateTntVehicle(level, d);
    this.generateAidVehicle(level, d);
  }

  generateAidVehicle(level, d) {
    const max = C.AID_VEHICLE_INTERVAL_MAX + Math.floor((C.DEMOCRACY_LEVEL_MAX - d) / 2);
    const min = C.AID_VEHICLE_INTERVAL_MIN + Math.floor((C.DEMOCRACY_LEVEL_MAX - d) / 2);

    const cam = level.gameScreen.camera;
    const leftEdge = cam.x - C.VIEW_PORT_WIDTH / 2;
    const now = performance.now() / 1000;

    if (now - this.aidVehicleInterval > this.lastAidVehicle) {
      level.levelEntities.AidVehicle.push(
        new AidVehicle(level.world, leftEdge - AidVehicle.width, C.GROUND_LEVEL, C.AID_VEHICLE_SPEED_X, 0));
      this.lastAidVehicle = now;
      this.aidVehicleInterval = randInt(min, max);
    }
  }

  generateTntVehicle(level, d) {
    const max = C.TNT_VEHICLE_INTERVAL_MAX + Math.floor((C.DEMOCRACY_LEVEL_MAX - d) / 2);
    const min = C.TNT_VEHICLE_INTERVAL_MIN + Math.floor((C.DEMOCRACY_LEVEL_MAX - d) / 2);

    const cam = level.gameScreen.camera;
    const rightEdge = cam.x + C.VIEW_PORT_WIDTH / 2;
    const now = performance.now() / 1000;

    if (now - this.tntVehicleInterval > this.lastTntVehicle) {
      level.levelEntities.TntVehicle.push(
        new TntVehicle(level.world, rightEdge, C.GROUND_LEVEL, -C.TNT_VEHICLE_SPEED_X, 0));
      this.lastTntVehicle = now;
      this.tntVehicleInterval = randInt(min, max);
    }
  }

  generateOilField(level) {
    let maxFieldX = 0;
    const cam = level.gameScreen.camera;
    const rightEdge = cam.x + C.VIEW_PORT_WIDTH / 2;

    for (const field of level.levelEntities.OilField) {
      if (field.x > maxFieldX) maxFieldX = field.x;
    }

    if (rightEdge - maxFieldX > C.OIL_FIELD_DISTANCE_MIN) {
      const newFieldX = rightEdge + randFloat(C.OIL_FIELD_DISTANCE_MIN, C.OIL_FIELD_DISTANCE_MAX);
      level.levelEntities.OilField.push(new OilField(level.world, newFieldX, C.OIL_FIELD_Y_POS));
      if (score.getDemocracyLevel() === 0) {
        level.levelEntities.Hint.push(new Hint(level.world, newFieldX - Hint.WIDTH / 2, C.HINT_LEVEL));
      }
    }
  }

  generateHouse(level, minHouseDistance, maxHouseDistance) {
    let maxHouseX = 0;
    const cam = level.gameScreen.camera;
    const rightEdge = cam.x + C.VIEW_PORT_WIDTH / 2;

    for (const house of level.levelEntities.House) {
      if (house.x > maxHouseX) maxHouseX = house.x;
    }

    if (rightEdge - maxHouseX > minHouseDistance) {
      const newHouseX = rightEdge + randFloat(minHouseDistance, maxHouseDistance);
      const heightInBlocks = randInt(2, 4);
      const widthInBlocks = 2;
      level.levelEntities.House.push(new House(level, newHouseX, C.GROUND_LEVEL, widthInBlocks, heightInBlocks));
    }
  }
}
