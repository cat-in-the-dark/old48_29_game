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
npm run build
npx vite preview --port 4173 &
node test/e2e.mjs
```

`test/e2e.mjs` drives the game headlessly via Playwright: screen chain flow, procedural
generation (houses/oil fields/vehicles), shooting, factory placement, bullet damage,
win (democracy 10) and game-over (health 0) transitions. `main.js` exposes a `window.BSOD`
debug hook (`chain`, `gameScreen`, `score`, `keys`) used only by the tests.

`test/regression.mjs` covers two previously reported bugs: stable flip scale on destroyed
vehicles (no flicker) and enemy sprites being hidden when their house block is destroyed.

## Known deviations from the original

- Escape is handled as a one-shot keydown event instead of per-frame polling
  (the original re-triggered `next()` every frame while held).
- HUD text positions are approximated (libgdx baseline coords → Pixi top-left coords).
- `Enemy.java` was dead code in the original and is not ported.
- Audio uses Web Audio (overlapping SFX + looping BGM) instead of libgdx Sound/Music.
