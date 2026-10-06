import { Sprite } from 'pixi.js';
export const LAYERS = {
  Hint: 0, House: 1, HouseBlock: 1, Rocket: 2, Bullet: 3,
  OilFactory: 4, OilField: 5, President: 6, TntVehicle: 7,
  AidVehicle: 8, Explosion: 9,
};

// Logical coordinates are in tiles, Y-up (like libgdx). The world container
// has scale 32; sprites are positioned at (x, -(y + height)) so that the
// sprite's top-left matches libgdx's bottom-left draw anchor.
export class Entity {
  static layer = LAYERS.Hint;
  constructor(world, x, y, width, height) {
    this.world = world;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.bounds = { x, y, width, height };
    this.deleted = false;
    this.stateTime = 0;
    this.sprite = new Sprite({ visible: false, zIndex: this.constructor.layer });
    world.addChild(this.sprite);
  }

  render(delta) {
    this.stateTime += delta;
  }

  markDeleted() { this.deleted = true; }
  isMarkedToDelete() { return this.deleted; }

  // frame: { tex, flipX, flipY } or a raw Texture
  drawAt(f, bx, by) {
    const fr = f.tex ? f : { tex: f, flipX: false, flipY: false };
    this.sprite.texture = fr.tex;
    this.sprite.visible = true;
    // Set the scale sign explicitly: Pixi's `width` setter preserves the
    // existing sign, so `scale.x *= -1` every frame would toggle it (flicker).
    this.sprite.scale.set(
      this.width / fr.tex.width * (fr.flipX ? -1 : 1),
      this.height / fr.tex.height * (fr.flipY ? -1 : 1));
    const px = bx + (fr.flipX ? this.width : 0);
    const py = -(by + this.height) + (fr.flipY ? this.height : 0);
    this.sprite.position.set(px, py);
  }

  draw(f) { this.drawAt(f, this.x, this.y); }

  destroy() { this.sprite.destroy(); }
}

export function intersectRectangles(a, b) {
  return a.x < b.x + b.width && b.x < a.x + a.width &&
         a.y < b.y + b.height && b.y < a.y + a.height;
}
