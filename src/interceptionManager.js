import * as C from './constants.js';
import { score } from './gameScore.js';
import { audio } from './audio.js';
import { intersectRectangles } from './entity.js';

// Port of com.catinthedark.InterceptionManager
export class InterceptionManager {
  constructor(level) {
    this.level = level;
  }

  entityBothDestroy(list1, list2) {
    for (const e1 of list1) {
      for (const e2 of list2) {
        if (e1.isMarkedToDelete() || e2.isMarkedToDelete()) continue;
        if (intersectRectangles(e1.bounds, e2.bounds)) {
          audio.play('smallExplosion');
          e1.markDeleted();
          e2.markDeleted();
        }
      }
    }
  }

  // Rockets deactivate terrorist bullets
  deactivateTerroristBullets() {
    this.entityBothDestroy(this.level.levelEntities.Rocket, this.level.levelEntities.Bullet);
  }

  destroyHouseBlock() {
    for (const rocket of this.level.levelEntities.Rocket) {
      for (const house of this.level.levelEntities.House) {
        for (const block of house.houseBlocks) {
          if (rocket.isMarkedToDelete() || block.isDestroyed()) continue;
          if (intersectRectangles(rocket.bounds, block.bounds)) {
            audio.play('explosion');
            if (block.isWithEnemy()) score.priceEnemy();
            score.priceHouse();
            rocket.markDeleted();
            block.setDestroyed(true);
            this.level.bum(block.x, block.y);
          }
        }
      }
    }
  }

  damagePresident() {
    const pres = this.level.president;
    for (const bullet of this.level.levelEntities.Bullet) {
      if (bullet.isMarkedToDelete()) continue;
      for (const bound of pres.getBounds()) {
        if (intersectRectangles(bound, bullet.bounds)) {
          pres.doDamage(C.TERR_DAMAGE);
          bullet.markDeleted();
          break;
        }
      }
    }
  }

  destroyKamaz() {
    for (const rocket of this.level.levelEntities.Rocket) {
      for (const vehicle of this.level.levelEntities.TntVehicle) {
        if (rocket.isMarkedToDelete() || vehicle.isDestroyed()) continue;
        if (intersectRectangles(rocket.bounds, vehicle.bounds)) {
          rocket.markDeleted();
          vehicle.setDestroyed(true);
          audio.play('kamazExplosion');
          score.priceTNTVehicle();
          this.level.bum(vehicle.x, vehicle.y);
        }
      }
    }
  }

  destroyAid() {
    for (const rocket of this.level.levelEntities.Rocket) {
      for (const vehicle of this.level.levelEntities.AidVehicle) {
        if (rocket.isMarkedToDelete() || vehicle.isDestroyed()) continue;
        if (intersectRectangles(rocket.bounds, vehicle.bounds)) {
          rocket.markDeleted();
          vehicle.setDestroyed(true);
          audio.play('kamazExplosion');
          score.priceAidVehicle();
          score.setHealth(score.getHealth() + C.HEALTH_REGEN);
          this.level.bum(vehicle.x, vehicle.y);
        }
      }
    }
  }

  kamazDestroysFactory() {
    for (const vehicle of this.level.levelEntities.TntVehicle) {
      if (vehicle.isDestroyed()) continue;
      for (const factory of this.level.levelEntities.OilFactory) {
        if (factory.isDestroyed()) continue;
        if (intersectRectangles(vehicle.bounds, factory.bounds)) {
          vehicle.setDestroyed(true);
          factory.setDestroyed(true);
          this.level.bum((factory.x + vehicle.x) / 2, (factory.y + vehicle.y) / 2);
          score.decDemocracyLevel();
          audio.play('kamazExplosion');
          break;
        }
      }
    }
  }

  manage() {
    this.deactivateTerroristBullets();
    this.destroyHouseBlock();
    this.damagePresident();
    this.destroyKamaz();
    this.destroyAid();
    this.kamazDestroysFactory();
  }
}
