// Regression tests for two reported bugs:
// 1) destroyed vehicle flicker (flip applied via negative scale must be stable)
// 2) enemy sprite must disappear when its house block is destroyed
import { chromium } from 'playwright';

const browser = await chromium.launch();
const page = await browser.newPage();
const errors = [];
page.on('pageerror', e => errors.push(e.message));
await page.goto(process.env.GAME_URL || 'http://127.0.0.1:4173/');
await page.waitForFunction(() => window.BSOD, { timeout: 20000 });
await page.waitForFunction(() => window.BSOD.chain.currentFrame === 1, { timeout: 20000 });
for (let i = 0; i < 5; i++) { await page.keyboard.press('Enter'); await page.waitForTimeout(300); }

const results = await page.evaluate(async () => {
  const B = window.BSOD, g = B.gameScreen, L = g.level;
  const out = {};

  // --- bug 1: vehicle destruction scale must not toggle sign each frame ---
  let v = null;
  for (let i = 0; i < 600 && !v; i++) {
    await new Promise(r => requestAnimationFrame(r));
    for (const t of L.levelEntities.TntVehicle) { v = t; break; }
  }
  if (!v) return { error: 'no tnt vehicle spawned' };
  v.setDestroyed(true);
  const signs = [];
  for (let i = 0; i < 5; i++) {
    await new Promise(r => requestAnimationFrame(r));
    signs.push(Math.sign(v.sprite.scale.x));
  }
  out.vehicleScaleStable = signs.every(s => s === -1);

  // --- bug 2: enemy sprite hidden after block destruction ---
  B.score.setDemocracyLevel(5);   // enemies spawn only when democracy > 0
  B.keys.add('KeyD');             // walk right so the main camera advances
  let block = null;
  for (let i = 0; i < 1200 && !block; i++) {
    await new Promise(r => requestAnimationFrame(r));
    for (const h of L.levelEntities.House)
      for (const b of h.houseBlocks) if (b.isWithEnemy()) { block = b; break; }
  }
  if (!block) return { ...out, error: 'no enemy block found' };
  block.setDestroyed(true);
  await new Promise(r => requestAnimationFrame(r));
  out.enemyHidden = !block.isWithEnemy() && !block.enemySprite.visible;

  return out;
});

console.log(results);
console.log('pageerrors:', errors);
await browser.close();
if (!results.vehicleScaleStable || !results.enemyHidden || errors.length) process.exit(1);
