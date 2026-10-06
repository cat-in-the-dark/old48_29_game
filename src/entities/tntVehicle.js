import { LAYERS } from '../entity.js';
import { Vehicle } from './vehicle.js';

export class TntVehicle extends Vehicle {
  static layer = LAYERS.TntVehicle;
  static riding = 'tntVehicleRiding';
  static exploded = 'tntVehicleExploded';
}
