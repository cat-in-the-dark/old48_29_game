import { Application } from 'pixi.js';
import { A, loadAssets } from './assets.js';
import { audio } from './audio.js';
import { score } from './gameScore.js';
import { ScreenChain } from './screens/screenChain.js';
import { TitleScreen } from './screens/titleScreen.js';
import { GameEndScreen } from './screens/gameEndScreen.js';
import { GameScreen } from './screens/gameScreen.js';

async function init() {
  const app = new Application();
  await app.init({ width: 1024, height: 640, background: 0x000000 });
  // Pixi v8 does not attach the canvas to the DOM automatically
  document.body.appendChild(app.canvas);

  await loadAssets();
  await audio.load();

  const keys = new Set();
  const chain = new ScreenChain(app.stage);

  // Screen chain, exactly as in BSODGame.create()
  // Original: new TitleScreen(screenChain, Assets.logoTex, 1000) — 1 second
  chain.add(new TitleScreen(chain, A.logo, 1000));

  const startGame = new TitleScreen(chain, A.gameStart, 0);
  startGame.bindNext('Enter');
  chain.add(startGame);

  for (const tex of [A.tutorial1, A.tutorial2, A.tutorial3, A.tutorial4]) {
    const screen = new TitleScreen(chain, tex, 10000);
    screen.bindNext('Enter');
    chain.add(screen);
  }

  chain.add(new GameScreen(chain, keys));

  const gameOverScreen = new GameEndScreen(chain, A.gameOver, 0);
  gameOverScreen.bind('Escape', 1);
  chain.add(gameOverScreen);

  const winScreen = new GameEndScreen(chain, A.gameWin, 0);
  winScreen.bind('Escape', 1);
  chain.add(winScreen);

  window.addEventListener('keydown', (e) => {
    keys.add(e.code);
    audio.resume();
    chain.current().keyDown(e.code);
  });
  window.addEventListener('keyup', (e) => keys.delete(e.code));

  app.ticker.add((ticker) => {
    chain.current().render(ticker.deltaTime / 60);
  });

  chain.gotoFrame(0);

  // debug/test hook
  window.BSOD = { chain, gameScreen: chain.screens[6], score, keys };
}

init().catch((err) => {
  console.error(err);
  const el = document.getElementById('fatal') || document.createElement('div');
  el.id = 'fatal';
  el.textContent = 'Failed to start the game: ' + err.message +
    ' (open via `npm run dev`, not file://)';
  if (!el.parentNode) document.body.appendChild(el);
});
