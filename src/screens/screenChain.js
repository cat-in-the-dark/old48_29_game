// Port of com.catinthedark.screens.ScreenChain
export class ScreenChain {
  constructor(stage) {
    this.screens = [];
    this.stage = stage;
    this.currentFrame = 0;
  }

  add(screen) { this.screens.push(screen); }

  next() { this.gotoFrame(this.currentFrame + 1); }
  prev() { this.gotoFrame(this.currentFrame - 1); }

  gotoFrame(index) {
    if (index < 0 || index >= this.screens.length) {
      throw new Error(`frame with id '${index}' does not exists! AZAZA!`);
    }
    this.currentFrame = index;
    const screen = this.screens[index];
    this.stage.removeChildren();
    this.stage.addChild(screen.container);
    screen.show();
  }

  current() { return this.screens[this.currentFrame]; }
}
