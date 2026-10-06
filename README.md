# BSoD port — JavaScript + PixiJS

Port of the libgdx game `legacy/` (Ludum Dare 29, "Beneath the surface of Democracy")
to JavaScript + [PixiJS v8](https://pixijs.com/). Design doc: [../DESIGN.md](../DESIGN.md).

## Run

```bash
npm install
npm run dev        # http://localhost:5173
```

Assets (textures, sounds, font, area02.tmx) are copied from `legacy/assets` into `public/assets`.

## Controls

| Key | Action |
|---|---|
| A/D, ←/→ | walk |
| W/S, ↑/↓ | aim up/down |
| Enter | fire rocket (and "next" on title screens) |
| Space | place oil factory (democracy +1) |
| Esc | end screen → back to start page |

## Tests

```bash
npm test
npm run build
npm run preview -- --host 127.0.0.1 --port 4173
# In another terminal:
npm run test:e2e
node test/regression.mjs
```

Set `GAME_URL` to test a different server. The logic tests cover FPS independence,
legacy enemy counts, aiming, collisions, healing, factories, end states and cleanup.
The browser tests exercise keyboard controls, focus pause and repeated sessions.
Win/death tests use real entities placed by fixtures; they do not constitute a
complete unassisted playthrough. `window.BSOD` exposes the game state for these checks.

Gameplay uses a fixed 60 Hz simulation and a level-local clock. Losing focus pauses
simulation and clears held keys. Pixi `zIndex` controls entity layers independently
of spawn order; leaving gameplay destroys the level without destroying shared textures.

## Known deviations from the original

- Escape is handled as a one-shot keydown event instead of per-frame polling
  (the original re-triggered `next()` every frame while held).
- HUD text positions are approximated (libgdx baseline coords → Pixi top-left coords).
- `Enemy.java` was dead code in the original and is not ported.
- Audio uses Web Audio (overlapping SFX + looping BGM) instead of libgdx Sound/Music.
