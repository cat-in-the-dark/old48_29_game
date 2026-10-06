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
  document.body.appendChild(app.canvas);

  await loadAssets();
  await audio.load();

  const keys = new Set();
  const chain = new ScreenChain(app.stage);

  chain.add(new TitleScreen(chain, A.logo, 1000));

  const startGame = new TitleScreen(chain, A.gameStart, 0);
  startGame.bindNext('Enter');
  chain.add(startGame);

  for (const tex of [A.tutorial1, A.tutorial2, A.tutorial3, A.tutorial4]) {
    const screen = new TitleScreen(chain, tex, 10000);
    screen.bindNext('Enter');
    chain.add(screen);
  }

  const gameScreen = new GameScreen(chain, keys);
  chain.add(gameScreen);

  const gameOverScreen = new GameEndScreen(chain, A.gameOver, 0);
  gameOverScreen.bind('Escape', 1);
  chain.add(gameOverScreen);

  const winScreen = new GameEndScreen(chain, A.gameWin, 0);
  winScreen.bind('Escape', 1);
  chain.add(winScreen);

  const controls = new Set([
    'KeyA', 'KeyD', 'KeyW', 'KeyS', 'ArrowLeft', 'ArrowRight',
    'ArrowUp', 'ArrowDown', 'Enter', 'Space', 'Escape',
  ]);
  let paused = false;
  const setPaused = (value) => {
    paused = value;
    if (paused) {
      keys.clear();
      gameScreen.clock.reset();
      app.ticker.stop();
    } else {
      app.ticker.start();
    }
  };
  window.addEventListener('blur', () => setPaused(true));
  window.addEventListener('focus', () => setPaused(document.hidden));
  document.addEventListener('visibilitychange', () => {
    setPaused(document.hidden || !document.hasFocus());
  });

  window.addEventListener('keydown', (e) => {
    if (!controls.has(e.code)) return;
    e.preventDefault();
    if (paused) return;
    keys.add(e.code);
    audio.resume();
    if (!e.repeat) chain.current().keyDown(e.code);
  });
  window.addEventListener('keyup', (e) => keys.delete(e.code));

  app.ticker.add((ticker) => {
    chain.current().render(ticker.deltaMS / 1000);
  });

  chain.gotoFrame(0);
  setPaused(document.hidden);

  window.BSOD = { chain, gameScreen, score, keys };
}

init().catch((err) => {
  console.error(err);
  const el = document.getElementById('fatal') || document.createElement('div');
  el.id = 'fatal';
  el.textContent = 'Failed to start the game: ' + err.message +
    ' (open via `npm run dev`, not file://)';
  if (!el.parentNode) document.body.appendChild(el);
});
