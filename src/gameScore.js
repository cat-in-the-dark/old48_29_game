import * as C from './constants.js';

// Port of com.catinthedark.GameScore
export class GameScore {
  constructor() {
    this.democracyLevel = 0;
    this.health = C.HEALTH_MAX;
    this.score = 0;
  }

  getHealth() { return this.health; }
  setHealth(v) {
    this.health = Math.max(0, Math.min(100, v));
  }

  getDemocracyLevel() { return this.democracyLevel; }
  setDemocracyLevel(v) { this.democracyLevel = v; }
  incDemocracyLevel() {
    if (this.democracyLevel < C.DEMOCRACY_LEVEL_MAX) this.democracyLevel++;
  }
  decDemocracyLevel() {
    if (this.democracyLevel > 0) this.democracyLevel--;
  }

  priceEnemy() { this.score += C.ENEMY_PRICE; }
  priceTNTVehicle() { this.score += C.TNT_VEHICLE_PRICE; }
  priceHouse() { this.score += C.HOUSE_PRICE; }
  priceAidVehicle() { this.score += C.AID_VEHICLE_PRICE; }

  getScore() { return this.score; }
  resetScore() { this.score = 0; }
}

// Singleton, as in the original GameScore.getInstance()
export const score = new GameScore();
