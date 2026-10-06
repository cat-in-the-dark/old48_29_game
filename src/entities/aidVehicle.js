import { LAYERS } from '../entity.js';
import { Vehicle } from './vehicle.js';

export class AidVehicle extends Vehicle {
  static layer = LAYERS.AidVehicle;
  static riding = 'aidVehicleRiding';
  static exploded = 'aidVehicleExploded';
}
