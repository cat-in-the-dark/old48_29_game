import assert from 'node:assert/strict';
import { chromium } from 'playwright';

const browser = await chromium.launch();
const page = await browser.newPage();
page.setDefaultTimeout(15000);
const errors = [];
page.on('pageerror', e => errors.push(e.message));
page.on('console', m => { if (m.type() === 'error') errors.push(m.text()); });
const currentFrame = () => page.evaluate(() => window.BSOD.chain.currentFrame);

async function startGame() {
  for (let i = 0; i < 5; i++) await page.keyboard.press('Enter');
  assert.equal(await currentFrame(), 6);
  assert.deepEqual(await page.evaluate(() => {
    const { score, gameScreen: g } = window.BSOD;
    return [score.getHealth(), score.getDemocracyLevel(), score.getScore(),
      g.world.children.filter(s => s === g.level.president.sprite).length];
  }), [100, 0, 0, 1]);
}

async function spawnBullets(count) {
  await page.evaluate(count => {
    const { gameScreen: g } = window.BSOD;
    const L = g.level, p = L.president;
    const block = L.levelEntities.House[0].houseBlocks[0];
    block.setWithEnemy(true);
    block.lastShootTime = -Infinity;
    const random = Math.random;
    try {
      Math.random = () => 0;
      block.shoot();
    } finally { Math.random = random; }
    block.setWithEnemy(false);
    const source = L.levelEntities.Bullet.at(-1);
    source.markDeleted();
    for (let i = 0; i < count; i++) {
      L.levelEntities.Bullet.push(new source.constructor(L.world, p.x + 1, p.y + 1, 0, 0));
    }
  }, count);
}

try {
  await page.goto(process.env.GAME_URL || 'http://127.0.0.1:4173/');
  await page.waitForFunction(() => window.BSOD?.chain.currentFrame === 1, null, { timeout: 20000 });
  await startGame();
  console.log('Started');

  await page.keyboard.down('ArrowRight');
  await page.waitForFunction(() => {
    const L = window.BSOD.gameScreen.level;
    return L.levelEntities.OilField.some(f =>
      L.president.x + 3 > f.x && L.president.x + 3 < f.x + f.width);
  }, null, { timeout: 15000 });
  await page.keyboard.up('ArrowRight');
  assert.equal(await page.evaluate(() => window.scrollY), 0);
  await page.keyboard.press('Space', { delay: 50 });
  await page.waitForFunction(() => window.BSOD.score.getDemocracyLevel() === 1);
  assert.equal(await page.evaluate(() => window.BSOD.gameScreen.level.levelEntities.OilFactory.length), 1);

  console.log('Factory placed');
  await page.keyboard.down('KeyW');
  await page.keyboard.down('Enter');
  await page.waitForFunction(() =>
    window.BSOD.gameScreen.level.levelEntities.Rocket.some(r => r.acceleration.y > 0));
  await page.keyboard.up('Enter');
  await page.keyboard.up('KeyW');

  await page.keyboard.down('KeyD');
  const paused = await page.evaluate(() => {
    window.dispatchEvent(new Event('blur'));
    const B = window.BSOD;
    return { time: B.gameScreen.level.time, x: B.gameScreen.level.president.x };
  });
  await page.keyboard.up('KeyD');
  await page.waitForTimeout(200);
  assert.deepEqual(await page.evaluate(() => ({
    time: window.BSOD.gameScreen.level.time,
    x: window.BSOD.gameScreen.level.president.x,
  })), paused);
  assert.equal(await page.evaluate(() => window.BSOD.keys.size), 0);
  await page.evaluate(() => window.dispatchEvent(new Event('focus')));

  console.log('Pause and shooting passed');
  await spawnBullets(1);
  await page.waitForFunction(() => window.BSOD.score.getHealth() === 96);
  await page.waitForTimeout(100);
  assert.equal(await page.evaluate(() => window.BSOD.score.getHealth()), 96);

  // Exercise construction and the win transition without setting the score directly.
  await page.evaluate(() => {
    const B = window.BSOD, L = B.gameScreen.level;
    const Field = L.levelEntities.OilField[0].constructor;
    for (let i = B.score.getDemocracyLevel(); i < 10; i++) {
      L.levelEntities.OilField.push(new Field(L.world, L.president.x, 0.5));
    }
    B.keys.add('Space');
  });
  await page.waitForFunction(() => window.BSOD.chain.currentFrame === 8);
  assert.equal(await page.evaluate(() => window.BSOD.gameScreen.world.children.length), 0);
  assert.equal(await page.evaluate(() => window.BSOD.chain.current().scoreText.text),
    await page.evaluate(() => 'Score:' + window.BSOD.score.getScore()));

  console.log('Win passed');
  await page.keyboard.press('Escape');
  await startGame();
  await page.waitForFunction(() => window.BSOD.gameScreen.level.levelEntities.House.length > 0);
  await spawnBullets(25);
  await page.waitForFunction(() => window.BSOD.chain.currentFrame === 7);
  assert.equal(await page.evaluate(() => window.BSOD.gameScreen.world.children.length), 0);

  for (let i = 0; i < 3; i++) {
    await page.keyboard.press('Escape');
    await startGame();
    await page.keyboard.down('Escape');
    await page.evaluate(() => window.dispatchEvent(new KeyboardEvent('keydown', {
      code: 'Escape', repeat: true, cancelable: true,
    })));
    assert.equal(await currentFrame(), 7);
    await page.keyboard.up('Escape');
    assert.equal(await page.evaluate(() => window.BSOD.gameScreen.world.children.length), 0);
  }

  assert.deepEqual(errors, []);
  console.log('Browser: controls, shooting, oil placement, pause, damage, win/death and repeated restarts passed.');
} finally {
  await browser.close();
}
