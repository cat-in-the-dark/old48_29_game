const STEP = 1 / 60;

// Keep the legacy movement and collision steps at 60 Hz.
export class FixedStep {
  elapsed = 0;

  reset() { this.elapsed = 0; }

  advance(delta, update) {
    this.elapsed += Math.min(Math.max(delta, 0), 0.25);
    while (this.elapsed + 1e-10 >= STEP) {
      this.elapsed = Math.max(0, this.elapsed - STEP);
      if (update(STEP) === false) {
        this.reset();
        break;
      }
    }
  }
}
