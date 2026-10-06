import assert from 'node:assert/strict';
import { Container, Texture } from 'pixi.js';
import { A } from '../src/assets.js';
import { score } from '../src/gameScore.js';
import { FixedStep } from '../src/fixedStep.js';
import { GameScreen } from '../src/screens/gameScreen.js';
import { House } from '../src/entities/house.js';
import { Bullet } from '../src/entities/bullet.js';
import { Rocket } from '../src/entities/rocket.js';
import { OilField } from '../src/entities/oilField.js';
import { TntVehicle } from '../src/entities/tntVehicle.js';
import { AidVehicle } from '../src/entities/aidVehicle.js';
import { LAYERS } from '../src/entity.js';

const frame = { tex: Texture.EMPTY };
for (const name of [
  'presidentRunRight', 'presidentRunLeft', 'presidentAimMiddle', 'presidentAimUp',
  'presidentAimDown', 'mdIdle', 'mdShoot', 'oilFactoryAppearance',
  'oilFactoryDisappearance', 'oilFieldAnimation', 'tntVehicleRiding',
  'aidVehicleRiding', 'explosionAnimation',
]) A[name] = { getKeyFrame: () => frame };
for (const name of [
  'leftBrocken', 'rightBrocken', 'leftTopUnbrocken', 'rightToppUnbrocken',
  'leftUnbrocken', 'rightUnbrocken', 'bullet', 'rocket', 'oilHint',
]) A[name] = Texture.EMPTY;
A.tntVehicleExploded = A.aidVehicleExploded = { ...frame, flipX: true, flipY: true };

function game() {
  const g = Object.assign(Object.create(GameScreen.prototype), {
    world: new Container({ sortableChildren: true }), bg: new Container(),
    camera: {}, backCamera: {}, keys: new Set(), clock: new FixedStep(),
    hud: { setHealth() {}, setDemocracyLevel() {}, render() {} },
  });
  g.chain = {
    currentFrame: 6, current: () => g.chain.currentFrame === 6 ? g : null,
    gotoFrame(index) { g.hide(); this.currentFrame = index; },
  };
  g.show();
  return g;
}

const originalRandom = Math.random;
try {
  const snapshots = [];
  for (const hz of [30, 60, 120, 144]) {
    let seed = 123;
    Math.random = () => ((seed = (seed * 1664525 + 1013904223) >>> 0) / 2 ** 32);
    const g = game();
    g.keys.add('KeyD');
    for (let i = 0; i < hz * 10; i++) g.render(1 / hz);
    snapshots.push({
      x: g.level.president.x, camera: g.camera.x,
      houses: g.level.levelEntities.House.map(h => h.x),
      vehicles: g.level.levelEntities.TntVehicle.map(v => v.x),
    });
    assert.ok(g.level.time > 9.99 && g.level.time < 10.01);
    g.hide();
    assert.equal(g.world.children.length, 0);
  }
  for (const snapshot of snapshots.slice(1)) assert.deepEqual(snapshot, snapshots[0]);

  Math.random = () => 0.99;
  const g = game();
  score.setDemocracyLevel(1);
  const house = new House(g.level, 5, 4, 2, 2);
  g.level.levelEntities.House.push(house);
  assert.equal(house.houseBlocks.filter(b => b.isWithEnemy()).length, 0);
  g.world.sortChildren();
  assert.ok(g.world.getChildIndex(house.houseBlocks[0].sprite) <
    g.world.getChildIndex(g.level.president.sprite));
  assert.equal(g.level.president.sprite.zIndex, LAYERS.President);

  for (const state of ['AIM_UP', 'AIM_DOWN', 'IDLE']) {
    g.keys.clear();
    g.keys.add('Enter');
    if (state === 'AIM_UP') g.keys.add('KeyW');
    if (state === 'AIM_DOWN') g.keys.add('KeyS');
    g.level.president.lastShutTime = 1;
    g.processKeys();
    const rocket = g.level.levelEntities.Rocket.at(-1);
    assert.equal(Math.sign(rocket.acceleration.y), state === 'AIM_UP' ? 1 : -1);
    assert.equal(rocket.y, g.level.president.y + 4.5 +
      (state === 'AIM_UP' ? 1.2 : state === 'AIM_DOWN' ? 0.1 : 0.8));
  }
  g.level.levelEntities.Rocket.forEach(r => r.markDeleted());

  const bullet = new Bullet(g.world, 1, 5, -0.2, 0);
  g.level.levelEntities.Bullet.push(bullet);
  score.setHealth(100);
  g.interManager.damagePresident();
  assert.equal(score.getHealth(), 96);
  g.interManager.damagePresident();
  assert.equal(score.getHealth(), 96);

  const target = house.houseBlocks[1];
  target.setWithEnemy(true);
  const houseRocket = new Rocket(g.world, target.x, target.y, g.level.president);
  g.level.levelEntities.Rocket.push(houseRocket);
  g.interManager.destroyHouseBlock();
  assert.ok(target.isDestroyed());
  assert.ok(houseRocket.isMarkedToDelete());
  assert.equal(score.getScore(), 250);
  score.resetScore();

  const interceptedBullet = new Bullet(g.world, 20, 5, 0, 0);
  const interceptor = new Rocket(g.world, 20, 5, g.level.president);
  g.level.levelEntities.Bullet.push(interceptedBullet);
  g.level.levelEntities.Rocket.push(interceptor);
  g.interManager.deactivateTerroristBullets();
  assert.ok(interceptedBullet.isMarkedToDelete());
  assert.ok(interceptor.isMarkedToDelete());

  const aid = new AidVehicle(g.world, 3, 4, 0.5, 0);
  const rocket = new Rocket(g.world, 3, 4, g.level.president);
  g.level.levelEntities.AidVehicle.push(aid);
  g.level.levelEntities.Rocket.push(rocket);
  g.interManager.destroyAid();
  assert.equal(score.getHealth(), 100);
  assert.equal(score.getScore(), 1000);
  assert.ok(aid.isDestroyed());
  aid.render(1 / 60);
  const scale = aid.sprite.scale.x;
  aid.render(1 / 60);
  assert.equal(aid.sprite.scale.x, scale);

  const tnt = new TntVehicle(g.world, 3, 4, -0.1, 0);
  g.level.levelEntities.TntVehicle.push(tnt);
  g.level.levelEntities.Rocket.push(new Rocket(g.world, 3, 4, g.level.president));
  g.interManager.destroyKamaz();
  assert.ok(tnt.isDestroyed());
  assert.equal(score.getScore(), 1500);

  const block = house.houseBlocks[0];
  block.setWithEnemy(true);
  block.render(1 / 60);
  assert.ok(block.enemySprite.visible);
  block.setDestroyed(true);
  block.render(1 / 60);
  assert.equal(block.enemySprite.visible, false);

  score.setDemocracyLevel(0);
  g.level.levelEntities.OilField.push(new OilField(g.world, 0, 0.5));
  g.level.placeOilFactory();
  assert.equal(score.getDemocracyLevel(), 1);
  g.level.placeOilFactory();
  assert.equal(score.getDemocracyLevel(), 1);
  const factory = g.level.levelEntities.OilFactory[0];
  g.level.levelEntities.TntVehicle.push(new TntVehicle(g.world, factory.x, 4, -0.1, 0));
  g.interManager.kamazDestroysFactory();
  assert.equal(score.getDemocracyLevel(), 0);
  assert.ok(factory.isDestroyed());

  const sprites = [...g.world.children];
  g.hide();
  assert.equal(g.world.children.length, 0);
  assert.ok(sprites.every(s => s.destroyed));
  for (let i = 0; i < 20; i++) {
    g.chain.currentFrame = 6;
    g.show();
    assert.equal(g.world.children.length, 1);
    assert.equal(score.getHealth(), 100);
    assert.equal(score.getDemocracyLevel(), 0);
    assert.equal(score.getScore(), 0);
    g.render(1 / 60);
    g.hide();
    assert.equal(g.world.children.length, 0);
  }

  // Reach both end states through gameplay mechanics, including a multi-step frame.
  g.chain.currentFrame = 6;
  g.show();
  for (let i = 0; i < 10; i++) {
    g.level.levelEntities.OilField.push(new OilField(g.world, 0, 0.5));
    g.level.placeOilFactory();
  }
  g.render(1 / 30);
  assert.equal(g.chain.currentFrame, 8);
  assert.equal(g.world.children.length, 0);
  g.chain.currentFrame = 6;
  g.show();
  for (let i = 0; i < 25; i++) {
    g.level.levelEntities.Bullet.push(new Bullet(g.world, 1, 5, 0, 0));
  }
  g.render(1 / 30);
  assert.equal(g.chain.currentFrame, 7);
  assert.equal(g.world.children.length, 0);
  g.chain.currentFrame = 6;
  g.show();
  score.setDemocracyLevel(10);
  score.setHealth(0);
  g.render(1 / 30);
  assert.equal(g.chain.currentFrame, 8);

  console.log('Logic: FPS independence, legacy balance, aim, damage, healing, layers, factories, restart, win/death passed.');
} finally {
  Math.random = originalRandom;
}
