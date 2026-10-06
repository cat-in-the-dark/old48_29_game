import { chromium } from 'playwright';
const browser = await chromium.launch();
const page = await browser.newPage();
await page.goto('http://localhost:4173/');
await page.waitForFunction(() => window.BSOD, { timeout: 20000 });
await page.waitForFunction(() => window.BSOD.chain.currentFrame === 1, { timeout: 20000 });
for (let i = 0; i < 5; i++) { await page.keyboard.press('Enter'); await page.waitForTimeout(300); }
await page.evaluate(async () => {
  const B = window.BSOD;
  B.keys.add('KeyD');
  await new Promise(r => setTimeout(r, 4000));
  B.keys.delete('KeyD');
});
const state = await page.evaluate(() => {
  const g = window.BSOD.gameScreen;
  return {
    frame: window.BSOD.chain.currentFrame,
    health: window.BSOD.score.getHealth(),
    houses: g.level.levelEntities.House.length,
    oilFields: g.level.levelEntities.OilField.length,
    presidentVisible: g.level.president.sprite.visible && !!g.level.president.sprite.texture,
    presidentX: Math.round(g.level.president.x),
    cameraX: Math.round(g.camera.x),
  };
});
console.log(state);
await page.screenshot({ path: 'test/gameplay2.png' });
await browser.close();
