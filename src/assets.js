import { Assets, Texture } from 'pixi.js';
import * as C from './constants.js';

export const A = {};

// A "frame" is { tex, flipX, flipY } — flips are applied at render time via
// sprite scale (Pixi v8 texture.source is not a drawable DOM element).
class Anim {
  constructor(frames, speed, mode = 'loop') {
    this.frames = frames;
    this.speed = speed;
    this.mode = mode;
  }
  getKeyFrame(t) {
    const i = Math.floor(t / this.speed);
    if (this.mode === 'loop') return this.frames[i % this.frames.length];
    return this.frames[Math.min(i, this.frames.length - 1)];
  }
}

function frame(tex, flipX = false, flipY = false) {
  return { tex, flipX, flipY };
}

function region(tex, row, col, cols, rows) {
  const cw = tex.source.width / cols;
  const ch = tex.source.height / rows;
  return frame(new Texture({
    source: tex.source,
    frame: { x: col * cw, y: row * ch, width: cw, height: ch },
  }));
}

const TEXTURES = {
  democracy: 'assets/texture/democracy_bar.png',
  logo: 'assets/texture/logo.png',
  gameStart: 'assets/texture/game_start_page.png',
  tutorial1: 'assets/texture/tutorial1_en.png',
  tutorial2: 'assets/texture/tutorial2_en.png',
  tutorial3: 'assets/texture/tutorial3_en.png',
  tutorial4: 'assets/texture/tutorial4_en.png',
  gameOver: 'assets/texture/game_over.png',
  gameWin: 'assets/texture/win_screen.png',
  enemyBlock: 'assets/texture/houses_shahids.png',
  presidentSheet: 'assets/texture/president_going.png',
  oilFactory: 'assets/texture/vyshka.png',
  oilField: 'assets/texture/oil.png',
  bullet: 'assets/texture/bullet.png',
  tntVehicle: 'assets/texture/kamaz_TERRORISM.png',
  aidVehicle: 'assets/texture/kamaz_AID.png',
  explosion: 'assets/texture/explosion.png',
  rocket: 'assets/texture/snaryad.png',
  oilHint: 'assets/texture/oil_hint_en.png',
  background: 'assets/texture/background.png',
};

// Minimal TMX loader for area02.tmx (single layer, base64 gids).
async function loadBackgroundMap() {
  const res = await fetch('assets/area02.tmx');
  const xml = new DOMParser().parseFromString(await res.text(), 'application/xml');
  const tileset = xml.querySelector('tileset');
  const image = tileset.querySelector('image');
  const layer = xml.querySelector('layer');
  const b64 = layer.querySelector('data').textContent.trim();
  const bin = atob(b64);
  const gids = [];
  for (let i = 0; i < bin.length; i += 4) {
    gids.push(bin.charCodeAt(i) | (bin.charCodeAt(i + 1) << 8) |
      (bin.charCodeAt(i + 2) << 16) | (bin.charCodeAt(i + 3) << 24));
  }
  return {
    cols: parseInt(layer.getAttribute('width'), 10),
    tileW: parseInt(tileset.getAttribute('tilewidth'), 10),
    tileH: parseInt(tileset.getAttribute('tileheight'), 10),
    imgW: parseInt(image.getAttribute('width'), 10),
    gids,
  };
}

export async function loadAssets() {
  for (const [name, path] of Object.entries(TEXTURES)) {
    A[name] = await Assets.load(path);
  }

  // --- house blocks (enemy windows) ---
  const e = A.enemyBlock;
  A.leftUnbrocken = region(e, 6, 0, C.FRAME_COLS, C.FRAME_ROWS);
  A.rightUnbrocken = region(e, 6, 1, C.FRAME_COLS, C.FRAME_ROWS);
  A.leftTopUnbrocken = region(e, 4, 0, C.FRAME_COLS, C.FRAME_ROWS);
  A.rightToppUnbrocken = region(e, 4, 1, C.FRAME_COLS, C.FRAME_ROWS);
  A.leftBrocken = region(e, 5, 0, C.FRAME_COLS, C.FRAME_ROWS);
  A.rightBrocken = region(e, 5, 1, C.FRAME_COLS, C.FRAME_ROWS);

  A.mdIdle = new Anim([region(e, 3, 3, C.FRAME_COLS, C.FRAME_ROWS)], C.ANIMATION_SPEED, 'once');
  A.mdShoot = new Anim([
    region(e, 3, 3, C.FRAME_COLS, C.FRAME_ROWS),
    region(e, 4, 3, C.FRAME_COLS, C.FRAME_ROWS),
  ], C.ANIMATION_SPEED, 'loop');

  // --- president (sheet split into cells of WIDTH*32 x HEIGHT*32 = 192x288) ---
  const p = A.presidentSheet;
  const pf = (col) => frame(new Texture({
    source: p.source,
    frame: { x: col * 6 * 32, y: 0, width: 6 * 32, height: 9 * 32 },
  }));
  A.presidentRunRight = new Anim([0,1,2,3,4,5,6,7].map(pf), C.ANIMATION_SPEED, 'loop');
  A.presidentRunLeft = new Anim([7,6,5,4,3,2,1,0].map(pf), C.ANIMATION_SPEED, 'loop');
  A.presidentAimDown = new Anim([pf(8)], C.ANIMATION_SPEED, 'once');
  A.presidentAimMiddle = new Anim([pf(9)], C.ANIMATION_SPEED, 'once');
  A.presidentAimUp = new Anim([pf(10)], C.ANIMATION_SPEED, 'once');

  // --- oil factory ---
  const of = A.oilFactory;
  const ofr = (col) => region(of, 0, col, 4, 1);
  A.oilFactoryAppearance = new Anim([0,1,2,3].map(ofr), C.ANIMATION_SPEED, 'once');
  A.oilFactoryDisappearance = new Anim([3,2,1,0].map(ofr), C.ANIMATION_SPEED, 'once');

  // --- oil field ---
  const oil = A.oilField;
  A.oilFieldAnimation = new Anim([region(oil, 0, 0, 2, 1), region(oil, 0, 1, 2, 1)],
    C.ANIMATION_SPEED_SLOW, 'loop');

  // --- tnt vehicle ---
  const tnt = A.tntVehicle;
  A.tntVehicleRiding = new Anim([region(tnt, 0, 0, 3, 1), region(tnt, 0, 1, 3, 1)],
    C.ANIMATION_SPEED, 'loop');
  A.tntVehicleExploded = frame(region(tnt, 0, 2, 3, 1).tex, true, true);

  // --- aid vehicle (frames flipped horizontally in the original) ---
  const aid = A.aidVehicle;
  A.aidVehicleRiding = new Anim([
    frame(region(aid, 0, 0, 3, 1).tex, true, false),
    frame(region(aid, 0, 1, 3, 1).tex, true, false),
  ], C.ANIMATION_SPEED, 'loop');
  A.aidVehicleExploded = frame(region(aid, 0, 2, 3, 1).tex, true, true);

  // --- explosion ---
  const ex = A.explosion;
  A.explosionAnimation = new Anim(
    Array.from({ length: 13 }, (_, i) => region(ex, 0, i, 13, 1)),
    C.ANIMATION_SPEED, 'once');

  // --- background tiles from area02.tmx ---
  const map = await loadBackgroundMap();
  A.bgMap = map;
  A.bgTiles = map.gids.map((gid) => new Texture({
    source: A.background.source,
    frame: { x: (gid - 1) * map.tileW, y: 0, width: map.tileW, height: map.tileH },
  }));
}
